package com.santana.dowglas.sql_server_application.infrastructure.controller;

import com.santana.dowglas.sql_server_application.application.dto.ErrorResponseDTO;
import com.santana.dowglas.sql_server_application.application.dto.PersonRequestDTO;
import com.santana.dowglas.sql_server_application.application.dto.PersonResponseDTO;
import com.santana.dowglas.sql_server_application.application.mapper.PersonMapper;
import com.santana.dowglas.sql_server_application.application.service.PersonService;
import com.santana.dowglas.sql_server_application.domain.model.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all persons", description = "Retrieve a list of all persons in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of persons retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<?> getAllPersons() {
        log.info("Request received: get all persons");
        try {
            List<PersonResponseDTO> personsDto = personMapper.toResponseDtoList(personService.getAllPersons());
            return ResponseEntity.ok(personsDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Unexpected error: " + e.getMessage()));
        }
    }

    @Operation(summary = "Get person by ID", description = "Retrieve a person by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person found"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonById(
            @Parameter(description = "ID of the person to be retrieved", required = true)
            @PathVariable Integer id) {
        log.info("Request received: get person by id {}", id);
        try {
            Person person = personService.getPersonById(id);
            return ResponseEntity.ok(personMapper.toResponseDto(person));
        } catch (RuntimeException e) {
            log.error("Error getting person by id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("Person not found for id: " + id));
        }
    }

    @Operation(summary = "Get person by CPF", description = "Retrieve a person using their CPF number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person found"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @GetMapping("/cpf/{cpfNumber}")
    public ResponseEntity<?> getPersonByCpf(
            @Parameter(description = "CPF number of the person", required = true)
            @PathVariable String cpfNumber) {
        log.info("Request received: get person by cpfNumber {}", cpfNumber);
        try {
            Person person = personService.getPersonByCpf(cpfNumber);
            if (person == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("Person not found for CPF: " + cpfNumber));
            }
            return ResponseEntity.ok(personMapper.toResponseDto(person));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Unexpected error: " + e.getMessage()));
        }
    }

    @Operation(summary = "Get persons by city", description = "Retrieve all persons living in a specific city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of persons retrieved successfully")
    })
    @GetMapping("/city/{city}")
    public ResponseEntity<?> getPersonsByCity(
            @Parameter(description = "City to filter persons", required = true)
            @PathVariable String city) {
        log.info("Request received: get persons by city {}", city);
        try {
            List<PersonResponseDTO> personsDto = personMapper.toResponseDtoList(personService.getPersonsByCity(city));
            return ResponseEntity.ok(personsDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Unexpected error: " + e.getMessage()));
        }
    }

    @Operation(summary = "Create a new person", description = "Create a new person along with their address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person created successfully"),
            @ApiResponse(responseCode = "409", description = "Conflict - email or CPF already in use")
    })
    @PostMapping
    public ResponseEntity<?> createPerson(@Valid @RequestBody PersonRequestDTO dto) {
        log.info("Request received: create person");
        try {
            Person newPerson = personService.createPerson(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(personMapper.toResponseDto(newPerson));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Unexpected error: " + e.getMessage()));
        }
    }

    @Operation(summary = "Update an existing person", description = "Update details of a person by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person updated successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - email or CPF already in use")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable Integer id,
                                          @Valid @RequestBody PersonRequestDTO dto) {
        log.info("Request received: update person id {}", id);
        try {
            Person updatedPerson = personService.updatePerson(id, dto);
            return ResponseEntity.ok(personMapper.toResponseDto(updatedPerson));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("Person not found for id: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Unexpected error: " + e.getMessage()));
        }
    }

    @Operation(summary = "Delete a person", description = "Delete a person by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Person deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(
            @Parameter(description = "ID of the person to delete", required = true)
            @PathVariable Integer id) {
        log.info("Request received: delete person id {}", id);
        try {
            personService.deletePerson(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("Person not found for id: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Unexpected error: " + e.getMessage()));
        }
    }
}