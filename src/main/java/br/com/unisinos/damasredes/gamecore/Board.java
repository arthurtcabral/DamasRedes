package br.com.unisinos.damasredes.gamecore;

import lombok.Getter;

import java.awt.*;

@Getter
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
		int dx = endPosition.x - startPosition.x;
		int dy = endPosition.y - startPosition.y;
		if (Math.abs(dx) != Math.abs(dy) || Math.abs(dx) > 2 || dx == 0) {
			return false;
		}
		if (Math.abs(dx) == 2) {
			if (!isValidKill(turn, startPosition, endPosition)) {
				return false;
			}
		}
		if (board[endPosition.y][endPosition.x] != EMPTY) {
			return false;
		}
		if (board[startPosition.y][startPosition.x] != turn) {
			return false;
		}
		return true;
	}

	private boolean isValidKill(int turn, Point startPosition, Point endPosition) {
		if (turn == WHITE) {
			if (endPosition.x < startPosition.x) {
				if (board[endPosition.y + 1][endPosition.x + 1] != BLACK) {
					return false;
				}
				board[endPosition.y + 1][endPosition.x + 1] = EMPTY;
			} else {
				if (board[endPosition.y + 1][endPosition.x - 1] != BLACK) {
					return false;
				}
				board[endPosition.y + 1][endPosition.x - 1] = EMPTY;
			}
		} else {
			if (endPosition.x < startPosition.x) {
				if (board[endPosition.y - 1][endPosition.x + 1] != WHITE) {
					return false;
				}
				board[endPosition.y - 1][endPosition.x + 1] = EMPTY;
			} else {
				if (board[endPosition.y - 1][endPosition.x - 1] != WHITE) {
					return false;
				}
				board[endPosition.y - 1][endPosition.x - 1] = EMPTY;
			}
		}
		return true;
	}

	public boolean play(int turn, Point startPosition, Point endPosition) {
		if (canMove(turn, startPosition, endPosition) ) {
			board[startPosition.y][startPosition.x] = EMPTY;
			board[endPosition.y][endPosition.x] = turn;
			return true;
		}
		return false;
	}

	public void print() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	public static void main(String[] args) {
		Board board = new Board();
		board.play(BLACK, new Point(0, 2), new Point(1, 3));
		board.play(WHITE, new Point(1, 5), new Point(2, 4));
		board.play(WHITE, new Point(2, 4), new Point(0, 2));
		board.print();
	}
}
