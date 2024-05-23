package com.gilbertojr.web.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExamDTO {

    private Long id;
    @NotBlank
    private String name;
    private String observations;
}
