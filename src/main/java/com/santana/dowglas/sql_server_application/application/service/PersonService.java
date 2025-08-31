package com.santana.dowglas.sql_server_application.application.service;

import com.santana.dowglas.sql_server_application.application.dto.PersonRequestDTO;
import com.santana.dowglas.sql_server_application.application.mapper.PersonMapper;
import com.santana.dowglas.sql_server_application.application.utils.CpfUtils;
import com.santana.dowglas.sql_server_application.domain.model.Person;
import com.santana.dowglas.sql_server_application.domain.port.PersonRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepositoryPort personRepository;
    private final PersonMapper personMapper;

    public List<Person> getAllPersons() {
        try {
            return personRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching all persons", e);
            return Collections.emptyList();
        }
    }

    public Person getPersonById(Integer id) {
        return personRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Person not found - id: {}", id);
                    return new EntityNotFoundException("Person not found for id: " + id);
                });
    }

    public Person getPersonByCpf(String cpfNumber) {
        return personRepository.findByCpfNumber(cpfNumber).orElse(null);
    }

    public List<Person> getPersonsByCity(String city) {
        return personRepository.findByAddressCity(city);
    }

    public Person createPerson(PersonRequestDTO dto) {
        Person person = personMapper.toEntity(dto);
        person.setCpfNumber(CpfUtils.cleanCpf(person.getCpfNumber()));

        if (personRepository.findByEmail(person.getEmail()).isPresent()) {
            log.error("This email is currently in use.");
            throw new IllegalArgumentException("Email already in use");
        }

        if (personRepository.findByCpfNumber(person.getCpfNumber()).isPresent()) {
            log.error("This CPF is currently in use.");
            throw new IllegalArgumentException("CPF already in use");
        }

        if (person.getAddress() != null) {
            person.getAddress().setPerson(person);
        }

        return personRepository.save(person);
    }

    public Person updatePerson(Integer id, PersonRequestDTO dto) {
        Person existingPerson = getPersonById(id);
        Person updatedPerson = personMapper.toEntity(dto);

        String newEmail = dto.getEmail();
        String newCpf = CpfUtils.cleanCpf(dto.getCpfNumber());

        if (!existingPerson.getEmail().equalsIgnoreCase(newEmail)) {
            if (personRepository.findByEmail(newEmail).isPresent()) {
                log.error("This email is currently in use.");
                throw new IllegalArgumentException("Email already in use");
            }
        }

        if (!existingPerson.getCpfNumber().equals(newCpf)) {
            if (personRepository.findByCpfNumber(newCpf).isPresent()) {
                log.error("This CPF is currently in use.");
                throw new IllegalArgumentException("CPF already in use");
            }
        }

        updatedPerson.setCpfNumber(newCpf);

        updateFields(existingPerson, updatedPerson);

        return personRepository.save(existingPerson);
    }

    public void deletePerson(Integer id) {
        getPersonById(id);
        personRepository.deleteById(id);
    }

    private void updateFields(Person existing, Person updated) {
        existing.setCpfNumber(updated.getCpfNumber());
        existing.setEmail(updated.getEmail());
        existing.setBirthDate(updated.getBirthDate());
        existing.setPhone(updated.getPhone());
        existing.setName(updated.getName());

        if (updated.getAddress() != null) {
            if (existing.getAddress() != null) {
                existing.getAddress().setCity(updated.getAddress().getCity());
                existing.getAddress().setState(updated.getAddress().getState());
                existing.getAddress().setCountry(updated.getAddress().getCountry());
                existing.getAddress().setNeighborhood(updated.getAddress().getNeighborhood());
                existing.getAddress().setZipcode(updated.getAddress().getZipcode());
                existing.getAddress().setHouseNumber(updated.getAddress().getHouseNumber());
            } else {
                updated.getAddress().setPerson(existing);
                existing.setAddress(updated.getAddress());
            }
        }
    }
}
