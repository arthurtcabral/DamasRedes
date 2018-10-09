package br.com.unisinos.damasredes.mensagem;

import lombok.Builder;
import lombok.Data;

/**
 * Mensagem do Servidor para o Cliente
 */
@Data
@Builder
public class MensagemServidor {

    private String mensagem;
    private Status status;
    private Tipo tipo;
    private String[][] tabuleiro;

    public boolean isGameOver() {
        return status != Status.JOGANDO;
    }
}
