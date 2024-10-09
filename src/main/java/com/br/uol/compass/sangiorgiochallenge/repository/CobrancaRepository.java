package com.br.uol.compass.sangiorgiochallenge.repository;

import com.br.uol.compass.sangiorgiochallenge.model.Cobranca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CobrancaRepository extends JpaRepository<Cobranca, Long> {
}
