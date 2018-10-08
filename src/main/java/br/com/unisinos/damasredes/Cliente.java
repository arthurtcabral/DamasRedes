package br.com.unisinos.damasredes;

import br.com.unisinos.damasredes.mensagem.MensagemCliente;
import br.com.unisinos.damasredes.mensagem.MensagemServidor;
import br.com.unisinos.damasredes.mensagem.Status;
import br.com.unisinos.damasredes.mensagem.Tipo;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static br.com.unisinos.damasredes.frontend.ScreenHelper.*;

@Log4j
@Getter
public class Cliente {

    private int[] posicaoDeOrigem;
    private int[] posicaoDeDestino;
    private Socket server;

    public static void main(String[] args) {
        Cliente servidor = new Cliente();

        while (true) {
            MensagemServidor mensagem = servidor.waitForMessage();
            if (mensagem.isGameOver()) {
                servidor.endGame(mensagem);
                break;
            }

            printGameScreen(mensagem);
            if (mensagem.getTipo() == Tipo.INFORMACAO) {
                notificarTurnoAdversario();
            } else {
                MensagemCliente jogada = aguardarJogada();
            }
        }
    }

    private Cliente() {
        start();
    }

    private void start() {
        server = estabelecerConexao();
        System.out.println("Conexão estabelecida.");
    }

    private MensagemServidor waitForMessage() {
        return null;
    }

    private void endGame(MensagemServidor mensagem) {
        if (mensagem.getStatus() == Status.GANHOU) {

        } else {

        }
    }

    private Socket estabelecerConexao() {
        try {
            System.out.println("Tentando estabelecer conexão com servidor...");
            return new Socket(GameProps.getForClient().getProperty(
                    "server.host"), getPort("server.port"));
        } catch (IOException e) {
            log.error("Problema na conexão ao servidor", e);
            System.out.println("Não foi possível estabelecer conexão com o servidor.");
            throw new RuntimeException(
                    "Não foi possível estabelecer conexão com o servidor.");
        }
    }

    private int getPort(String s) {
        String port = GameProps.getForClient().getProperty(s);
        return Integer.parseInt(port);
    }

    private void jogar(BufferedReader doServidor) {
        // TODO: Apresentar tabuleiro.
        // TODO: Ler informa��es oriundas da l�gica de jogo do servidor
        Scanner teclado = new Scanner(System.in);
        System.out.println("Informe a linha e a coluna da posi��o de origem: ");
        this.posicaoDeOrigem[0] = teclado.nextInt();
        this.posicaoDeOrigem[1] = teclado.nextInt();

        System.out.println("Informe a linha e a coluna da posi��o de destino: ");
        this.posicaoDeDestino[0] = teclado.nextInt();
        this.posicaoDeDestino[1] = teclado.nextInt();
    }
}
