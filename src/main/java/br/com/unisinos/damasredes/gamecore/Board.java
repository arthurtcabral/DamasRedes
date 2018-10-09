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

	private boolean canMove(Point startPosition, Point endPosition) {
		if ((endPosition.x == startPosition.x + 1 || endPosition.x == startPosition.x -1)
				&& (endPosition.y == startPosition.y + 1 || endPosition.y == startPosition.y -1)) {
			return true;
		}
		if ()
		return false;
	}

}
