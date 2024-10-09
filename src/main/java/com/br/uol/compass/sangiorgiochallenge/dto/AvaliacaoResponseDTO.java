package com.br.uol.compass.sangiorgiochallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AvaliacaoResponseDTO {
    private String mensagem;
    private List<String> erros;
    private List<PagamentoDTO> pagamentosProcessados;
}
