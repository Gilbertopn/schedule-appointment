package com.gilbertojr.web.dto;

import com.gilbertojr.model.Exam;
import com.gilbertojr.model.ExamType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppointmentDTO {


    @NotBlank
    private PatientDetailsDTO patientDetailsDTO;

    @NotBlank
    private ExamTypeDTO examTypeDTO;

    @NotBlank
    private LocalDate appointmentDate;

    @NotBlank
    private LocalTime appointmentTime;

    private String protocolNumber;

    public void setPatientDetailsDTO(String name) {
        if (this.patientDetailsDTO == null) {
            this.patientDetailsDTO = new PatientDetailsDTO();
        }
        this.patientDetailsDTO.setName(name);
    }

    public void setExamTypeDTO(ExamType examType) {
        if (this.examTypeDTO == null) {
            this.examTypeDTO = new ExamTypeDTO();
        }
        this.examTypeDTO.setName(examType.getName());
        this.examTypeDTO.setDescription(examType.getDescription());
    }




    public void setExamTypeDTOResponse(String name, String description, List<Exam> exams) {
        if (this.examTypeDTO == null) {
            this.examTypeDTO = new ExamTypeDTO();
        }
        this.examTypeDTO.setName(name);
        this.examTypeDTO.setDescription(description);


    }


    public void setExamTypeDTOgetId(String name, String description, List<Exam> exams) {
        if (this.examTypeDTO == null) {
            this.examTypeDTO = new ExamTypeDTO();
        }
        this.examTypeDTO.setName(name);
        this.examTypeDTO.setDescription(description);

    }

}
