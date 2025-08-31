package com.santana.dowglas.sql_server_application.infrastructure.repository;

import com.santana.dowglas.sql_server_application.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataPersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByCpfNumber(String cpfNumber);

    List<Person> findByAddressCity(String city);

    Optional<Person> findByEmail(String email);
}
