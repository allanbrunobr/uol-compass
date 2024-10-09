package com.br.uol.compass.sangiorgiochallenge.service;

import com.br.uol.compass.sangiorgiochallenge.dto.AvaliacaoRequestDTO;
import com.br.uol.compass.sangiorgiochallenge.dto.AvaliacaoResponseDTO;
import com.br.uol.compass.sangiorgiochallenge.exception.VendedorNaoEncontradoException;
import com.br.uol.compass.sangiorgiochallenge.model.Cobranca;
import com.br.uol.compass.sangiorgiochallenge.model.Vendedor;
import com.br.uol.compass.sangiorgiochallenge.repository.CobrancaRepository;
import com.br.uol.compass.sangiorgiochallenge.repository.VendedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AvaliacaoServiceTest {

    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private CobrancaRepository cobrancaRepository;

    @Mock
    private MessagingService messagingService;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Test
    public void testProcessarAvaliacao_WhenVendedorNotFound_ShouldThrowException() {
        // Given
        Long codigoVendedor = 1L;
        when(vendedorRepository.existsById(codigoVendedor)).thenReturn(false);

        // When & Then
        assertThrows(VendedorNaoEncontradoException.class, () -> avaliacaoService.processarAvaliacao(new AvaliacaoRequestDTO(codigoVendedor, new ArrayList<>())));
    }

}