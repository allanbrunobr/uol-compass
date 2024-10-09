package com.br.uol.compass.sangiorgiochallenge.dto;

import lombok.Data;
import java.util.List;

@Data
public class VendedorDTO {
    private Long id;
    private String nome;
    private List<CobrancaDTO> cobrancas;
}