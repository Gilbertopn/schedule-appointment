package com.gilbertojr.web.controller;

import com.gilbertojr.web.dto.AppointmentDTO;
import com.gilbertojr.exception.AppointmentTimeNotAvailableException;
import com.gilbertojr.exception.PatientNotFoundException;
import com.gilbertojr.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;


    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleAppointment(
            @RequestParam(required = false) String nameOrCpf,
            @RequestParam Long examTypeId,
            @RequestParam String date,
            @RequestParam String time) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            LocalTime parsedTime = LocalTime.parse(time);
            AppointmentDTO appointment = appointmentService.scheduleAppointment(nameOrCpf, examTypeId, parsedDate, parsedTime);
            return ResponseEntity.ok(appointment);
        } catch (PatientNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Paciente não encontrado. Redirecionando para a tela de cadastro...");
        } catch (AppointmentTimeNotAvailableException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointmentDTOs = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointmentDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentInfo(@PathVariable Long id) {
        try {
            AppointmentDTO appointmentDTO = appointmentService.getAppointmentInfo(id);
            return ResponseEntity.ok(appointmentDTO);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}



