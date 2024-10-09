# Projeto: Sistema de Mensageria com RabbitMQ, Kafka, SQS e PubSub

Este projeto é um sistema de mensageria que utiliza RabbitMQ, SQS (AWS Simple Queue Service), Kafka e GCP Pub/Sub, configurado para ser executado usando Docker. Abaixo você encontrará as instruções para baixar o código, configurar as credenciais da AWS e iniciar o ambiente Docker.

## Requisitos

Antes de começar, você precisará ter as seguintes ferramentas instaladas:

- [Git](https://git-scm.com/)
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Passos para Configuração

### 1. Baixar o Código

Primeiro, clone o repositório do projeto em seu ambiente local. No terminal, execute o comando abaixo:

```bash
git clone https://github.com/allanbrunobr/seu-repositorio.git
```

Navegue para o diretório do projeto:

```bash
cd seu-repositorio
```

### 2. Configurar as Credenciais AWS

O projeto faz uso do SQS da AWS, então é necessário configurar as credenciais da AWS no arquivo `application.yaml`.

Abra o arquivo `application.yaml` localizado na pasta `src/main/resources` e insira suas credenciais da AWS (chave de acesso, chave secreta e região):

```yaml
aws:
  access-key: YOUR_AWS_ACCESS_KEY
  secret-key: YOUR_AWS_SECRET_KEY
  region: us-east-1

sqs:
  queue:
    url-parcial: https://sqs.us-east-1.amazonaws.com/your-account-id/queue-parcial
    url-total: https://sqs.us-east-1.amazonaws.com/your-account-id/queue-total
    url-excedente: https://sqs.us-east-1.amazonaws.com/your-account-id/queue-excedente
```

- **`YOUR_AWS_ACCESS_KEY`**: Substitua pelo seu `access key` da AWS.
- **`YOUR_AWS_SECRET_KEY`**: Substitua pela sua `secret key` da AWS.
- **`your-account-id`**: Substitua pelo seu ID de conta da AWS.

### 3. Iniciar o Ambiente com Docker

O ambiente de mensageria utiliza RabbitMQ, Kafka, Zookeeper e SQS. Para iniciar esses serviços com Docker, siga os passos abaixo.

#### 3.1 Build e Execução do Docker

No diretório do projeto, execute o seguinte comando para construir e iniciar os containers:

```bash
docker-compose up --build
```

Este comando fará o seguinte:

- Baixará as imagens do Docker necessárias para RabbitMQ, Kafka, Zookeeper e o próprio projeto.
- Configurará os volumes para persistência de dados.
- Iniciará os containers para RabbitMQ, Kafka e Zookeeper.

#### 3.2 Verificar o Status dos Containers

Você pode verificar se todos os containers estão em execução com o comando:

```bash
docker ps
```

Isso mostrará os containers ativos, como `rabbitmq`, `kafka`, `zookeeper`, etc.

#### 3.3 Acessar a Interface de Administração do RabbitMQ

Para acessar a interface de administração do RabbitMQ, abra o navegador e vá para o endereço:

```
http://localhost:15672
```

- **Username**: `guest`
- **Password**: `guest`

Aqui você poderá ver as filas e mensagens do RabbitMQ.

### 4. Tópicos Kafka

Os tópicos Kafka são criados automaticamente ao iniciar o Kafka, com base no script `create-topics.sh` incluído no projeto. Este script é executado automaticamente no container Kafka.

### 5. Encerramento

Para encerrar todos os containers, execute o comando:

```bash
docker-compose down
```

Este comando irá parar e remover todos os containers criados pelo Docker Compose, mas os dados persistidos nos volumes ainda estarão disponíveis.

## Contribuindo

Contribuições são bem-vindas! Se você encontrar problemas ou tiver sugestões para melhorar o projeto, sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença

Este projeto está licenciado sob os termos da licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
