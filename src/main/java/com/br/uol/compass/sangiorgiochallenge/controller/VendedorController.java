package com.br.uol.compass.sangiorgiochallenge.controller;

import com.br.uol.compass.sangiorgiochallenge.dto.VendedorDTO;
import com.br.uol.compass.sangiorgiochallenge.service.VendedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    private final VendedorService vendedorService;

    public VendedorController(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> getVendedor(@PathVariable Long id) {
        VendedorDTO vendedor = vendedorService.buscarVendedorPorId(id);
        return ResponseEntity.ok(vendedor);
    }
}