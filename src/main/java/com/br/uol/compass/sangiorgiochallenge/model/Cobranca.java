package com.br.uol.compass.sangiorgiochallenge.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cobranca")
public class Cobranca {
    @Id
    private Long id;

    private Double valorOriginal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

}