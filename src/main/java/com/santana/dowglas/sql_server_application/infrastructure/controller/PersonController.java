package com.santana.dowglas.sql_server_application.infrastructure.controller;

import com.santana.dowglas.sql_server_application.application.dto.PersonRequestDTO;
import com.santana.dowglas.sql_server_application.application.dto.PersonResponseDTO;
import com.santana.dowglas.sql_server_application.application.mapper.PersonMapper;
import com.santana.dowglas.sql_server_application.application.service.PersonService;
import com.santana.dowglas.sql_server_application.domain.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/people")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @GetMapping
    public ResponseEntity<List<PersonResponseDTO>> getAllPersons() {
        log.info("Request received: get all persons");
        List<PersonResponseDTO> personsDto = personMapper.toResponseDtoList(personService.getAllPersons());
        return ResponseEntity.ok(personsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> getPersonById(@PathVariable Integer id) {
        log.info("Request received: get person by id {}", id);
        try {
            Person person = personService.getPersonById(id);
            return ResponseEntity.ok(personMapper.toResponseDto(person));
        } catch (RuntimeException e) {
            log.error("Error getting person by id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/cpf/{cpfNumber}")
    public ResponseEntity<PersonResponseDTO> getPersonByCpf(@PathVariable String cpfNumber) {
        log.info("Request received: get person by cpfNumber {}", cpfNumber);
        Person person = personService.getPersonByCpf(cpfNumber);
        if (person == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(personMapper.toResponseDto(person));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<PersonResponseDTO>> getPersonsByCity(@PathVariable String city) {
        log.info("Request received: get persons by city {}", city);
        List<PersonResponseDTO> personsDto = personMapper.toResponseDtoList(personService.getPersonsByCity(city));
        return ResponseEntity.ok(personsDto);
    }

    @PostMapping
    public ResponseEntity<PersonResponseDTO> createPerson(@Valid @RequestBody PersonRequestDTO dto) {
        log.info("Request received: create person");
        Person newPerson = personService.createPerson(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personMapper.toResponseDto(newPerson));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> updatePerson(@PathVariable Integer id,
                                                          @Valid @RequestBody PersonRequestDTO dto) {
        log.info("Request received: update person id {}", id);
        try {
            Person updatedPerson = personService.updatePerson(id, dto);
            return ResponseEntity.ok(personMapper.toResponseDto(updatedPerson));
        } catch (RuntimeException e) {
            log.error("Error updating person id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        log.info("Request received: delete person id {}", id);
        try {
            personService.deletePerson(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error deleting person id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}