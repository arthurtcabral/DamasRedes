package br.com.unisinos.damasredes;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Log4j
public class Servidor {

    public static void main(String[] args) {
        Servidor servidor = new Servidor();

        servidor.closeConnections();
    }

    private Socket clientA;
    private Socket clientB;

    public Servidor() {
        start();
    }

    private void start() {
        try {
            ServerSocket socketClientA = new ServerSocket(getPort("client.a.port"));
            ServerSocket socketClientB = new ServerSocket(getPort("client.b.port"));

            System.out.println("Aguardando conexão do primeiro jogador...");
            clientA = socketClientA.accept();

            System.out.println("Aguardando conexão do segundo jogador...");
            clientB = socketClientB.accept();
        } catch (IOException e) {
            log.error("Problema na criação dos sockets do servidor", e);
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
