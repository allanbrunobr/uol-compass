package com.br.uol.compass.sangiorgiochallenge.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queue.parcial}")
    private String queueParcial;

    @Value("${rabbitmq.queue.total}")
    private String queueTotal;

    @Value("${rabbitmq.queue.excedente}")
    private String queueExcedente;

    @Value("${rabbitmq.routing-key.parcial}")
    private String routingKeyParcial;

    @Value("${rabbitmq.routing-key.total}")
    private String routingKeyTotal;

    @Value("${rabbitmq.routing-key.excedente}")
    private String routingKeyExcedente;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue queueParcial() {
        return new Queue(queueParcial, true);
    }

    @Bean
    public Queue queueTotal() {
        return new Queue(queueTotal, true);
    }

    @Bean
    public Queue queueExcedente() {
        return new Queue(queueExcedente, true);
    }

    @Bean
    public Binding bindingParcial(Queue queueParcial, DirectExchange exchange) {
        return BindingBuilder.bind(queueParcial).to(exchange).with(routingKeyParcial);
    }

    @Bean
    public Binding bindingTotal(Queue queueTotal, DirectExchange exchange) {
        return BindingBuilder.bind(queueTotal).to(exchange).with(routingKeyTotal);
    }

    @Bean
    public Binding bindingExcedente(Queue queueExcedente, DirectExchange exchange) {
        return BindingBuilder.bind(queueExcedente).to(exchange).with(routingKeyExcedente);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}