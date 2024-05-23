package com.gilbertojr.service;

import com.gilbertojr.builder.ExamTypeMapper;
import com.gilbertojr.web.dto.ExamTypeDTO;
import com.gilbertojr.model.ExamType;
import com.gilbertojr.repository.ExamRepository;
import com.gilbertojr.repository.ExamTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ExamTypeService {

    private final ExamRepository examRepository;
    private final ExamTypeRepository examTypeRepository;
    private final ExamTypeMapper examTypeMapper;


    public ExamTypeDTO save(ExamTypeDTO questionDTO) {
        try {
            ExamType questionEntity = examTypeRepository.save(examTypeMapper.toEntity(questionDTO));
            return examTypeMapper.toDTO(questionEntity);
        } catch (DataAccessException ex) {
            ex.getStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocorreu um erro ao tentar salvar a QuestionDTO",ex);
        }
    }

    @Transactional
    public List<ExamTypeDTO> saveAll(List<ExamTypeDTO> examTypeDTOList) {
        try {
            List<ExamType> examTypeToSave = examTypeMapper.toList(examTypeDTOList);
            List<ExamType> examTypesaved = examTypeRepository.saveAll(examTypeToSave);

            System.out.println("Iniciando leitura das alternativas de cada questão para em cada alternativa inserir a propria questão. Só assim o hibernate vai saber inserir os dados do id da questão em alternativa.");
            examTypesaved.forEach(examType -> {
                examType.getExams().forEach(alternative -> alternative.setExamType(examType));
                examRepository.saveAll(examType.getExams());
            });
            return examTypeMapper.toListDTO(examTypesaved);
        } catch (Exception ex) {
            ex.getStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msgError("saveAll"), ex);
        }
    }


    @Transactional
    public ExamTypeDTO findById(Long id) {
       ExamType examType = examTypeRepository.findById(id).orElseThrow(
               ()-> new RuntimeException("Tipo do Exame não encontrado.")
       );

        return examTypeMapper.toDTO(examType);
    }



    @Transactional
    public List<ExamTypeDTO> findAll() {
        return examTypeMapper.toListDTO(examTypeRepository.findAll());
    }

    @Transactional
    public ExamTypeDTO update(Long id, ExamType updatedExamType) {
        ExamType existingExamType = examTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo do exame não encontrado com o ID: " + id));

        if (updatedExamType.getName() != null) {
            existingExamType.setName(updatedExamType.getName());
        }
        if (updatedExamType.getDescription() != null) {
            existingExamType.setDescription(updatedExamType.getDescription());
        }

        return examTypeMapper.toDTO(examTypeRepository.save(existingExamType));
    }

    private String msgError(String method){
        return "Ocorreu um erro em ExamService ao tentar fazer a operação no método: "  + method;
    }
}
