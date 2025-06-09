package com.elitesoftwarehouse.corsiAParte.mapper;

import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.DocenteDTO;
import com.elitesoftwarehouse.corsiAParte.model.entity.Corso;
import com.elitesoftwarehouse.corsiAParte.service.client.DocenteServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CorsoMapper {
    @Autowired
    private DocenteServiceClient docenteClient;

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

        if (entity.getDocenteId() != null) {
            try {
                DocenteDTO docente = docenteClient.getDocenteById(entity.getDocenteId());
                if (docente != null) {
                    dto.setNomeDocente(docente.getNomeDocente());
                    dto.setCognomeDocente(docente.getCognomeDocente());
                }
            } catch (Exception e) {
                // Log the error or ignore it gracefully
            }
        }

        return dto;
    }

    public void updateCorsoFromDTO(CorsoFullDTO dto, Corso corso) {
        corso.setNome(dto.getNome());
        corso.setAnnoAccademico(dto.getAnnoAccademico());
    }
}