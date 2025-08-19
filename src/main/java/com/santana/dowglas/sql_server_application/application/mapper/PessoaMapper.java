package com.santana.dowglas.sql_server_application.application.mapper;

import com.santana.dowglas.sql_server_application.domain.model.Pessoa;
import com.santana.dowglas.sql_server_application.domain.model.Endereco;
import com.santana.dowglas.sql_server_application.application.dto.PessoaRequestDTO;
import com.santana.dowglas.sql_server_application.application.dto.PessoaResponseDTO;
import com.santana.dowglas.sql_server_application.application.dto.EnderecoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    Pessoa toEntity(PessoaRequestDTO dto);

    @Mapping(target = "id", source = "id")
    PessoaResponseDTO toResponseDto(Pessoa pessoa);

    Endereco toEntity(EnderecoDTO dto);

    EnderecoDTO toDto(Endereco endereco);

    List<PessoaResponseDTO> toResponseDtoList(List<Pessoa> pessoas);

    List<Pessoa> toEntityList(List<PessoaRequestDTO> dtos);
}
