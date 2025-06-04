package com.elitesoftwarehouse.corsiAParte.service;
import com.elitesoftwarehouse.corsiAParte.mapper.CorsoMapper;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.DocenteDTO;
import com.elitesoftwarehouse.corsiAParte.model.entity.Corso;
import com.elitesoftwarehouse.corsiAParte.repository.CorsoRepository;
import com.elitesoftwarehouse.corsiAParte.service.client.DocenteServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CorsoService {
    @Autowired
    private CorsoRepository corsoRepository;
    @Autowired
    private CorsoMapper corsoMapper;
    @Autowired
    private DocenteServiceClient docenteServiceClient;

    @Autowired
    public CorsoService(CorsoRepository corsoRepository,
                        CorsoMapper corsoMapper,
                        DocenteServiceClient docenteServiceClient) {
        this.corsoRepository = corsoRepository;
        this.corsoMapper = corsoMapper;
        this.docenteServiceClient = docenteServiceClient;
    }

    public List<CorsoDTO> getAllCorsiDTO() {
            List<Corso> corsi = corsoRepository.findAll();

            return corsi.stream().map(corso -> {
                CorsoDTO dto = corsoMapper.toDto(corso);

                try {
                    DocenteDTO docente = docenteServiceClient.getDocenteById(corso.getDocenteId());
                    dto.setNomeDocente(docente.getNome());
                    dto.setCognomeDocente(docente.getCognome());
                } catch (Exception e) {
                    dto.setNomeDocente("N/D");
                    dto.setCognomeDocente("N/D");
                }

                return dto;
            }).collect(Collectors.toList());
        }
        public CorsoDTO saveCorso(CorsoFullDTO corsoFullDTO) {
        Long docenteId = corsoFullDTO.getDocenteId();
        if (docenteId == null) {
            throw new IllegalArgumentException("L'ID del docente è obbligatorio");
        }

        boolean docenteEsiste = docenteServiceClient.docenteEsiste(docenteId);
        if (!docenteEsiste) {
            throw new IllegalArgumentException("Docente non trovato con id: " + docenteId);
        }

        Corso corso = corsoMapper.toEntity(corsoFullDTO);
        Corso salvato = corsoRepository.save(corso);

        return corsoMapper.toDto(salvato);
    }

    public CorsoDTO updateCorso(Long id, CorsoFullDTO corsoFullDTO) {
        Optional<Corso> corsoOpt = corsoRepository.findById(id);
        if (corsoOpt.isEmpty()) {
            throw new RuntimeException("Corso non trovato con id: " + id);
        }

        // Verifica l'esistenza del docente se è stato specificato
        Long docenteId = corsoFullDTO.getDocenteId();
        if (docenteId != null && !docenteServiceClient.docenteEsiste(docenteId)) {
            throw new IllegalArgumentException("Docente non trovato con id: " + docenteId);
        }

        Corso corso = corsoOpt.get();
        corsoMapper.updateCorsoFromDTO(corsoFullDTO, corso);
        Corso updated = corsoRepository.save(corso);
        return corsoMapper.toDto(updated);
    }

    public void deleteCorso(Long id) {
        if (!corsoRepository.existsById(id)) {
            throw new RuntimeException("Corso non trovato con id: " + id);
        }
        corsoRepository.deleteById(id);
    }

    public CorsoDTO getCorsoById(Long id) {
        Optional<Corso> corsoOpt = corsoRepository.findById(id);
        if (corsoOpt.isEmpty()) {
            throw new RuntimeException("Corso non trovato con id: " + id);
        }
        return corsoMapper.toDto(corsoOpt.get());
    }
}