package com.gilbertojr.builder;

import com.gilbertojr.web.dto.ExamDTO;
import com.gilbertojr.model.Exam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExamMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public ExamMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ExamDTO toDTO(Exam model){
        return modelMapper.map(model, ExamDTO.class);
    }

    public Exam toEntity(ExamDTO dto){
        return modelMapper.map(dto, Exam.class);
    }

    public List<ExamDTO> toListDTO(List<Exam> modelList){
        return modelList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Exam> toList(List<ExamDTO> dtoList){
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
