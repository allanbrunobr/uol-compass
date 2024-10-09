package com.br.uol.compass.sangiorgiochallenge.exception;

import java.util.List;

public class CobrancasInvalidasException extends RuntimeException {
    private final List<String> erros;

    public CobrancasInvalidasException(String message, List<String> erros) {
        super(message);
        this.erros = erros;
    }

    public List<String> getErros() {
        return erros;
    }
}