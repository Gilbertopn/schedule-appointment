package com.gilbertojr.builder;

import com.gilbertojr.web.dto.AppointmentDTO;
import com.gilbertojr.model.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public AppointmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AppointmentDTO toDTO(Appointment model){
        return modelMapper.map(model, AppointmentDTO.class);
    }

    public Appointment toEntity(AppointmentDTO dto){
        return modelMapper.map(dto, Appointment.class);
    }

    public List<AppointmentDTO> toListDTO(List<Appointment> modelList){
        return modelList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Appointment> toList(List<AppointmentDTO> dtoList){
        return   dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
