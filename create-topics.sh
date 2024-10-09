#!/bin/bash

# Espera o Kafka estar pronto
echo "Aguardando o Kafka estar pronto para receber comandos..."
sleep 10

# Criar tópicos
kafka-topics --create --topic pagamentos-parciais --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
kafka-topics --create --topic pagamentos-totais --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
kafka-topics --create --topic pagamentos-excedentes --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

echo "Tópicos criados com sucesso!"
