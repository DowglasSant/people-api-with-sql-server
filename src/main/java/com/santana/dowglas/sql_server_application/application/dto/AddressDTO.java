package com.santana.dowglas.sql_server_application.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private String city;
    private String state;
    private String country;
    private String neighborhood;
    private String zipcode;
    private String houseNumber;
}