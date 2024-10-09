package com.br.uol.compass.sangiorgiochallenge.controller;

import com.br.uol.compass.sangiorgiochallenge.dto.AvaliacaoRequestDTO;
import com.br.uol.compass.sangiorgiochallenge.dto.AvaliacaoResponseDTO;
import com.br.uol.compass.sangiorgiochallenge.exception.CobrancasInvalidasException;
import com.br.uol.compass.sangiorgiochallenge.exception.VendedorNaoEncontradoException;
import com.br.uol.compass.sangiorgiochallenge.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {
    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> avaliarPagamentos(@Valid @RequestBody AvaliacaoRequestDTO avaliacaoRequest) {
        try {
            AvaliacaoResponseDTO resultado = avaliacaoService.processarAvaliacao(avaliacaoRequest);
            return ResponseEntity.ok(resultado);
        } catch (VendedorNaoEncontradoException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new AvaliacaoResponseDTO("Erro: Vendedor não encontrado", List.of(e.getMessage()), null));
        } catch (CobrancasInvalidasException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new AvaliacaoResponseDTO("Erro: Cobranças inválidas", e.getErros(), null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AvaliacaoResponseDTO("Erro interno ao processar a avaliação", List.of(e.getMessage()), null));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Serviço de Avaliação de Pagamentos está operacional.");
    }
}