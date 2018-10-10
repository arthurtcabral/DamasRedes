package br.com.unisinos.damasredes;

import br.com.unisinos.damasredes.mensagem.MensagemCliente;
import br.com.unisinos.damasredes.mensagem.MensagemServidor;
import br.com.unisinos.damasredes.mensagem.Tipo;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static br.com.unisinos.damasredes.frontend.ScreenHelper.*;

@Log4j
@Getter
public class Cliente {

    private int[] posicaoDeOrigem;
    private int[] posicaoDeDestino;
    private Socket server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public static void main(String[] args) throws IOException {
        Cliente cliente = new Cliente();
        BasicConfigurator.configure();

        while (true) {
            MensagemServidor mensagem = cliente.waitForMessage();

            printGameScreen(mensagem);

            if (mensagem.isGameOver()) {
                endGame(mensagem);
                break;
            }

            if (mensagem.getTipo() == Tipo.INFORMACAO) {
                notificarTurnoAdversario();
            } else {
                MensagemCliente jogada = aguardarJogada();
                cliente.enviarJogada(jogada);
            }
        }

        cliente.closeConnection();
    }

    private Cliente() throws IOException {
        start();
    }

    private void start() throws IOException {
        server = estabelecerConexao();
        inputStream = new ObjectInputStream(server.getInputStream());
        outputStream = new ObjectOutputStream(server.getOutputStream());
        System.out.println("Conexão estabelecida.");
    }

    private MensagemServidor  waitForMessage() {
        try {
            return (MensagemServidor) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("Problema na leitura do socket do servidor.", e);
            throw new RuntimeException("Problema na leitura do socket do servidor", e);
        }
    }

    private void enviarJogada(MensagemCliente jogada) {
        try {
            outputStream.writeObject(jogada);
            outputStream.flush();
        } catch (IOException e) {
            log.error("Problema na escrita do socket do servidor.", e);
            throw new RuntimeException("Problema na escrita do socket do servidor", e);
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

    private void closeConnection() {
        System.out.println("Fechando conexão com servidor.");
        try {
            server.close();
        } catch (IOException e) {
            log.error("Erro no fechamento do socket", e);
        }
    }
}
