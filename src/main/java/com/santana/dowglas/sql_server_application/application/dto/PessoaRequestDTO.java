package com.santana.dowglas.sql_server_application.application.dto;

import com.santana.dowglas.sql_server_application.application.dto.EnderecoDTO;
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
public class PessoaRequestDTO {
    @NotBlank
    private String cpf;

    @NotBlank
    private String email;

    @NotNull
    private LocalDate dataNascimento;

    @NotBlank
    private String telefone;

    private EnderecoDTO endereco;
}