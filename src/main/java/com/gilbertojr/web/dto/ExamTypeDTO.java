package com.gilbertojr.web.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExamTypeDTO {


    @NotBlank
    private String name;
    private String description;

    private List<ExamDTO> exams = new ArrayList<>();

}
