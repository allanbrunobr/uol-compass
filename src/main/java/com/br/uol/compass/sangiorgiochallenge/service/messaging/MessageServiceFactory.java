package com.br.uol.compass.sangiorgiochallenge.service.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceFactory {

    private final RabbitMQService rabbitMQService;
    private final SQSService sqsService;

    @Autowired
    public MessageServiceFactory(RabbitMQService rabbitMQService,
                                 SQSService sqsService) {
        this.rabbitMQService = rabbitMQService;
        this.sqsService = sqsService;
    }

    public MessageService getMessageService(String serviceType) {
        return switch (serviceType.toUpperCase()) {
            case "RABBITMQ" -> rabbitMQService;
            case "SQS" -> sqsService;
            default -> throw new IllegalArgumentException("Tipo de serviço desconhecido: " + serviceType);
        };
    }
}

