package br.com.unisinos.damasredes.mensagem;

import lombok.Data;

/**
 * Mensagem do Cliente para o Servidor
 */
@Data
public class MensagemCliente {

    private int jogadaX;
    private int jogadaY;
    private int destinoX;
    private int destinoY;
}
