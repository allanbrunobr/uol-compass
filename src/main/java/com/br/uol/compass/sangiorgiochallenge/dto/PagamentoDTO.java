package com.br.uol.compass.sangiorgiochallenge.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serializable;

@Data
public class PagamentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "O código da cobrança é obrigatório")
    private Long codigoCobranca;

    @NotNull(message = "O valor do pagamento é obrigatório")
    @Positive(message = "O valor do pagamento deve ser positivo")
    private Double valorPagamento;

    @NotNull(message = "O valor original é obrigatório")
    @Positive(message = "O valor original deve ser positivo")
    private Double valorOriginal;

    private String statusPagamento;
}