package com.gilbertojr.repository;

import com.gilbertojr.model.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExamTypeRepository extends JpaRepository<ExamType, Long> {

    @Query("SELECT et FROM ExamType et LEFT JOIN FETCH et.exams")
    List<ExamType> findAllWithExams();

}
