package com.gilbertojr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@SequenceGenerator(name = "tb_exam_type_seq", allocationSize = 1)
@Table(name = "exam_types")
public class ExamType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_exam_type_seq")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;


    @Column(name = "description", length = 256)
    private String description;

    @OneToMany(mappedBy = "examType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Exam> exams = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamType examType = (ExamType) o;
        return Objects.equals(id, examType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ExamType{" +
                "id=" + id +
                '}';
    }
}


