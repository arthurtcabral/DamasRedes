package br.com.unisinos.damasredes;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.net.ServerSocket;

@Log4j
public class Servidor {

    private ServerSocket socketClientA;
    private ServerSocket socketClientB;

    public Servidor() {
        start();
    }

    private void start() {
        try {
            socketClientA = new ServerSocket(getPort("client.a.port"));
            socketClientB = new ServerSocket(getPort("client.b.port"));
        } catch (IOException e) {
            log.error("Problema na criação dos sockets do servidor");
        }
    }

    private int getPort(String s) {
        String port = GameProps.get().getProperty(s);
        return Integer.parseInt(port);
    }
}
