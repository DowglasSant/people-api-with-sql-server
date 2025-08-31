package com.santana.dowglas.sql_server_application.infrastructure.repository;

import com.santana.dowglas.sql_server_application.domain.model.Person;
import com.santana.dowglas.sql_server_application.domain.port.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryAdapter implements PersonRepositoryPort {

    private final SpringDataPersonRepository springDataRepository;

    @Override
    public List<Person> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public Optional<Person> findById(Integer id) {
        return springDataRepository.findById(id);
    }

    @Override
    public Optional<Person> findByCpfNumber(String cpfNumber) {
        return springDataRepository.findByCpfNumber(cpfNumber);
    }

    @Override
    public List<Person> findByAddressCity(String city) {
        return springDataRepository.findByAddressCity(city);
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return springDataRepository.findByEmail(email);
    }

    @Override
    public Person save(Person person) {
        return springDataRepository.save(person);
    }

    @Override
    public void deleteById(Integer id) {
        springDataRepository.deleteById(id);
    }
}
