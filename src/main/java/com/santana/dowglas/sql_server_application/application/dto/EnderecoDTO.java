package com.santana.dowglas.sql_server_application.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoDTO {
    private String cidade;
    private String estado;
    private String pais;
    private String bairro;
    private String cep;
    private String numeroResidencia;
}
