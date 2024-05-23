package com.gilbertojr.web.controller;

import com.gilbertojr.web.dto.PatientDTO;
import com.gilbertojr.exception.PatientNotFoundException;
import com.gilbertojr.model.Patient;
import com.gilbertojr.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientDTO> create(@RequestBody Patient patient){
        PatientDTO dto = patientService.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getById(@PathVariable Long id){
        PatientDTO dto = patientService.findById(id);
        return ResponseEntity.ok(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        try {
            PatientDTO updatedDTO = patientService.update(id, patient);
            return ResponseEntity.ok(updatedDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAll(){
        List<PatientDTO> patients = patientService.findAll();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/search")
    public ResponseEntity<PatientDTO> searchByNameOrCpf(@RequestParam String nameOrCpf) {
        try {
            PatientDTO patientDTO = patientService.findByFullNameOrCpf(nameOrCpf);
            return ResponseEntity.ok(patientDTO);
        } catch (PatientNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
