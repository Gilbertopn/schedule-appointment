package com.gilbertojr.repository;

import com.gilbertojr.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {


    Patient findByCpf(String cpf);


    @Query("SELECT p FROM Patient p WHERE p.name = :nameOrCpf OR p.cpf = :nameOrCpf")
    Patient findPatientByNameOrCPF(@Param("nameOrCpf") String nameOrCpf);


    List<Patient> findByNameContainingIgnoreCase(String nameOrCpf);
}
