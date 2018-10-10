package br.com.unisinos.damasredes.mensagem;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Mensagem do Servidor para o Cliente
 */
@Data
@Builder
public class MensagemServidor implements Serializable {

    private String mensagem;
    private Status status;
    private Tipo tipo;
    private int[][] tabuleiro;

    public boolean isGameOver() {
        return status != Status.JOGANDO;
    }
}
