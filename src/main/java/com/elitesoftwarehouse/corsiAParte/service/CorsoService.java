package com.elitesoftwarehouse.corsiAParte.service;
import com.elitesoftwarehouse.corsiAParte.mapper.CorsoMapper;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.DiscenteDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.DocenteDTO;
import com.elitesoftwarehouse.corsiAParte.model.entity.Corso;
import com.elitesoftwarehouse.corsiAParte.repository.CorsoRepository;
import com.elitesoftwarehouse.corsiAParte.service.client.CorsoDiscenteService;
import com.elitesoftwarehouse.corsiAParte.service.client.DiscenteServiceClient;
import com.elitesoftwarehouse.corsiAParte.service.client.DocenteServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorsoService {

    private final CorsoRepository corsoRepository;
    private final CorsoMapper corsoMapper;
    private final DocenteServiceClient docenteServiceClient;
    private final DiscenteServiceClient discenteServiceClient;
    private final CorsoDiscenteService corsoDiscenteService;

    @Autowired
    public CorsoService(CorsoRepository corsoRepository,
                        CorsoMapper corsoMapper,
                        DocenteServiceClient docenteServiceClient,
                        DiscenteServiceClient discenteServiceClient,
                        CorsoDiscenteService corsoDiscenteService) {
        this.corsoRepository = corsoRepository;
        this.corsoMapper = corsoMapper;
        this.docenteServiceClient = docenteServiceClient;
        this.discenteServiceClient = discenteServiceClient;
        this.corsoDiscenteService = corsoDiscenteService;
    }

    public Mono<CorsoDTO> saveCorso(CorsoFullDTO corsoFullDTO) {
        return docenteServiceClient.getOrCreateDocente(
                corsoFullDTO.getNomeDocente(),
                corsoFullDTO.getCognomeDocente()
        ).flatMap(docente -> {
            // 2. Creare o ottenere i discenti
            List<DiscenteDTO> discentiAssociati = new ArrayList<>();
            if (corsoFullDTO.getDiscenti() != null) {
                for (DiscenteDTO discenteDTO : corsoFullDTO.getDiscenti()) {
                    DiscenteDTO discente = discenteServiceClient.getOrCreateDiscente(discenteDTO);
                    discentiAssociati.add(discente);
                }
            }

            // 3. Creare il corso
            Corso corso = corsoMapper.toEntity(corsoFullDTO);
            corso.setDocenteId(docente.getId());
            Corso savedCorso = corsoRepository.save(corso);

            // 4. Associare automaticamente i discenti al corso
            if (!discentiAssociati.isEmpty()) {
                List<Long> discentiIds = discentiAssociati.stream()
                        .map(DiscenteDTO::getId)
                        .collect(Collectors.toList());
                corsoDiscenteService.saveAssociazioni(savedCorso.getId(), discentiIds);
            }

            // 5. Preparare il DTO di risposta con discenti associati
            return corsoMapper.toDto(savedCorso)
                    .map(dto -> {
                        dto.setDiscenti(discentiAssociati);
                        return dto;
                    });
        });
    }

    public Mono<CorsoDTO> updateCorso(Long id, CorsoFullDTO corsoFullDTO) {
        return docenteServiceClient.getOrCreateDocente(
                corsoFullDTO.getNomeDocente(),
                corsoFullDTO.getCognomeDocente()
        ).flatMap(docente -> {
            // 1. Verifica che il corso esista
            Corso esistente = corsoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));

            // 3. Aggiornare i discenti
            List<DiscenteDTO> discentiAggiornati = new ArrayList<>();
            if (corsoFullDTO.getDiscenti() != null) {
                for (DiscenteDTO discenteDTO : corsoFullDTO.getDiscenti()) {
                    DiscenteDTO discente = discenteServiceClient.getOrCreateDiscente(discenteDTO);
                    discentiAggiornati.add(discente);
                }
            }

            // 4. Aggiornare il corso
            Corso corsoAggiornato = corsoMapper.toEntity(corsoFullDTO);
            corsoAggiornato.setId(id);
            corsoAggiornato.setDocenteId(docente.getId());
            Corso savedCorso = corsoRepository.save(corsoAggiornato);

            // 5. Aggiornare le associazioni corso-discenti
            if (!discentiAggiornati.isEmpty()) {
                List<Long> discentiIds = discentiAggiornati.stream()
                        .map(DiscenteDTO::getId)
                        .collect(Collectors.toList());
                corsoDiscenteService.saveAssociazioni(savedCorso.getId(), discentiIds);
            }

            // 6. Prepariamo il DTO di risposta con discenti associati
            return corsoMapper.toDto(savedCorso)
                    .map(dto -> {
                        dto.setDiscenti(discentiAggiornati);
                        return dto;
                    });
        });
    }

    public void deleteCorso(Long id) {
        // 1. Verifica che il corso esista
        Corso corso = corsoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));

        // 2. Rimuovere le associazioni corso-discenti
        corsoDiscenteService.removeAssociazioni(corso.getId());

        // 3. Eliminare il corso
        corsoRepository.deleteById(id);
    }

    public Mono<CorsoDTO> getCorsoById(Long id) {
        Corso corso = corsoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));

        return corsoMapper.toDto(corso)
                .flatMap(corsoDTO -> 
                    corsoDiscenteService.getDiscentiByCorsoId(id)
                        .map(discenti -> {
                            corsoDTO.setDiscenti(discenti);
                            return corsoDTO;
                        })
                );
    }

    public Mono<List<CorsoDTO>> getAllCorsi() {
        List<Corso> corsi = corsoRepository.findAll();
        List<Mono<CorsoDTO>> corsoMonos = corsi.stream()
                .map(corso -> corsoMapper.toDto(corso)
                        .flatMap(dto -> corsoDiscenteService.getDiscentiByCorsoId(corso.getId())
                                .map(discenti -> {
                                    dto.setDiscenti(discenti);
                                    return dto;
                                })))
                .collect(Collectors.toList());

        return Mono.zip(corsoMonos, objects -> {
            List<CorsoDTO> result = new ArrayList<>();
            for (Object obj : objects) {
                result.add((CorsoDTO) obj);
            }
            return result;
        });
    }
}
