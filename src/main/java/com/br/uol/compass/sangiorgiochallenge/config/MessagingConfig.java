package com.br.uol.compass.sangiorgiochallenge.config;

import com.br.uol.compass.sangiorgiochallenge.dto.PagamentoDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;

@Configuration
public class MessagingConfig {

    @Bean
    public Function<Message<PagamentoDTO>, Message<PagamentoDTO>> processarPagamento() {
        return message -> {
            PagamentoDTO pagamento = message.getPayload();
            String statusPagamento = determinarStatusPagamento(pagamento);
            pagamento.setStatusPagamento(statusPagamento);
            return MessageBuilder.withPayload(pagamento)
                    .copyHeaders(message.getHeaders())
                    .setHeader("pagamentoStatus", statusPagamento)
                    .build();
        };
    }

    private String determinarStatusPagamento(PagamentoDTO pagamento) {
      if (pagamento.getValorPagamento() < pagamento.getValorOriginal()) {
            return "PARCIAL";
        } else if (pagamento.getValorPagamento().equals(pagamento.getValorOriginal())) {
            return "TOTAL";
        } else {
            return "EXCEDENTE";
        }
    }
}