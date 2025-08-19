package com.santana.dowglas.sql_server_application.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String cpfNumber;

    @NotBlank
    private String email;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    private String phone;

    private AddressDTO address;
}