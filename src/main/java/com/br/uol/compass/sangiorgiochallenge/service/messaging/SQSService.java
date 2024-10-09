package com.br.uol.compass.sangiorgiochallenge.service.messaging;

import com.br.uol.compass.sangiorgiochallenge.dto.PagamentoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class SQSService implements MessageService {

    private final SqsClient sqsClient;
    private final String queueUrlParcial;
    private final String queueUrlTotal;
    private final String queueUrlExcedente;

    public SQSService(SqsClient sqsClient,
                      @Value("${sqs.queue.url-parcial}") String queueUrlParcial,
                      @Value("${sqs.queue.url-total}") String queueUrlTotal,
                      @Value("${sqs.queue.url-excedente}") String queueUrlExcedente) {
        this.sqsClient = sqsClient;
        this.queueUrlParcial = queueUrlParcial;
        this.queueUrlTotal = queueUrlTotal;
        this.queueUrlExcedente = queueUrlExcedente;
    }

    @Override
    public void enviarMensagem(PagamentoDTO pagamento) {
        String queueUrl = switch (pagamento.getStatusPagamento()) {
            case "PARCIAL" -> queueUrlParcial;
            case "TOTAL" -> queueUrlTotal;
            case "EXCEDENTE" -> queueUrlExcedente;
            default -> throw new IllegalArgumentException("Status de pagamento inválido: " + pagamento.getStatusPagamento());
        };

        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(pagamento.toString())
                .build());

        System.out.println("Mensagem enviada para SQS com status: " + pagamento.getStatusPagamento());
    }
}

