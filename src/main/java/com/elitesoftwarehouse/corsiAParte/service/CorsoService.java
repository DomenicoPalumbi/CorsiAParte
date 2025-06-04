package com.elitesoftwarehouse.corsiAParte.service;

import com.elitesoftwarehouse.corsiAParte.client.DocenteClient;
import com.elitesoftwarehouse.corsiAParte.converter.CorsoConverter;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.data.dto.DocenteDTO;
import com.elitesoftwarehouse.corsiAParte.data.entity.Corso;
import com.elitesoftwarehouse.corsiAParte.repository.CorsoRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CorsoService {

    private final CorsoRepository corsoRepository;
    private final CorsoConverter corsoConverter;
    private final DocenteClient docenteClient;

    public CorsoService(CorsoRepository corsoRepository, CorsoConverter corsoConverter, DocenteClient docenteClient) {
        this.corsoRepository = corsoRepository;
        this.corsoConverter = corsoConverter;
        this.docenteClient = docenteClient;
    }

    private boolean doesDocenteExist(Long docenteId) {
        ResponseEntity<DocenteDTO> response = docenteClient.getDocenteById(docenteId);

        if (response.getBody() != null) {
            System.out.println("Docente trovato!");
            return true;
        } else {
            System.out.println("Docente con ID " + docenteId + " non trovato!");
            return false;
        }
    }


    public List<CorsoDTO> getAllCorsiDTO() {
        List<Corso> corsi = corsoRepository.findAll();
        return corsoConverter.toDtoList(corsi);
    }

    public List<CorsoFullDTO> getAllCorsiFullDTO() {
        List<Corso> corsi = corsoRepository.findAll();
        return corsoConverter.toFullDtoList(corsi);
    }

    public CorsoDTO getCorsoById(Long id) {
        return corsoRepository.findById(id)
                .map(corsoConverter::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Corso non trovato con id: " + id));
    }

    public CorsoFullDTO getCorsoFullById(Long id) {
        return corsoRepository.findById(id)
                .map(corsoConverter::toFullDto)
                .orElseThrow(() -> new EntityNotFoundException("Corso non trovato con id: " + id));
    }

    @Transactional
    public CorsoDTO saveCorso(CorsoFullDTO corsoFullDTO) {
        if (corsoFullDTO.getId() != null) {
            throw new IllegalArgumentException("Un nuovo corso non deve avere un ID");
        }

        Long docenteId = corsoFullDTO.getDocenteId();
        if (docenteId == null) {
            throw new IllegalArgumentException("L'ID del docente non può essere null");
        }

        if (!doesDocenteExist(docenteId)) {
            throw new EntityNotFoundException("Impossibile creare il corso: docente non trovato con id: " + docenteId);
        }

        Corso corso = corsoConverter.toEntity(corsoFullDTO);
        Corso saved = corsoRepository.save(corso);
        return corsoConverter.toDto(saved);
    }

    @Transactional
    public CorsoDTO updateCorso(Long id, CorsoFullDTO corsoFullDTO) {
        if (!id.equals(corsoFullDTO.getId())) {
            throw new IllegalArgumentException("L'ID del path non corrisponde all'ID nel DTO");
        }

        Long docenteId = corsoFullDTO.getDocenteId();
        if (docenteId == null) {
            throw new IllegalArgumentException("L'ID del docente non può essere null");
        }

        if (!doesDocenteExist(docenteId)) {
            throw new EntityNotFoundException("Impossibile aggiornare il corso: docente non trovato con id: " + docenteId);
        }

        Corso esistente = corsoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Corso non trovato con id: " + id));

        if (corsoFullDTO.getNome() != null) {
            esistente.setNome(corsoFullDTO.getNome());
        }
        if (corsoFullDTO.getAnnoAccademico() != null) {
            esistente.setAnnoAccademico(corsoFullDTO.getAnnoAccademico());
        }
        esistente.setDocenteId(docenteId);

        Corso updated = corsoRepository.save(esistente);
        return corsoConverter.toDto(updated);
    }

    @Transactional
    public void deleteCorso(Long id) {
        if (!corsoRepository.existsById(id)) {
            throw new EntityNotFoundException("Corso non trovato con id: " + id);
        }
        corsoRepository.deleteById(id);
    }
}