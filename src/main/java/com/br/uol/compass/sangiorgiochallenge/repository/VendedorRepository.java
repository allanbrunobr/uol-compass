package com.br.uol.compass.sangiorgiochallenge.repository;

import com.br.uol.compass.sangiorgiochallenge.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
}