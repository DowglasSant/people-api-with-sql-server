package com.santana.dowglas.sql_server_application.application.mapper;

import com.santana.dowglas.sql_server_application.domain.model.Address;
import com.santana.dowglas.sql_server_application.domain.model.Person;
import com.santana.dowglas.sql_server_application.application.dto.PersonRequestDTO;
import com.santana.dowglas.sql_server_application.application.dto.PersonResponseDTO;
import com.santana.dowglas.sql_server_application.application.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person toEntity(PersonRequestDTO dto);

    @Mapping(target = "id", source = "id")
    PersonResponseDTO toResponseDto(Person person);

    Address toEntity(AddressDTO dto);

    AddressDTO toDto(Address address);

    List<PersonResponseDTO> toResponseDtoList(List<Person> people);

    List<Person> toEntityList(List<PersonRequestDTO> dtos);
}
