package com.gilbertojr.service;


import com.gilbertojr.web.dto.AppointmentDTO;
import com.gilbertojr.exception.AppointmentTimeNotAvailableException;
import com.gilbertojr.exception.InvalidNameOrCpfException;
import com.gilbertojr.exception.PatientNotFoundException;
import com.gilbertojr.model.Appointment;
import com.gilbertojr.model.ExamType;
import com.gilbertojr.model.Patient;
import com.gilbertojr.repository.AppointmentRepository;
import com.gilbertojr.repository.ExamTypeRepository;
import com.gilbertojr.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ExamTypeRepository examTypeRepository;

    // Outros métodos

    @Transactional
    public AppointmentDTO scheduleAppointment(String nameOrCpf, Long examTypeId, LocalDate date, LocalTime time) {
        Patient patient = findPatient(nameOrCpf);

        if (patient == null) {
            throw new PatientNotFoundException("Nome ou CPF inválido");
        }

        ExamType examType = examTypeRepository.findById(examTypeId)
                .orElseThrow(() -> new RuntimeException("Tipo de exame não encontrado"));

        verifyAppointmentAvailability(date, time);

        String protocolNumber = generateProtocolNumber();

        Appointment savedAppointment = saveAppointment(patient, examType, date, time, protocolNumber);

        return buildAppointmentDTO(savedAppointment);
    }

    private void verifyAppointmentAvailability(LocalDate date, LocalTime time) {
        List<Appointment> appointmentsAtDateTime = appointmentRepository.findByDateAndTime(date, time);
        if (!appointmentsAtDateTime.isEmpty()) {
            throw new AppointmentTimeNotAvailableException("Horário de consulta não disponível");
        }

        if (!isWithinAllowedInterval(time) || !isWeekday(date)) {
            throw new AppointmentTimeNotAvailableException("Data ou horário inválido. Agende apenas de segunda a sexta e dentro do horário permitido.");
        }
    }

    @Transactional(readOnly = true)
    public AppointmentDTO getAppointmentInfo(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        return buildAppointmentDTO(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream()
                .map(this::buildAppointmentDTO)
                .collect(Collectors.toList());
    }

    private Patient findPatient(String nameOrCpf) {
        return patientRepository.findPatientByNameOrCPF(nameOrCpf);
    }


    private boolean isWithinAllowedInterval(LocalTime time) {
        LocalTime startInterval = LocalTime.of(8, 0);
        LocalTime endInterval = LocalTime.of(17, 0);
        return !time.isBefore(startInterval) && !time.isAfter(endInterval);
    }

    private boolean isWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    private String generateProtocolNumber() {
        int length = 12;
        StringBuilder protocolNumber = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            protocolNumber.append(random.nextInt(10));
        }
        return protocolNumber.toString();
    }

    private Appointment saveAppointment(Patient patient, ExamType examType, LocalDate date, LocalTime time, String protocolNumber) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setExamType(examType);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setProtocolNumber(protocolNumber);
        return appointmentRepository.save(appointment);
    }

    private AppointmentDTO buildAppointmentDTO(Appointment appointment) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setPatientDetailsDTO(appointment.getPatient().getName());
        appointmentDTO.setExamTypeDTOResponse(appointment.getExamType().getName(), appointment.getExamType().getDescription(), appointment.getExamType().getExams());
        appointmentDTO.setAppointmentDate(appointment.getDate());
        appointmentDTO.setAppointmentTime(appointment.getTime());
        appointmentDTO.setProtocolNumber(appointment.getProtocolNumber());
        return appointmentDTO;
    }
}