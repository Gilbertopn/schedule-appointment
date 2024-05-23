package com.gilbertojr.web.controller;

import com.gilbertojr.web.dto.ExamDTO;
import com.gilbertojr.model.Exam;
import com.gilbertojr.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/exam")
public class ExamController {


    private final ExamService examService;

        @PostMapping
        public ResponseEntity<ExamDTO> create(@RequestBody ExamDTO exam){
            ExamDTO dto = examService.save(exam);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ExamDTO> getById(@PathVariable Long id){
            ExamDTO dto = examService.findById(id);
            return ResponseEntity.ok(dto);
        }
        @PutMapping("/{id}")
        public ResponseEntity<ExamDTO> updatePatient(@PathVariable Long id, @RequestBody Exam exam) {
            try {
                ExamDTO updatedDTO = examService.update(id, exam);
                return ResponseEntity.ok(updatedDTO);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @GetMapping
        public ResponseEntity<List<ExamDTO>> getAll(){
            List<ExamDTO> exams = examService.findAll();
            return ResponseEntity.ok(exams);
        }

    }

