package br.com.unisinos.damasredes.frontend;

import br.com.unisinos.damasredes.mensagem.MensagemCliente;
import br.com.unisinos.damasredes.mensagem.MensagemServidor;
import br.com.unisinos.damasredes.mensagem.Status;
import lombok.extern.log4j.Log4j;

import java.util.Scanner;

@Log4j
public class ScreenHelper {

    public static void printGameScreen(MensagemServidor mensagem) {
        System.out.print("\n\n\n");
        System.out.printf("Mensagem: %s\tStatus: %s\n\n", mensagem.getMensagem(), mensagem.getStatus().name());
        System.out.print(TabuleiroHelper.print(mensagem.getTabuleiro()));
    }

    public static MensagemCliente aguardarJogada() {
        System.out.println("\n\nÉ sua vez de jogar!");
        System.out.println("\n\nInforme a linha e a coluna da posição de origem no padrão \"x,y\": ");
        ParCoordenadas origem = aguardarCoordenadas();
        System.out.println("\n\nInforme a linha e a coluna da posição de destino no padrão \"x,y\": ");
        ParCoordenadas destino = aguardarCoordenadas();

        return MensagemCliente.builder()
                .origemX(origem.getX())
                .origemY(origem.getY())
                .destinoX(destino.getX())
                .destinoY(destino.getY())
                .build();
    }

    public static void endGame(MensagemServidor mensagem) {
        if (mensagem.getStatus() == Status.GANHOU) {
            System.out.println("\n\n\nParabéns! Você ganhou o jogo! Foi fácil!\n");
        } else {
            System.out.println("\n\n\nNão foi dessa vez! Você perdeu a partida :(\n");
        }
    }

    private static ParCoordenadas aguardarCoordenadas() {
        ParCoordenadas parCoordenadas = new ParCoordenadas();
        boolean notFirstTime = false;

        try {
            Scanner teclado = new Scanner(System.in);
            do {
                if (notFirstTime) {
                    System.out.println("Problema na leitura dos dados. Tente novamente: \n");
                }
                notFirstTime = true;
                parCoordenadas.processar(teclado.nextLine());
            } while (!parCoordenadas.isValido());
        } catch (Exception e) {
            log.error("Problema na leitura da entrada de dados", e);
        }

        return parCoordenadas;
    }

    public static void notificarTurnoAdversario() {
        System.out.println("\n\nNão é sua vez de jogar.");
    }
}
