# Projeto: Sistema de Mensageria com RabbitMQ, Kafka, SQS e Zookeeper

Este projeto é um sistema de mensageria que utiliza RabbitMQ, Kafka, SQS (AWS Simple Queue Service) e Zookeeper, configurado para ser executado usando Docker. Abaixo você encontrará as instruções para baixar o código, configurar as credenciais da AWS e iniciar o ambiente Docker.

## Requisitos

Antes de começar, você precisará ter as seguintes ferramentas instaladas:

- [Git](https://git-scm.com/)
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Gradle](https://gradle.org/install/)

## Passos para Configuração

### 1. Baixar o Código

Primeiro, clone o repositório do projeto em seu ambiente local. No terminal, execute o comando abaixo:

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
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

### 5. Iniciar a Aplicação Spring Boot

#### 5.1 Executar a Aplicação Localmente com Gradle

No diretório raiz do projeto, execute o seguinte comando para iniciar a aplicação Spring Boot com Gradle:

```bash
./gradlew bootRun
```

Isso iniciará a aplicação Spring Boot, que se conectará aos serviços de mensageria (RabbitMQ, Kafka, Zookeeper e SQS) que você configurou anteriormente com Docker.

#### 5.2 Verificar se a Aplicação Está Rodando

Após iniciar o Spring Boot, você pode verificar se a aplicação está funcionando corretamente acessando o seguinte endpoint:

```
http://localhost:8080/api/avaliacoes/status
```

Se tudo estiver correto, você verá uma mensagem de status indicando que o serviço está operacional.

### 6. Realizar Pesquisas e Pagamentos via cURL e Postman

#### 6.1 Pesquisar Vendedor

Para buscar informações sobre um vendedor, você pode usar a seguinte requisição `GET`:

##### cURL:
```bash
curl -X GET "http://localhost:8080/api/vendedores/{id}" -H "accept: application/json"
```

Substitua `{id}` pelo ID do vendedor que deseja pesquisar.

##### Postman:
- Método: `GET`
- URL: `http://localhost:8080/api/vendedores/{id}`
- Substitua `{id}` pelo ID do vendedor.
- Clique em "Send" para executar a requisição.

#### 6.2 Realizar Pagamento

Para enviar um pagamento, use a seguinte requisição `POST` com o corpo JSON:

##### cURL:
```bash
curl -X POST "http://localhost:8080/api/avaliacoes" \
     -H "Content-Type: application/json" \
     -d '{
           "codigoVendedor": 1,
           "pagamentos": [
             {
               "codigoCobranca": 1,
               "valorPagamento": 100.00
             }
           ]
         }'
```

##### Postman:
- Método: `POST`
- URL: `http://localhost:8080/api/avaliacoes`
- No Body, escolha o formato `raw` e selecione `JSON`. Insira o seguinte JSON:

```json
{
  "codigoVendedor": 1,
  "pagamentos": [
    {
      "codigoCobranca": 1,
      "valorPagamento": 100.00
    }
  ]
}
```

- Clique em "Send" para executar a requisição.

### 7. Encerramento

Para encerrar todos os containers, execute o comando:

```bash
docker-compose down
```

Este comando irá parar e remover todos os containers criados pelo Docker Compose, mas os dados persistidos nos volumes ainda estarão disponíveis.

## Contribuindo

Contribuições são bem-vindas! Se você encontrar problemas ou tiver sugestões para melhorar o projeto, sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença

Este projeto está licenciado sob os termos da licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
