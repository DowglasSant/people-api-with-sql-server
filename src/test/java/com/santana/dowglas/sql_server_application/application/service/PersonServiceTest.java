package com.santana.dowglas.sql_server_application.application.service;

import com.santana.dowglas.sql_server_application.application.dto.AddressDTO;
import com.santana.dowglas.sql_server_application.application.dto.PersonRequestDTO;
import com.santana.dowglas.sql_server_application.application.mapper.PersonMapper;
import com.santana.dowglas.sql_server_application.domain.model.Address;
import com.santana.dowglas.sql_server_application.domain.model.Person;
import com.santana.dowglas.sql_server_application.domain.port.PersonRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepositoryPort personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    private Person person;
    private PersonRequestDTO personRequestDTO;

    @BeforeEach
    void setUp() {
        Address address = Address.builder()
                .city("New York")
                .state("NY")
                .country("USA")
                .neighborhood("Manhattan")
                .zipcode("10001")
                .houseNumber("123")
                .build();

        person = Person.builder()
                .id(1)
                .cpfNumber("12345678901")
                .email("john.doe@example.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .phone("123456789")
                .name("John Doe")
                .address(address)
                .build();

        personRequestDTO = PersonRequestDTO.builder()
                .name("John Doe")
                .cpfNumber("12345678901") // Removido os pontos
                .email("john.doe@example.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .phone("123456789")
                .address(AddressDTO.builder()
                        .city("New York")
                        .state("NY")
                        .country("USA")
                        .neighborhood("Manhattan")
                        .zipcode("10001")
                        .houseNumber("123")
                        .build())
                .build();
    }

    @Test
    void testGetAllPersons() {
        when(personRepository.findAll()).thenReturn(List.of(person));
        List<Person> result = personService.getAllPersons();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testGetPersonByIdFound() {
        when(personRepository.findById(1)).thenReturn(Optional.of(person));
        Person result = personService.getPersonById(1);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testGetPersonByIdNotFound() {
        when(personRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> personService.getPersonById(1));
    }

    @Test
    void testGetPersonByCpfFound() {
        when(personRepository.findByCpfNumber("12345678901")).thenReturn(Optional.of(person));
        Person result = personService.getPersonByCpf("12345678901");
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testGetPersonByCpfNotFound() {
        when(personRepository.findByCpfNumber("12345678901")).thenReturn(Optional.empty());
        Person result = personService.getPersonByCpf("12345678901");
        assertNull(result);
    }

    @Test
    void testCreatePerson() {
        when(personMapper.toEntity(personRequestDTO)).thenReturn(person);
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person result = personService.createPerson(personRequestDTO);
        assertNotNull(result);
        assertEquals("12345678901", result.getCpfNumber());
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void testUpdatePerson() {
        Person updatedPerson = Person.builder()
                .cpfNumber("12345678901")
                .email("updated@example.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .phone("987654321")
                .name("John Updated")
                .address(person.getAddress())
                .build();

        when(personRepository.findById(1)).thenReturn(Optional.of(person));
        when(personMapper.toEntity(personRequestDTO)).thenReturn(updatedPerson);
        when(personRepository.save(any(Person.class))).thenReturn(updatedPerson);

        Person result = personService.updatePerson(1, personRequestDTO);
        assertNotNull(result);
        assertEquals("John Updated", result.getName());
        assertEquals("987654321", result.getPhone());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testDeletePerson() {
        when(personRepository.findById(1)).thenReturn(Optional.of(person));
        doNothing().when(personRepository).deleteById(1);

        personService.deletePerson(1);
        verify(personRepository, times(1)).deleteById(1);
    }
}