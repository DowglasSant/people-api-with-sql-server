package com.santana.dowglas.sql_server_application.infrastructure.repository;

import com.santana.dowglas.sql_server_application.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataPessoaRepository extends JpaRepository<Pessoa, Integer> {
    Optional<Pessoa> findByCpf(String cpf);
    List<Pessoa> findByEnderecoCidade(String cidade);
}
