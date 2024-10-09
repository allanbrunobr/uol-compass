package com.br.uol.compass.sangiorgiochallenge.interfaces;

import com.br.uol.compass.sangiorgiochallenge.dto.PagamentoDTO;

public interface MessageService {
    void enviarMensagem(PagamentoDTO pagamento);
}
