package com.gilbertojr.service;

import com.gilbertojr.builder.ExamMapper;
import com.gilbertojr.web.dto.ExamDTO;
import com.gilbertojr.model.Exam;
import com.gilbertojr.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamService {


    private final ExamRepository examRepository;
    private final ExamMapper examMapper;


    public ExamDTO save(ExamDTO questionDTO) {
        try {
            Exam questionEntity = examRepository.save(examMapper.toEntity(questionDTO));
            return examMapper.toDTO(questionEntity);
        } catch (DataAccessException ex) {
            ex.getStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocorreu um erro ao tentar salvar a QuestionDTO",ex);
        }
    }



    @Transactional
    public ExamDTO findById(Long id) {
       Exam exam = examRepository.findById(id).orElseThrow(
               ()-> new RuntimeException("Tipo do Exame não encontrado.")
       );

        return examMapper.toDTO(exam);
    }

    @Transactional
    public List<ExamDTO> findAll() {
        return examMapper.toListDTO(examRepository.findAll());
    }

    @Transactional
    public ExamDTO update(Long id, Exam updatedExam) {
        Exam existingExam = examRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo do exame não encontrado com o ID: " + id));

        if (updatedExam.getName() != null) {
            existingExam.setName(updatedExam.getName());
        }
        if (updatedExam.getObservations() != null) {
            existingExam.setObservations(updatedExam.getObservations());
        }

        return examMapper.toDTO(examRepository.save(existingExam));
    }
}
