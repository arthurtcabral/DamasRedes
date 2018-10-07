package br.com.unisinos.damasredes;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.net.Socket;

@Log4j
public class Cliente {

    public static void main(String[] args) {
        Cliente servidor = new Cliente();
    }

    private Socket server;

    public Cliente() {
        start();
    }

    private void start() {
        server = estabelecerConexao();
        System.out.println("Conexão estabelecida.");
    }

    private Socket estabelecerConexao() {
        try {
            System.out.println("Tentando estabelecer conexão com servidor...");
            return new Socket(GameProps.getForClient().getProperty("server.host"), getPort("server.port"));
        } catch (IOException e) {
            log.error("Problema na conexão ao servidor", e);
            System.out.println("Não foi possível estabelecer conexão com o servidor.");
            throw new RuntimeException("Não foi possível estabelecer conexão com o servidor.");
        }
    }

    private int getPort(String s) {
        String port = GameProps.getForClient().getProperty(s);
        return Integer.parseInt(port);
    }
}
