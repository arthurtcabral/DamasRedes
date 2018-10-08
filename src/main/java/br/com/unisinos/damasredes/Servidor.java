package br.com.unisinos.damasredes;

import br.com.unisinos.damasredes.mensagem.Status;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Log4j
public class Servidor {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Servidor servidor = new Servidor();

        ObjectInputStream doCliente = new ObjectInputStream(
                servidor.clientA.getInputStream());

        Cliente jogadorA = (Cliente) doCliente.readObject();
        System.out.println(jogadorA.getPosicaoDeOrigem()[0]);
        System.out.println(jogadorA.getPosicaoDeOrigem()[1]);

        servidor.closeConnections();
    }

    private Socket clientA;
    private Socket clientB;
    private int numeroDePecas;
    private Status status;
    private Socket server;
    private String tabuleiro[][];

    public Servidor() {
        start();
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
        this.status = Status.JOGANDO;
        this.numeroDePecas = 12;
        this.tabuleiro = new String[8][8];
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro.length; j++) {
                this.tabuleiro[i][j] = "V";
            }
        }
        for (int i = 0; i < 4; i++) {
            int coluna = i % 2 == 0 ? 0 : 1;
            for (int j = coluna; j < tabuleiro.length; j = j + 2) {
                this.tabuleiro[i][j] = "X";
            }
        }
    }

    public void closeConnections() {
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
