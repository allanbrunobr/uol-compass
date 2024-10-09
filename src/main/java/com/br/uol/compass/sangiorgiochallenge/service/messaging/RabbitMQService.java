package com.br.uol.compass.sangiorgiochallenge.service.messaging;

import com.br.uol.compass.sangiorgiochallenge.dto.PagamentoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQService.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKeyParcial;
    private final String routingKeyTotal;
    private final String routingKeyExcedente;

    public RabbitMQService(RabbitTemplate rabbitTemplate,
                           @Value("${rabbitmq.exchange.name}") String exchangeName,
                           @Value("${rabbitmq.routing-key.parcial}") String routingKeyParcial,
                           @Value("${rabbitmq.routing-key.total}") String routingKeyTotal,
                           @Value("${rabbitmq.routing-key.excedente}") String routingKeyExcedente) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKeyParcial = routingKeyParcial;
        this.routingKeyTotal = routingKeyTotal;
        this.routingKeyExcedente = routingKeyExcedente;
    }

    @Override
    public void enviarMensagem(PagamentoDTO pagamento) {
        String routingKey = switch (pagamento.getStatusPagamento()) {
            case "PARCIAL" -> routingKeyParcial;
            case "TOTAL" -> routingKeyTotal;
            case "EXCEDENTE" -> routingKeyExcedente;
            default -> throw new IllegalArgumentException("Status de pagamento inválido: " + pagamento.getStatusPagamento());
        };

        rabbitTemplate.convertAndSend(exchangeName, routingKey, pagamento);
        logger.info("Mensagem enviada para RabbitMQ com status: {}", pagamento.getStatusPagamento());
    }
}

