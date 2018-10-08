package br.com.unisinos.damasredes.frontend;

import br.com.unisinos.damasredes.mensagem.MensagemCliente;
import br.com.unisinos.damasredes.mensagem.MensagemServidor;

public class ScreenHelper {

    public static void printGameScreen(MensagemServidor mensagem) {
        System.out.print("\n\n\n");
        System.out.printf("Mensagem: %s\tStatus: %s\n\n", mensagem.getStatus(), mensagem.getStatus().name());
        System.out.print(TabuleiroHelper.print(mensagem.getTabuleiro()));
    }

    public static MensagemCliente aguardarJogada() {
        System.out.printf("\n\nÉ sua vez de jogar!");
        return null;
    }

    public static void notificarTurnoAdversario() {
        System.out.printf("\n\nNão é sua vez de jogar.");
    }
}
