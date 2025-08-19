package com.santana.dowglas.sql_server_application.infrastructure.repository;

import com.santana.dowglas.sql_server_application.domain.model.Pessoa;
import com.santana.dowglas.sql_server_application.domain.port.PessoaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PessoaRepositoryAdapter implements PessoaRepositoryPort {

    private final SpringDataPessoaRepository springDataRepository;

    @Override
    public List<Pessoa> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public Optional<Pessoa> findById(Integer id) {
        return springDataRepository.findById(id);
    }

    @Override
    public Optional<Pessoa> findByCpf(String cpf) {
        return springDataRepository.findByCpf(cpf);
    }

    @Override
    public List<Pessoa> findByEnderecoCidade(String cidade) {
        return springDataRepository.findByEnderecoCidade(cidade);
    }

    @Override
    public Pessoa save(Pessoa pessoa) {
        return springDataRepository.save(pessoa);
    }

    @Override
    public void deleteById(Integer id) {
        springDataRepository.deleteById(id);
    }
}
