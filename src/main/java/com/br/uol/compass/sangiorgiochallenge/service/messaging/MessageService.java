package com.br.uol.compass.sangiorgiochallenge.service.messaging;

import com.br.uol.compass.sangiorgiochallenge.dto.PagamentoDTO;

public interface MessageService {
    void enviarMensagem(PagamentoDTO pagamento);
}
