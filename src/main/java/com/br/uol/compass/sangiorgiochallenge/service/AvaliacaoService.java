package com.br.uol.compass.sangiorgiochallenge.service;

import com.br.uol.compass.sangiorgiochallenge.dto.AvaliacaoRequestDTO;
import com.br.uol.compass.sangiorgiochallenge.dto.AvaliacaoResponseDTO;
import com.br.uol.compass.sangiorgiochallenge.dto.PagamentoDTO;
import com.br.uol.compass.sangiorgiochallenge.exception.CobrancaNaoEncontradaException;
import com.br.uol.compass.sangiorgiochallenge.exception.CobrancasInvalidasException;
import com.br.uol.compass.sangiorgiochallenge.exception.VendedorNaoEncontradoException;
import com.br.uol.compass.sangiorgiochallenge.model.Cobranca;
import com.br.uol.compass.sangiorgiochallenge.repository.CobrancaRepository;
import com.br.uol.compass.sangiorgiochallenge.repository.VendedorRepository;
import com.br.uol.compass.sangiorgiochallenge.service.messaging.MessageService;
import com.br.uol.compass.sangiorgiochallenge.service.messaging.MessageServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvaliacaoService {

    private static final Logger logger = LoggerFactory.getLogger(AvaliacaoService.class);

    private final VendedorRepository vendedorRepository;
    private final CobrancaRepository cobrancaRepository;
    private final MessageServiceFactory messageServiceFactory;
    private final String messageServiceType;

    public AvaliacaoService(VendedorRepository vendedorRepository,
                            CobrancaRepository cobrancaRepository,
                            MessageServiceFactory messageServiceFactory,
                            @Value("${message.service}") String messageServiceType) {
        this.vendedorRepository = vendedorRepository;
        this.cobrancaRepository = cobrancaRepository;
        this.messageServiceFactory = messageServiceFactory;
        this.messageServiceType = messageServiceType;
    }

    public AvaliacaoResponseDTO processarAvaliacao(AvaliacaoRequestDTO avaliacaoRequest) {
        validarVendedor(avaliacaoRequest.getCodigoVendedor());

        List<String> erros = new ArrayList<>();
        List<PagamentoDTO> pagamentosProcessados = new ArrayList<>();

        for (PagamentoDTO pagamento : avaliacaoRequest.getPagamentos()) {
            try {
                Cobranca cobranca = validarCobranca(pagamento.getCodigoCobranca());
                pagamento.setValorOriginal(cobranca.getValorOriginal());
                String status = determinarStatusPagamento(pagamento.getValorPagamento(), cobranca.getValorOriginal());
                pagamento.setStatusPagamento(status);
                atualizarCobranca(cobranca, pagamento.getValorPagamento(), status);
                pagamentosProcessados.add(pagamento);

                MessageService messageService = messageServiceFactory
                                                    .getMessageService(messageServiceType);
                messageService.enviarMensagem(pagamento);

            } catch (CobrancaNaoEncontradaException e) {
                erros.add(e.getMessage());
            }
        }

        if (!erros.isEmpty()) {
            throw new CobrancasInvalidasException("Erro ao processar pagamentos", erros);
        }

        return new AvaliacaoResponseDTO(
                "Todos os pagamentos foram processados com sucesso",
                null,
                pagamentosProcessados
        );
    }

    private void atualizarCobranca(Cobranca cobranca, Double valorPago, String statusPagamento) {
        if ("PARCIAL".equals(statusPagamento)) {
            cobranca.setValorOriginal(cobranca.getValorOriginal() - valorPago);
        } else if ("TOTAL".equals(statusPagamento) || "EXCEDENTE".equals(statusPagamento)) {
            cobranca.setValorOriginal(0.0);

        }
        cobrancaRepository.save(cobranca);
        logger.info("Valor atualizado da cobrança (ID: {} - {}", cobranca.getId(), cobranca.getValorOriginal());

    }

    private void validarVendedor(Long codigoVendedor) {
        if (!vendedorRepository.existsById(codigoVendedor)) {
            throw new VendedorNaoEncontradoException("Vendedor não encontrado: " + codigoVendedor);
        }
    }

    private Cobranca validarCobranca(Long codigoCobranca) {
        return cobrancaRepository.findById(codigoCobranca)
                .orElseThrow(() -> new CobrancaNaoEncontradaException("Cobrança não encontrada: " + codigoCobranca));
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