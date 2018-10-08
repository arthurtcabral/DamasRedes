package br.com.unisinos.damasredes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.log4j.Log4j;

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

	public Servidor() throws ClassNotFoundException {
		start();
	}

	private void start() throws ClassNotFoundException {
		try {
			ServerSocket socketClientA = new ServerSocket(
					getPort("client.a.port"));
			ServerSocket socketClientB = new ServerSocket(
					getPort("client.b.port"));

			System.out.println("Aguardando conexão do primeiro jogador...");
			clientA = socketClientA.accept();

			clientB = socketClientB.accept();

		} catch (IOException e) {
			// log.error("Problema na criação dos sockets do servidor", e);
		}
	}

	public void closeConnections() {
		System.out.println("Fechando conexões com clients.");
		try {
			clientA.close();
			clientB.close();
		} catch (IOException e) {
			// log.error("Erro no fechamento dos sockets", e);
		}
	}

	private int getPort(String s) {
		String port = GameProps.getForServer().getProperty(s);
		return Integer.parseInt(port);
	}
}
