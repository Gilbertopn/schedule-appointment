package com.gilbertojr.repository;

import com.gilbertojr.model.Exam;
import com.gilbertojr.model.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

}
