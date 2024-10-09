package com.br.uol.compass.sangiorgiochallenge.service;

import com.br.uol.compass.sangiorgiochallenge.dto.PagamentoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKeyParcial;
    private final String routingKeyTotal;
    private final String routingKeyExcedente;

    public MessagingService(RabbitTemplate rabbitTemplate,
                            @Value("${rabbitmq.exchange.name}") String exchangeName,
                            @Value("${rabbitmq.routing-key.parcial}") String routingKeyParcial,
                            @Value("${rabbitmq.routing-key.total}") String routingKeyTotal,
                            @Value("${rabbitmq.routing-key.excedente}") String routingKeyExcedente) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKeyParcial = routingKeyParcial;
        this.routingKeyTotal = routingKeyTotal;
        this.routingKeyExcedente = routingKeyExcedente;
        this.rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

    }

    public void enviarParaFila(PagamentoDTO pagamento) {
        String routingKey = switch (pagamento.getStatusPagamento()) {
            case "PARCIAL" -> routingKeyParcial;
            case "TOTAL" -> routingKeyTotal;
            case "EXCEDENTE" -> routingKeyExcedente;
            default -> throw new IllegalArgumentException("Status de pagamento inválido: " + pagamento.getStatusPagamento());
        };

        System.out.println("Enviando mensagem para fila com routingKey: " + routingKey + " | Pagamento: " + pagamento);

        rabbitTemplate.convertAndSend(exchangeName, routingKey, pagamento);

        System.out.println("Mensagem enviada com sucesso para " + routingKey);    }
}