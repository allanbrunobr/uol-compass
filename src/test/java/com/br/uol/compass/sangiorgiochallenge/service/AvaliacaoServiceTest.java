package com.br.uol.compass.sangiorgiochallenge.service;

import com.br.uol.compass.sangiorgiochallenge.dto.AvaliacaoRequestDTO;
import com.br.uol.compass.sangiorgiochallenge.exception.VendedorNaoEncontradoException;
import com.br.uol.compass.sangiorgiochallenge.repository.CobrancaRepository;
import com.br.uol.compass.sangiorgiochallenge.repository.VendedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceTest {

    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private CobrancaRepository cobrancaRepository;

    @Mock
    private MessagingService messagingService;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Test
    void testProcessarAvaliacao_WhenVendedorNotFound_ShouldThrowException() {
        Long codigoVendedor = 1L;
        AvaliacaoRequestDTO avaliacaoRequest = new AvaliacaoRequestDTO(codigoVendedor, new ArrayList<>());

        when(vendedorRepository.existsById(codigoVendedor)).thenReturn(false);

        assertThrows(VendedorNaoEncontradoException.class, () -> avaliacaoService.processarAvaliacao(avaliacaoRequest));
    }

}
