package com.elitesoftwarehouse.corsiAParte.converter;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.data.entity.Corso;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CorsoConverter {

    @Autowired
     ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }
    public CorsoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Corso → CorsoDTO
    public CorsoDTO toDto(Corso corso) {
        return modelMapper.map(corso, CorsoDTO.class);
    }

    // CorsoDTO → Corso
    public Corso toEntity(CorsoDTO corsoDTO) {
        return modelMapper.map(corsoDTO, Corso.class);
    }

    // Corso → CorsoFullDTO
    public CorsoFullDTO toFullDto(Corso corso) {
        return modelMapper.map(corso, CorsoFullDTO.class);
    }

    // CorsoFullDTO → Corso
    public Corso toEntity(CorsoFullDTO fullDTO) {
        return modelMapper.map(fullDTO, Corso.class);
    }

    // Lista di Corso → Lista di CorsoDTO
    public List<CorsoDTO> toDtoList(List<Corso> corsi) {
        return corsi.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Lista di Corso → Lista di CorsoFullDTO
    public List<CorsoFullDTO> toFullDtoList(List<Corso> corsi) {
        return corsi.stream()
                .map(this::toFullDto)
                .collect(Collectors.toList());
    }
}
