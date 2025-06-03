package com.elitesoftwarehouse.corsiAParte.service;
import com.elitesoftwarehouse.corsiAParte.converter.CorsoConverter;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.data.entity.Corso;
import com.elitesoftwarehouse.corsiAParte.repository.CorsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CorsoService {
    @Autowired
    private CorsoRepository corsoRepository;

    @Autowired
    private CorsoConverter corsoConverter;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${docenti.url}")
    private String docentiUrl;

    public CorsoService(CorsoRepository corsoRepository,
                        CorsoConverter corsoConverter,
                        RestTemplate restTemplate) {
        this.corsoRepository = corsoRepository;
        this.corsoConverter = corsoConverter;
        this.restTemplate = restTemplate;
    }

    public List<CorsoDTO> getAllCorsiDTO() {
        List<Corso> corsi = corsoRepository.findAll();
        return corsoConverter.toDtoList(corsi);
    }

    public CorsoDTO saveCorso(CorsoFullDTO corsoFullDTO) {
        // Verifica esistenza docente
        if (!verificaDocente(corsoFullDTO.getDocenteId())) {
            throw new RuntimeException("Docente con ID " + corsoFullDTO.getDocenteId() + " non trovato!");
        }

        Corso corso = corsoConverter.toEntity(corsoFullDTO);
        Corso saved = corsoRepository.save(corso);
        return corsoConverter.toDto(saved);
    }

    public CorsoDTO updateCorso(Long id, CorsoFullDTO corsoFullDTO) {
        // Verifica esistenza corso
        Optional<Corso> corsoOpt = corsoRepository.findById(id);
        if (corsoOpt.isEmpty()) {
            throw new RuntimeException("Corso non trovato con id: " + id);
        }

        // Verifica esistenza nuovo docente
        if (!verificaDocente(corsoFullDTO.getDocenteId())) {
            throw new RuntimeException("Docente con ID " + corsoFullDTO.getDocenteId() + " non trovato!");
        }

        Corso corso = corsoOpt.get();
        corsoConverter.getModelMapper().map(corsoFullDTO, corso);

        Corso updated = corsoRepository.save(corso);
        return corsoConverter.toDto(updated);
    }

    public void deleteCorso(Long id) {
        if (!corsoRepository.existsById(id)) {
            throw new RuntimeException("Corso non trovato con id: " + id);
        }
        corsoRepository.deleteById(id);
    }

    private boolean verificaDocente(Long idDocente) {
        try {
            String urlDocente = docentiUrl + "/docenti/" + idDocente;
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    urlDocente,
                    HttpMethod.GET,
                    entity,
                    Object.class
            );

            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
