package com.elitesoftwarehouse.corsiAParte.mapper;

import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.DocenteDTO;
import com.elitesoftwarehouse.corsiAParte.model.entity.Corso;
import com.elitesoftwarehouse.corsiAParte.service.client.DocenteServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CorsoMapper {
    @Autowired
    private DocenteServiceClient docenteClient;

    public Corso toEntity(CorsoFullDTO dto) {
        Corso corso = new Corso();
        corso.setNome(dto.getNome());
        corso.setAnnoAccademico(dto.getAnnoAccademico());
        return corso;
    }

    public Mono<CorsoDTO> toDto(Corso corso) {
        CorsoDTO dto = new CorsoDTO();
        dto.setId(corso.getId());
        dto.setNome(corso.getNome());
        dto.setAnnoAccademico(corso.getAnnoAccademico());
        dto.setDocenteId(corso.getDocenteId());

        if (corso.getDocenteId() != null) {
            return docenteClient.getDocenteById(corso.getDocenteId())
                    .map(docente -> {
                        dto.setNomeDocente(docente.getNomeDocente());
                        dto.setCognomeDocente(docente.getCognomeDocente());
                        return dto;
                    })
                    .defaultIfEmpty(dto);
        }

        return Mono.just(dto);
    }
}