package com.br.uol.compass.sangiorgiochallenge.service.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceFactory {

    private final RabbitMQService rabbitMQService;
    private final SQSService sqsService;
    //private final KafkaService kafkaService;
//    private final PubSubService pubSubService;

    @Autowired
    public MessageServiceFactory(RabbitMQService rabbitMQService,
                                 SQSService sqsService) {
    //,KafkaService kafkaService) {
        this.rabbitMQService = rabbitMQService;
        this.sqsService = sqsService;
        //this.kafkaService = kafkaService;
    }

    public MessageService getMessageService(String serviceType) {
        return switch (serviceType.toUpperCase()) {
            case "RABBITMQ" -> rabbitMQService;
            case "SQS" -> sqsService;
            //case "KAFKA" -> kafkaService;
//            case "PUBSUB" -> pubSubService;
            default -> throw new IllegalArgumentException("Tipo de serviço desconhecido: " + serviceType);
        };
    }
}

