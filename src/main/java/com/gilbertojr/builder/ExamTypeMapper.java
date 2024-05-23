package com.gilbertojr.builder;

import com.gilbertojr.web.dto.ExamTypeDTO;
import com.gilbertojr.model.ExamType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExamTypeMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public ExamTypeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ExamTypeDTO toDTO(ExamType model){
        return modelMapper.map(model, ExamTypeDTO.class);
    }

    public ExamType toEntity(ExamTypeDTO dto){
        return modelMapper.map(dto, ExamType.class);
    }

    public List<ExamTypeDTO> toListDTO(List<ExamType> modelList){
        return modelList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ExamType> toList(List<ExamTypeDTO> dtoList){
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
