package com.gilbertojr.repository;

import com.gilbertojr.model.Appointment;
import com.gilbertojr.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDateAndTime(LocalDate date, LocalTime time);
}
