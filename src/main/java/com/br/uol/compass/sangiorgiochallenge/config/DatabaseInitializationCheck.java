package com.br.uol.compass.sangiorgiochallenge.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseInitializationCheck {

    @Bean
    public CommandLineRunner checkDatabaseInitialization(JdbcTemplate jdbcTemplate) {
        return args -> {
            int vendedorCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM vendedor", Integer.class);
            int cobrancaCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cobranca", Integer.class);

            System.out.println("Número de vendedores: " + vendedorCount);
            System.out.println("Número de cobranças: " + cobrancaCount);
        };
    }
}