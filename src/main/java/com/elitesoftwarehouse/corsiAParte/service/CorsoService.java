package com.elitesoftwarehouse.corsiAParte.service;
import com.elitesoftwarehouse.corsiAParte.converter.CorsoConverter;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.data.entity.Corso;
import com.elitesoftwarehouse.corsiAParte.repository.CorsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorsoService {
    @Autowired
    CorsoRepository corsoRepository;
    @Autowired
    CorsoConverter corsoConverter;

    public CorsoService(CorsoRepository corsoRepository, CorsoConverter corsoConverter) {
        this.corsoRepository = corsoRepository;
        this.corsoConverter = corsoConverter;
    }

    public List<CorsoDTO> getAllCorsiDTO() {
        List<Corso> corsi = corsoRepository.findAll();
        return corsoConverter.toDtoList(corsi);
    }

    public CorsoDTO saveCorso(CorsoFullDTO corsoFullDTO) {
        Corso corso = corsoConverter.toEntity(corsoFullDTO);
        Corso saved = corsoRepository.save(corso);
        return corsoConverter.toDto(saved);
    }

    public CorsoDTO updateCorso(Long id, CorsoFullDTO corsoFullDTO) {
        Optional<Corso> corsoOpt = corsoRepository.findById(id);
        if (corsoOpt.isEmpty()) {
            throw new RuntimeException("Corso non trovato con id: " + id);
        }

        Corso corso = corsoOpt.get();

        // Aggiorna i campi del corso con quelli del corsoFullDTO (puoi usare modelMapper o manualmente)
        // Esempio semplice con ModelMapper:
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
}
