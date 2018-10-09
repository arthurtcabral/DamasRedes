package br.com.unisinos.damasredes.mensagem;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Mensagem do Cliente para o Servidor
 */
@Data
@Builder
public class MensagemCliente implements Serializable {

    private int origemX;
    private int origemY;
    private int destinoX;
    private int destinoY;
}
