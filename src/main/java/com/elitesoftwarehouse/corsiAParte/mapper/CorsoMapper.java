package com.elitesoftwarehouse.corsiAParte.mapper;

import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.model.entity.Corso;
import org.springframework.stereotype.Component;

@Component
public class CorsoMapper {

    public Corso toEntity(CorsoFullDTO dto) {
        Corso corso = new Corso();
        updateCorsoFromDTO(dto, corso);
        return corso;
    }

    public CorsoDTO toDto(Corso entity) {
        CorsoDTO dto = new CorsoDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setAnnoAccademico(entity.getAnnoAccademico());
        dto.setDocenteId(entity.getDocenteId());
        return dto;
    }

    public void updateCorsoFromDTO(CorsoFullDTO dto, Corso corso) {
        corso.setNome(dto.getNome());
        corso.setAnnoAccademico(dto.getAnnoAccademico());
        corso.setDocenteId(dto.getDocenteId());
    }
}