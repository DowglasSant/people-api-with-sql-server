package com.santana.dowglas.sql_server_application.domain.port;

import com.santana.dowglas.sql_server_application.domain.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepositoryPort {

    List<Person> findAll();

    Optional<Person> findById(Integer id);

    Optional<Person> findByCpfNumber(String cpfNumber);

    Optional<Person> findByEmail(String email);

    List<Person> findByAddressCity(String city);

    Person save(Person person);

    void deleteById(Integer id);
}