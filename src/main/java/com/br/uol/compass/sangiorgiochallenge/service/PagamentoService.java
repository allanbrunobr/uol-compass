package com.br.uol.compass.sangiorgiochallenge.service;

import com.br.uol.compass.sangiorgiochallenge.dto.AvaliacaoRequestDTO;
import com.br.uol.compass.sangiorgiochallenge.dto.PagamentoDTO;
import com.br.uol.compass.sangiorgiochallenge.model.Cobranca;
import com.br.uol.compass.sangiorgiochallenge.repository.CobrancaRepository;
import com.br.uol.compass.sangiorgiochallenge.exception.CobrancaNaoEncontradaException;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    private final MessagingService messagingService;
    private final CobrancaRepository cobrancaRepository;

    public PagamentoService(MessagingService messagingService, CobrancaRepository cobrancaRepository) {
        this.messagingService = messagingService;
        this.cobrancaRepository = cobrancaRepository;
    }

    public void processarPagamentos(AvaliacaoRequestDTO avaliacao) {
        for (PagamentoDTO pagamento : avaliacao.getPagamentos()) {
            Cobranca cobranca = cobrancaRepository.findById(pagamento.getCodigoCobranca())
                    .orElseThrow(() -> new CobrancaNaoEncontradaException("Cobrança não encontrada: " + pagamento.getCodigoCobranca()));

            String statusPagamento = determinarStatusPagamento(pagamento.getValorPagamento(), cobranca.getValorOriginal());
            pagamento.setStatusPagamento(statusPagamento);

            messagingService.enviarParaFila(pagamento);
        }
    }

    private String determinarStatusPagamento(Double valorPago, Double valorOriginal) {
        if (valorPago.equals(valorOriginal)) {
            return "TOTAL";
        } else if (valorPago < valorOriginal) {
            return "PARCIAL";
        } else {
            return "EXCEDENTE";
        }
    }
}