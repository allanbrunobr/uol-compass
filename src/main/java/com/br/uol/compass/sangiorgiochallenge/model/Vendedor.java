package com.br.uol.compass.sangiorgiochallenge.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "vendedor")
@Data
public class Vendedor {
    @Id
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cobranca> cobrancas;
}