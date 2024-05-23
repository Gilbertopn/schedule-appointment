package com.gilbertojr.web.controller;

import com.gilbertojr.web.dto.ExamTypeDTO;
import com.gilbertojr.model.ExamType;
import com.gilbertojr.service.ExamTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/exam-type")
public class ExamTypeController {


    private final ExamTypeService examTypeService;

    @PostMapping
    public ResponseEntity<ExamTypeDTO> create(@RequestBody ExamTypeDTO examType) {
        ExamTypeDTO dto = examTypeService.save(examType);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("save/list")
    public List<ExamTypeDTO> save(@RequestBody List<ExamTypeDTO> examTypeDTOList) {
        return examTypeService.saveAll(examTypeDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamTypeDTO> getById(@PathVariable Long id) {
        ExamTypeDTO examType = examTypeService.findById(id);
        return ResponseEntity.ok(examType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamTypeDTO> updatePatient(@PathVariable Long id, @RequestBody ExamType examType) {
        try {
            ExamTypeDTO updatedDTO = examTypeService.update(id, examType);
            return ResponseEntity.ok(updatedDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ExamTypeDTO>> getAll() {
        List<ExamTypeDTO> examTypes = examTypeService.findAll();
        return ResponseEntity.ok(examTypes);
    }

}

