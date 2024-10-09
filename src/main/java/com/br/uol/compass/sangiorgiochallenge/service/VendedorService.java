package com.br.uol.compass.sangiorgiochallenge.service;

import com.br.uol.compass.sangiorgiochallenge.dto.CobrancaDTO;
import com.br.uol.compass.sangiorgiochallenge.dto.VendedorDTO;
import com.br.uol.compass.sangiorgiochallenge.model.Vendedor;
import com.br.uol.compass.sangiorgiochallenge.repository.VendedorRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class VendedorService {

    private final VendedorRepository vendedorRepository;
    private final AtomicLong idGenerator = new AtomicLong();

    public VendedorService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
        vendedorRepository.findAll().stream()
                .mapToLong(Vendedor::getId)
                .max()
                .ifPresent(maxId -> idGenerator.set(maxId + 1));
    }

    public VendedorDTO buscarVendedorPorId(Long id) {
        Vendedor vendedor = vendedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
        return convertToDTO(vendedor);
    }

    private VendedorDTO convertToDTO(Vendedor vendedor) {
        VendedorDTO dto = new VendedorDTO();
        dto.setId(vendedor.getId());
        dto.setNome(vendedor.getNome());
        dto.setCobrancas(vendedor.getCobrancas().stream()
                .map(cobranca -> {
                    CobrancaDTO cobrancaDTO = new CobrancaDTO();
                    cobrancaDTO.setId(cobranca.getId());
                    cobrancaDTO.setValorOriginal(cobranca.getValorOriginal());
                    return cobrancaDTO;
                })
                .collect(Collectors.toList()));
        return dto;
    }
}