package com.gilbertojr.web.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientDetailsDTO {


    @NotBlank
    private String name;

}
