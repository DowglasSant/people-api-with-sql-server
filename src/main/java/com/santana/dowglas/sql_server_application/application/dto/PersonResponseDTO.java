package com.santana.dowglas.sql_server_application.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonResponseDTO {
    private Integer id;
    private String name;
    private String cpfNumber;
    private String email;
    private LocalDate birthDate;
    private String phone;
    private AddressDTO address;
}