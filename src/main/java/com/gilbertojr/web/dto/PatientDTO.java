package com.gilbertojr.web.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientDTO {


    @NotBlank
    private String name;
    @NotBlank
    @CPF
    private String cpf;
    @NotBlank
    private LocalDate dateOfBirth;
    @NotBlank
    private String sex;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
}
