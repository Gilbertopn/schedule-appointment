package com.gilbertojr.service;

import com.gilbertojr.builder.PatientMapper;
import com.gilbertojr.web.dto.PatientDTO;
import com.gilbertojr.exception.CpfUniqueViolationException;
import com.gilbertojr.exception.PatientNotFoundException;
import com.gilbertojr.model.Patient;
import com.gilbertojr.repository.PatientRepository;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Transactional
    public PatientDTO save(Patient patientDTO) {
        try {
            return patientMapper.toDTO(patientRepository.save(patientDTO));
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(
                    String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", patientDTO.getCpf())
            );
        }
    }



    @Transactional
    public PatientDTO findById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Usuario não encontrado.")
        );
        return patientMapper.toDTO(patient);
    }

    @Transactional
    public List<PatientDTO> findAll() {
        return patientMapper.toListDTO(patientRepository.findAll());
    }

    @Transactional
    public PatientDTO update(Long id, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com o ID: " + id));

        if (updatedPatient.getName() != null) {
            existingPatient.setName(updatedPatient.getName());
        }
        if (updatedPatient.getCpf() != null) {
            existingPatient.setCpf(updatedPatient.getCpf());
        }
        if (updatedPatient.getDateOfBirth() != null) {
            existingPatient.setDateOfBirth(updatedPatient.getDateOfBirth());
        }
        if (updatedPatient.getSex() != null) {
            existingPatient.setSex(updatedPatient.getSex());
        }
        if (updatedPatient.getPhone() != null) {
            existingPatient.setPhone(updatedPatient.getPhone());
        }
        if (updatedPatient.getEmail() != null) {
            existingPatient.setEmail(updatedPatient.getEmail());
        }


        return patientMapper.toDTO(patientRepository.save(existingPatient));
    }

    @Transactional(readOnly = true)
    public PatientDTO findByFullNameOrCpf(String nameOrCpf) {
        Patient patient = null;
        // Verifica se o parâmetro é um CPF
        if (nameOrCpf.matches("\\d{11}")) {
            patient = patientRepository.findByCpf(nameOrCpf);
        } else {
            // Caso contrário, busca por nome
            List<Patient> patients = patientRepository.findByNameContainingIgnoreCase(nameOrCpf);
            if (!patients.isEmpty()) {
                // Se encontrar mais de um paciente com o mesmo nome, lança exceção
                if (patients.size() > 1) {
                    throw new NonUniqueResultException("Mais de um paciente encontrado com o mesmo nome. Especifique o CPF.");
                }
                patient = patients.get(0);
            }
        }
        if (patient == null) {
            throw new PatientNotFoundException("Paciente não encontrado.");
        }
        return patientMapper.toDTO(patient);
    }
}
