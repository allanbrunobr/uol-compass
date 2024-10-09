CREATE TABLE IF NOT EXISTS vendedor (
                                        id BIGINT PRIMARY KEY,
                                        nome VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS cobranca (
                                        id BIGINT PRIMARY KEY,
                                        valor_original DOUBLE NOT NULL,
                                        vendedor_id BIGINT,
                                        FOREIGN KEY (vendedor_id) REFERENCES vendedor(id)
    );