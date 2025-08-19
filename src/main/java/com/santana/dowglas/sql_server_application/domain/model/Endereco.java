package com.santana.dowglas.sql_server_application.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "pessoa_id", nullable = false, unique = true)
    @JsonBackReference
    private Pessoa pessoa;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(nullable = false, length = 50)
    private String pais;

    @Column(length = 100)
    private String bairro;

    @Column(length = 20)
    private String cep;

    @Column(name = "numero_residencia", length = 20)
    private String numeroResidencia;
}
