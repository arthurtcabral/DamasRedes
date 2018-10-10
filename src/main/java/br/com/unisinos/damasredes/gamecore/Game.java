package br.com.unisinos.damasredes.gamecore;

import lombok.Getter;

import java.awt.*;

@Getter
public class Game {

	private Board board;

	public Game() {
		this.board = new Board();
	}

	public boolean play(int turn, Point startPosition, Point endPosition) {
		return board.play(turn, startPosition, endPosition);
	}

	public boolean isFinished() {
		return false;
	}
}
