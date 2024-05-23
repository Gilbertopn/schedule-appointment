package com.gilbertojr.builder;

import com.gilbertojr.web.dto.PatientDTO;
import com.gilbertojr.model.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientMapper {

    private final ModelMapper modelMapper;

    public PatientMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PatientDTO toDTO(Patient model) {
        return modelMapper.map(model, PatientDTO.class);
    }

    public Patient toEntity(PatientDTO dto) {
        return modelMapper.map(dto, Patient.class);
    }

    public List<PatientDTO> toListDTO(List<Patient> modelList) {
        return modelList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Patient> toList(List<PatientDTO> dtosList) {
        return dtosList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
