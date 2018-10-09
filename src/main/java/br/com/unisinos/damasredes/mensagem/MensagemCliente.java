package br.com.unisinos.damasredes.mensagem;

import lombok.Builder;
import lombok.Data;

/**
 * Mensagem do Cliente para o Servidor
 */
@Data
@Builder
public class MensagemCliente {

    private int origemX;
    private int origemY;
    private int destinoX;
    private int destinoY;
}
