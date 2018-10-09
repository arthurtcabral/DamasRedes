package br.com.unisinos.damasredes;

import br.com.unisinos.damasredes.mensagem.MensagemCliente;
import br.com.unisinos.damasredes.mensagem.MensagemServidor;
import br.com.unisinos.damasredes.mensagem.Status;
import br.com.unisinos.damasredes.mensagem.Tipo;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Log4j
@Getter
public class Servidor {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        BasicConfigurator.configure();
        Servidor servidor = new Servidor();

        MensagemServidor mensagemInicio = MensagemServidor.builder()
                .mensagem("")
                .status(Status.JOGANDO)
                .tabuleiro(servidor.getTabuleiro())
                .tipo(Tipo.JOGADA)
                .build();

        MensagemServidor mensagemVitoria = MensagemServidor.builder()
                .mensagem("Parabéns!")
                .status(Status.GANHOU)
                .tabuleiro(servidor.getTabuleiro())
                .tipo(Tipo.INFORMACAO)
                .build();

        ObjectOutputStream outputStream = new ObjectOutputStream(servidor.getClientA().getOutputStream());
        outputStream.writeObject(mensagemInicio);
        outputStream.flush();

        ObjectInputStream inputStream = new ObjectInputStream(servidor.getClientA().getInputStream());
        MensagemCliente mensagem = (MensagemCliente) inputStream.readObject();
        System.out.println(mensagem.toString());

        outputStream.writeObject(mensagemVitoria);
        outputStream.flush();

        servidor.closeConnections();
    }

    private Socket clientA;
    private Socket clientB;
    private int numeroDePecas;
    private Status status;
    private Socket server;
    private String tabuleiro[][];

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
