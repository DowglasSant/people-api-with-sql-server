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
public class PessoaResponseDTO {
    private Integer id;
    private String cpf;
    private String email;
    private LocalDate dataNascimento;
    private String telefone;
    private EnderecoDTO endereco;
}
