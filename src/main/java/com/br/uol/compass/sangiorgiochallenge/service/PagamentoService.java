package com.br.uol.compass.sangiorgiochallenge.repository;

import com.br.uol.compass.sangiorgiochallenge.dto.AvaliacaoRequestDTO;
import com.br.uol.compass.sangiorgiochallenge.service.MessagingService;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final MessagingService messagingService;

    public PagamentoService(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public void processarPagamento(AvaliacaoRequestDTO avaliacao) {
        if (isPagamentoParcial(avaliacao)) {
            messagingService.enviarPagamentoParcial(avaliacao);
        } else if (isPagamentoTotal(avaliacao)) {
            messagingService.enviarPagamentoTotal(avaliacao);
        } else {
            messagingService.enviarPagamentoExcedente(avaliacao);
        }
    }

    private boolean isPagamentoParcial(AvaliacaoRequestDTO avaliacao) {
        // Implementar lógica para determinar se é pagamento parcial
        return false; // Placeholder
    }

    private boolean isPagamentoTotal(AvaliacaoRequestDTO avaliacao) {
        // Implementar lógica para determinar se é pagamento total
        return false; // Placeholder
    }
}