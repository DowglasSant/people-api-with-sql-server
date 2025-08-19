package com.santana.dowglas.sql_server_application.infrastructure.controller;

import com.santana.dowglas.sql_server_application.application.dto.PessoaRequestDTO;
import com.santana.dowglas.sql_server_application.application.dto.PessoaResponseDTO;
import com.santana.dowglas.sql_server_application.application.mapper.PessoaMapper;
import com.santana.dowglas.sql_server_application.application.service.PessoaService;
import com.santana.dowglas.sql_server_application.domain.model.Pessoa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
@Slf4j
public class PessoaController {

    private final PessoaService pessoaService;
    private final PessoaMapper pessoaMapper;

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> listarTodasPessoas() {
        log.info("Requisição recebida: listar todas as pessoas");
        List<PessoaResponseDTO> pessoasDto = pessoaMapper.toResponseDtoList(pessoaService.listarTodasPessoas());
        return ResponseEntity.ok(pessoasDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> buscarPessoaPorId(@PathVariable Integer id) {
        log.info("Requisição recebida: buscar pessoa por id {}", id);
        try {
            Pessoa pessoa = pessoaService.buscarPessoaPorId(id);
            return ResponseEntity.ok(pessoaMapper.toResponseDto(pessoa));
        } catch (RuntimeException e) {
            log.error("Erro ao buscar pessoa por id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PessoaResponseDTO> buscarPessoaPorCpf(@PathVariable String cpf) {
        log.info("Requisição recebida: buscar pessoa por cpf {}", cpf);
        Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
        if (pessoa == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(pessoaMapper.toResponseDto(pessoa));
    }

    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<List<PessoaResponseDTO>> buscarPorCidade(@PathVariable String cidade) {
        log.info("Requisição recebida: buscar pessoas por cidade {}", cidade);
        List<PessoaResponseDTO> pessoasDto = pessoaMapper.toResponseDtoList(pessoaService.buscarPessoaPorCidade(cidade));
        return ResponseEntity.ok(pessoasDto);
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> criarPessoa(@Valid @RequestBody PessoaRequestDTO dto) {
        log.info("Requisição recebida: criar pessoa");
        Pessoa novaPessoa = pessoaService.criarPessoa(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pessoaMapper.toResponseDto(novaPessoa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizarPessoa(@PathVariable Integer id,
                                                             @Valid @RequestBody PessoaRequestDTO dto) {
        log.info("Requisição recebida: atualizar pessoa id {}", id);
        try {
            Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(id, dto);
            return ResponseEntity.ok(pessoaMapper.toResponseDto(pessoaAtualizada));
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar pessoa id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable Integer id) {
        log.info("Requisição recebida: deletar pessoa id {}", id);
        try {
            pessoaService.deletarPessoa(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Erro ao deletar pessoa id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

