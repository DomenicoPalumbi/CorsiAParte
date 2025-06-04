package com.elitesoftwarehouse.corsiAParte.converter;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.data.entity.Corso;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configurazione per la conversione Corso -> CorsoDTO
        modelMapper.createTypeMap(Corso.class, CorsoDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Corso::getDocenteId, CorsoDTO::setDocenteId);
                });

        // Configurazione per la conversione CorsoDTO -> Corso
        modelMapper.createTypeMap(CorsoDTO.class, Corso.class)
                .addMappings(mapper -> {
                    mapper.map(CorsoDTO::getDocenteId, Corso::setDocenteId);
                });

        return modelMapper;
    }
}