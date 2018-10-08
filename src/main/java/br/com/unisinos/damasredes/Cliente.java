package br.com.unisinos.damasredes;

import java.io.BufferedReader;
import br.com.unisinos.damasredes.frontend.ScreenHelper;
import br.com.unisinos.damasredes.mensagem.MensagemCliente;
import br.com.unisinos.damasredes.mensagem.MensagemServidor;
import br.com.unisinos.damasredes.mensagem.Status;
import br.com.unisinos.damasredes.mensagem.Tipo;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import lombok.extern.log4j.Log4j;

import static br.com.unisinos.damasredes.frontend.ScreenHelper.aguardarJogada;
import static br.com.unisinos.damasredes.frontend.ScreenHelper.notificarTurnoAdversario;
import static br.com.unisinos.damasredes.frontend.ScreenHelper.printGameScreen;

@Log4j
public class Cliente {

	private String tabuleiro[][];
	private int[] posicaoDeOrigem;
	private int[] posicaoDeDestino;
	private int numeroDePecas;
	private EStatus status;
	private Socket server;
	
	public static void main(String[] args) throws IOException {
		Cliente servidor = new Cliente();
		ObjectOutputStream paraServidor = new ObjectOutputStream(
				servidor.server.getOutputStream());
		BufferedReader doServidor = new BufferedReader(new InputStreamReader(
				servidor.server.getInputStream()));
		servidor.jogar(doServidor);
		paraServidor.writeObject(servidor);
	}
	
	public Cliente() throws IOException {
		start();
	}

	private void start() throws IOException {
		server = estabelecerConexao();
		System.out.println("Conexão estabelecida.");
		
	}

	private Socket estabelecerConexao() {
		try {
			this.status = EStatus.JOGANDO;
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
			System.out.println("Tentando estabelecer conexão com servidor...");
			return new Socket(GameProps.getForClient().getProperty(
					"server.host"), getPort("server.port"));
		} catch (IOException e) {
			// log.error("Problema na conexão ao servidor", e);
			System.out
					.println("Não foi possível estabelecer conexão com o servidor.");
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

	public String[][] getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(String[][] tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public int[] getPosicaoDeOrigem() {
		return this.posicaoDeOrigem;
	}

	public void setPosicaoDeOrigem(int[] posicaoDeOrigem) {
		this.posicaoDeOrigem = posicaoDeOrigem;
	}

	public int[] getPosicaoDeDestino() {
		return this.posicaoDeDestino;
	}

	public void setPosicaoDeDestino(int[] posicaoDeDestino) {
		this.posicaoDeDestino = posicaoDeDestino;
	}

	public int getNumeroDePecas() {
		return numeroDePecas;
	}

	public void setNumeroDePecas(int numeroDePecas) {
		this.numeroDePecas = numeroDePecas;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}
}
