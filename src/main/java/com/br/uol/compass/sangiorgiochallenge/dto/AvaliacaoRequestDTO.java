package com.br.uol.compass.sangiorgiochallenge.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AvaliacaoRequestDTO {
    @NotNull(message = "O código do vendedor é obrigatório")
    private Long codigoVendedor;

    @NotNull(message = "A lista de pagamentos é obrigatória")
    @Size(min = 1, message = "A lista de pagamentos deve conter pelo menos um pagamento")
    private List<PagamentoDTO> pagamentos;
}