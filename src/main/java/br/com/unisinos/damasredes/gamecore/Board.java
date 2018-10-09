package br.com.unisinos.damasredes.gamecore;

import java.awt.*;

public class Board {

	private static final int
			EMPTY = 0,
			WHITE = 1,
			BLACK = 2,
			WHITE_KING = 3,
			BLACK_KING = 4;

	private final int[][] board;

	public Board() {
		board = initBoard();
	}

	private int[][] initBoard() {
		final int board[][] = {
				{BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY},
				{EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK},
				{BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY},
				{EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
				{EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
				{EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE},
				{WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY},
				{EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE}
		};
		return board;
	}

	private boolean canMove(int turn, Point startPosition, Point endPosition) {
		if ((turn == 1 && endPosition.y > startPosition.y) || (turn == 2 && endPosition.y < startPosition.y)) {
			return false;
		}
		if (!(endPosition.x == startPosition.x + 1 || endPosition.x == startPosition.x -1)
				|| !(endPosition.y == startPosition.y + 1 || endPosition.y == startPosition.y -1)) {
			return false;
		}
		if (board[endPosition.y][endPosition.x] != EMPTY) {
			return false;
		}
		if (board[startPosition.y][startPosition.x] != turn) {
			return false;
		}
		return true;
	}

	public void play(int turn, Point startPosition, Point endPosition) {
		if (canMove(turn, startPosition, endPosition) ) {
			board[startPosition.y][startPosition.x] = EMPTY;
			board[endPosition.y][endPosition.x] = turn;
		}
	}

	public void print() {
		for (int i = z0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	public static void main(String[] args) {
		Board board = new Board();
		board.play(BLACK, new Point(0, 2), new Point(2, 3));
		board.print();
	}
}
