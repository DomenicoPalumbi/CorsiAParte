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
import org.springframework.web.reactive.function.client.WebClient;

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

    // Metodo per creare un corso
    public CorsoDTO saveCorso(CorsoFullDTO corsoFullDTO) {
        // 1. Creare o ottenere il docente
        DocenteDTO docente = docenteServiceClient.getOrCreateDocente(
                corsoFullDTO.getNomeDocente(),
                corsoFullDTO.getCognomeDocente()
        );

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
            corsoDiscenteService.saveAssociazioni(savedCorso.getId(), discentiIds);  // Associa i discenti al corso
        }

        // 5. Preparare il DTO di risposta con discenti associati
        CorsoDTO corsoDTO = corsoMapper.toDto(savedCorso);
        corsoDTO.setDiscenti(discentiAssociati);
        return corsoDTO;
    }

    // Metodo per aggiornare un corso
    public CorsoDTO updateCorso(Long id, CorsoFullDTO corsoFullDTO) {
        // 1. Verifica che il corso esista
        Corso esistente = corsoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));

        // 2. Aggiornare il docente
        DocenteDTO docente = docenteServiceClient.getOrCreateDocente(
                corsoFullDTO.getNomeDocente(),
                corsoFullDTO.getCognomeDocente()
        );

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
        corsoAggiornato.setId(id); // Mantieni lo stesso ID
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
        CorsoDTO corsoDTO = corsoMapper.toDto(savedCorso);
        corsoDTO.setDiscenti(discentiAggiornati);
        return corsoDTO;
    }

    // Metodo per eliminare un corso
    public void deleteCorso(Long id) {
        // 1. Verifica che il corso esista
        Corso corso = corsoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));

        // 2. Rimuovere le associazioni corso-discenti
        corsoDiscenteService.removeAssociazioni(corso.getId());

        // 3. Eliminare il corso
        corsoRepository.deleteById(id);
    }

    // Metodo per ottenere un corso e i discenti associati
    public CorsoDTO getCorsoById(Long id) {
        Corso corso = corsoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));

        CorsoDTO corsoDTO = corsoMapper.toDto(corso);

        // Carica i discenti associati
        List<DiscenteDTO> discenti = corsoDiscenteService.getDiscentiByCorsoId(id);
        corsoDTO.setDiscenti(discenti);

        return corsoDTO;
    }
    public List<CorsoDTO> getAllCorsi() {
        return corsoRepository.findAll().stream()
                .map(corso -> {
                    CorsoDTO dto = corsoMapper.toDto(corso);
                    dto.setDiscenti(corsoDiscenteService.getDiscentiByCorsoId(corso.getId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
