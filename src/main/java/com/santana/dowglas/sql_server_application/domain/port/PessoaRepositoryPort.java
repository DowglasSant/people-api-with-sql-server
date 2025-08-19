package com.santana.dowglas.sql_server_application.domain.port;

import com.santana.dowglas.sql_server_application.domain.model.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaRepositoryPort {
    List<Pessoa> findAll();
    Optional<Pessoa> findById(Integer id);
    Optional<Pessoa> findByCpf(String cpf);
    List<Pessoa> findByEnderecoCidade(String cidade);
    Pessoa save(Pessoa pessoa);
    void deleteById(Integer id);
}
