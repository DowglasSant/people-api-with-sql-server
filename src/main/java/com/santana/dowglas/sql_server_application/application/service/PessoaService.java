package com.santana.dowglas.sql_server_application.application.service;

import com.santana.dowglas.sql_server_application.application.dto.PessoaRequestDTO;
import com.santana.dowglas.sql_server_application.application.mapper.PessoaMapper;
import com.santana.dowglas.sql_server_application.application.utils.CpfUtils;
import com.santana.dowglas.sql_server_application.domain.model.Pessoa;
import com.santana.dowglas.sql_server_application.domain.port.PessoaRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepositoryPort pessoaRepository;
    private final PessoaMapper pessoaMapper;

    public List<Pessoa> listarTodasPessoas() {
        try {
            return pessoaRepository.findAll();
        } catch (Exception e) {
            log.error("Erro ao buscar todas as pessoas", e);
            return Collections.emptyList();
        }
    }

    public Pessoa buscarPessoaPorId(Integer id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pessoa não encontrada - id: {}", id);
                    return new EntityNotFoundException("Pessoa não encontrada para o id: " + id);
                });
    }

    public Pessoa buscarPessoaPorCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf).orElse(null);
    }

    public List<Pessoa> buscarPessoaPorCidade(String cidade) {
        return pessoaRepository.findByEnderecoCidade(cidade);
    }

    public Pessoa criarPessoa(PessoaRequestDTO dto) {
        Pessoa pessoa = pessoaMapper.toEntity(dto);
        pessoa.setCpf(CpfUtils.limparCpf(pessoa.getCpf()));

        if (pessoa.getEndereco() != null) {
            pessoa.getEndereco().setPessoa(pessoa);
        }

        return pessoaRepository.save(pessoa);
    }

    public Pessoa atualizarPessoa(Integer id, PessoaRequestDTO dto) {
        Pessoa pessoaExistente = buscarPessoaPorId(id);
        Pessoa pessoaAtualizada = pessoaMapper.toEntity(dto);

        pessoaAtualizada.setCpf(CpfUtils.limparCpf(pessoaAtualizada.getCpf()));
        atualizarCampos(pessoaExistente, pessoaAtualizada);

        return pessoaRepository.save(pessoaExistente);
    }

    public void deletarPessoa(Integer id) {
        buscarPessoaPorId(id);
        pessoaRepository.deleteById(id);
    }

    private void atualizarCampos(Pessoa existente, Pessoa atualizada) {
        existente.setCpf(atualizada.getCpf());
        existente.setEmail(atualizada.getEmail());
        existente.setDataNascimento(atualizada.getDataNascimento());
        existente.setTelefone(atualizada.getTelefone());

        if (atualizada.getEndereco() != null) {
            if (existente.getEndereco() != null) {
                existente.getEndereco().setBairro(atualizada.getEndereco().getBairro());
                existente.getEndereco().setCep(atualizada.getEndereco().getCep());
                existente.getEndereco().setCidade(atualizada.getEndereco().getCidade());
                existente.getEndereco().setEstado(atualizada.getEndereco().getEstado());
                existente.getEndereco().setPais(atualizada.getEndereco().getPais());
                existente.getEndereco().setNumeroResidencia(atualizada.getEndereco().getNumeroResidencia());
            } else {
                atualizada.getEndereco().setPessoa(existente);
                existente.setEndereco(atualizada.getEndereco());
            }
        }
    }
}
