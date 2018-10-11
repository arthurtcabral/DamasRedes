package br.com.unisinos.damasredes;

import br.com.unisinos.damasredes.gamecore.Game;
import br.com.unisinos.damasredes.mensagem.MensagemCliente;
import br.com.unisinos.damasredes.mensagem.MensagemServidor;
import br.com.unisinos.damasredes.mensagem.Status;
import br.com.unisinos.damasredes.mensagem.Tipo;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.BasicConfigurator;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static br.com.unisinos.damasredes.mensagem.Tipo.INFORMACAO;
import static br.com.unisinos.damasredes.mensagem.Tipo.JOGADA;

@Log4j
@Getter
public class Servidor {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Integer turn = 1;
        BasicConfigurator.configure();
        Servidor servidor = new Servidor();

        Game game = new Game();

        MensagemServidor mensagemInicio = MensagemServidor.builder()
                .mensagem("")
                .status(Status.JOGANDO)
                .tabuleiro(servidor.getGame().getBoard().getBoard())
                .tipo(Tipo.JOGADA)
                .build();

        MensagemServidor mensagemVitoria = MensagemServidor.builder()
                .mensagem("Parabéns!")
                .status(Status.GANHOU)
                .tabuleiro(servidor.getGame().getBoard().getBoard())
                .tipo(INFORMACAO)
                .build();

        ObjectOutputStream outputStreamClientA = new ObjectOutputStream(servidor.getClientA().getOutputStream());
        outputStreamClientA.writeObject(mensagemInicio);
        outputStreamClientA.flush();

        ObjectInputStream inputStreamClientA = new ObjectInputStream(servidor.getClientA().getInputStream());
        MensagemCliente response = (MensagemCliente) inputStreamClientA.readObject();
        servidor.getGame().play(turn, new Point(response.getOrigemX(), response.getOrigemY()), new Point(response.getDestinoX(), response.getDestinoY()));
        System.out.println(response.toString());

        ObjectOutputStream outputStreamClientB = new ObjectOutputStream(servidor.getClientB().getOutputStream());
        mensagemInicio.setTabuleiro(servidor.getGame().getBoard().getBoard());
        outputStreamClientB.writeObject(mensagemInicio);
        outputStreamClientB.flush();

        ObjectInputStream inputStreamClientB = new ObjectInputStream(servidor.getClientB().getInputStream());
        MensagemCliente responseB = (MensagemCliente) inputStreamClientB.readObject();
        servidor.getGame().play(2, new Point(responseB.getOrigemX(), responseB.getOrigemY()), new Point(responseB.getDestinoX(), responseB.getDestinoY()));
        System.out.println(responseB.toString());

        while (!servidor.getGame().isFinished()) {
            mensagemVitoria.setTabuleiro(servidor.getGame().getBoard().getBoard());
            outputStreamClientA.writeObject(getMessage(JOGADA, servidor.getGame().getBoard().getBoard(), Status.JOGANDO));
            outputStreamClientA.flush();

            outputStreamClientB.writeObject(getMessage(INFORMACAO, servidor.getGame().getBoard().getBoard(), Status.JOGANDO));
            outputStreamClientB.flush();

            response = (MensagemCliente) inputStreamClientA.readObject();
            servidor.getGame().play(1, new Point(response.getOrigemX(), response.getOrigemY()), new Point(response.getDestinoX(), response.getDestinoY()));
            System.out.println(response.toString());

            // Jogador A terminou jogada, inicia a do B
            outputStreamClientB.writeObject(getMessage(JOGADA, servidor.getGame().getBoard().getBoard(), Status.JOGANDO));
            outputStreamClientA.writeObject(getMessage(INFORMACAO, servidor.getGame().getBoard().getBoard(), Status.JOGANDO));
            outputStreamClientA.flush();
            outputStreamClientB.flush();

            response = (MensagemCliente) inputStreamClientB.readObject();
            System.out.println(response.toString());
            servidor.getGame().play(2, new Point(response.getOrigemX(), response.getOrigemY()), new Point(response.getDestinoX(), response.getDestinoY()));

        }

        servidor.closeConnections();
    }

    private static MensagemServidor getMessage(Tipo tipo, int[][] board, Status status) {
        return MensagemServidor.builder()
                .mensagem("")
                .status(status)
                .tabuleiro(copyArray(board))
                .tipo(tipo)
                .build();
    }

    private static int[][] copyArray(int[][] array) {
        int[][] copy = new int[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                copy[i][j] = array[i][j];
            }
        }
        return copy;
    }

    private Socket clientA;
    private Socket clientB;
    private int numeroDePecas;
    private Status status;
    private Socket server;
    private Game game;

    private Servidor() {
        start();
        initializeData();
    }

    private void start() {
        try {
            ServerSocket socketClientA = new ServerSocket(
                    getPort("client.a.port"));
            ServerSocket socketClientB = new ServerSocket(
                    getPort("client.b.port"));

            System.out.println("Aguardando conexão do primeiro jogador...");
            clientA = socketClientA.accept();

            System.out.println("Aguardando conexão do segundo jogador...");
            clientB = socketClientB.accept();

            System.out.println("\nIniciando jogo!");
        } catch (IOException e) {
            log.error("Problema na criação dos sockets do servidor", e);
        }
    }

    private void initializeData() {
        this.game = new Game();
    }

    private void closeConnections() {
        System.out.println("Fechando conexões com clients.");
        try {
            clientA.close();
            clientB.close();
        } catch (IOException e) {
            log.error("Erro no fechamento dos sockets", e);
        }
    }

    private int getPort(String s) {
        String port = GameProps.getForServer().getProperty(s);
        return Integer.parseInt(port);
    }
}
