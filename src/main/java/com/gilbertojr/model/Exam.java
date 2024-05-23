package com.gilbertojr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@SequenceGenerator(name = "tb_exam_seq", allocationSize = 1)
@Table(name = "tb_exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_exam_seq")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;


    @Column(name ="observations",length = 1000)
    private String observations;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private ExamType examType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return Objects.equals(id, exam.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                '}';
    }
}