package com.br.uol.compass.sangiorgiochallenge.service.messaging;

import com.br.uol.compass.sangiorgiochallenge.dto.PagamentoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
public class KafkaService implements MessageService {

    private final KafkaTemplate<String, PagamentoDTO> kafkaTemplate;
    private final String topicParcial;
    private final String topicTotal;
    private final String topicExcedente;

    public KafkaService(KafkaTemplate<String, PagamentoDTO> kafkaTemplate,
                        @Value("${kafka.topics.parcial}") String topicParcial,
                        @Value("${kafka.topics.total}") String topicTotal,
                        @Value("${kafka.topics.excedente}") String topicExcedente) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicParcial = topicParcial;
        this.topicTotal = topicTotal;
        this.topicExcedente = topicExcedente;
    }

    @Override
    public void enviarMensagem(PagamentoDTO pagamento) {
        String topic = switch (pagamento.getStatusPagamento()) {
            case "PARCIAL" -> topicParcial;
            case "TOTAL" -> topicTotal;
            case "EXCEDENTE" -> topicExcedente;
            default -> throw new IllegalArgumentException("Status de pagamento inválido: " + pagamento.getStatusPagamento());
        };

        kafkaTemplate.send(topic, pagamento);
        System.out.println("Mensagem enviada para Kafka - Tópico: " + topic + " com status: " + pagamento.getStatusPagamento());
    }
}
