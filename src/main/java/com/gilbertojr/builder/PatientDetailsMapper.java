package com.gilbertojr.builder;

import com.gilbertojr.web.dto.PatientDetailsDTO;
import com.gilbertojr.model.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientDetailsMapper {

    private final ModelMapper modelMapper;

    public PatientDetailsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PatientDetailsDTO toDTO(Patient model) {
        return modelMapper.map(model, PatientDetailsDTO.class);
    }

    public Patient toEntity(PatientDetailsDTO dto) {
        return modelMapper.map(dto, Patient.class);
    }

    public List<PatientDetailsDTO> toListDTO(List<Patient> modelList) {
        return modelList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Patient> toList(List<PatientDetailsDTO> dtosList) {
        return dtosList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
