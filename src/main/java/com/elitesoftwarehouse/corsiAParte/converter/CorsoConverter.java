package com.elitesoftwarehouse.corsiAParte.converter;

import com.elitesoftwarehouse.corsiAParte.client.DocenteClient;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.data.entity.Corso;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CorsoConverter {
    private final ModelMapper modelMapper;
    private final DocenteClient docenteClient;

    @Autowired
    public CorsoConverter(ModelMapper modelMapper, DocenteClient docenteClient) {
        this.modelMapper = modelMapper;
        this.docenteClient = docenteClient;
    }

    // Corso → CorsoDTO
    public CorsoDTO toDto(Corso corso) {
        if (corso == null) return null;

        CorsoDTO dto = modelMapper.map(corso, CorsoDTO.class);

        try {
            var response = docenteClient.getDocenteById(corso.getDocenteId());
            if (response.getBody() != null) {
                dto.setNomeDocente(response.getBody().getNome());
                dto.setCognomeDocente(response.getBody().getCognome());
            }
        } catch (Exception e) {
            dto.setNomeDocente("Non disponibile");
            dto.setCognomeDocente("Non disponibile");
        }

        return dto;
    }


    // CorsoDTO → Corso
    public Corso toEntity(CorsoDTO corsoDTO) {
        if (corsoDTO == null) return null;

        if (corsoDTO.getDocenteId() != null) {
            try {
                docenteClient.getDocenteById(corsoDTO.getDocenteId());
            } catch (FeignException.NotFound e) {
                throw new EntityNotFoundException("Docente non trovato con id: " + corsoDTO.getDocenteId());
            }
        }

        return modelMapper.map(corsoDTO, Corso.class);
    }

    // Corso → CorsoFullDTO (ora gestisce solo docenteId)
    public CorsoFullDTO toFullDto(Corso corso) {
        if (corso == null) return null;
        return modelMapper.map(corso, CorsoFullDTO.class);
    }

    // CorsoFullDTO → Corso
    public Corso toEntity(CorsoFullDTO fullDTO) {
        if (fullDTO == null) return null;

        if (fullDTO.getDocenteId() != null) {
            try {
                docenteClient.getDocenteById(fullDTO.getDocenteId());
            } catch (FeignException.NotFound e) {
                throw new EntityNotFoundException("Docente non trovato con id: " + fullDTO.getDocenteId());
            }
        }

        return modelMapper.map(fullDTO, Corso.class);
    }

    // Lista di Corso → Lista di CorsoDTO
    public List<CorsoDTO> toDtoList(List<Corso> corsi) {
        if (corsi == null) return List.of();
        return corsi.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Lista di Corso → Lista di CorsoFullDTO
    public List<CorsoFullDTO> toFullDtoList(List<Corso> corsi) {
        if (corsi == null) return List.of();
        return corsi.stream()
                .map(this::toFullDto)
                .collect(Collectors.toList());
    }
}