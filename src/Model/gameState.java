package Model;

import Interfaces.IModel;
import Interfaces.IObserver;
import java.util.ArrayList;
import java.util.List;

public class gameState implements IModel {
	private String[][] board;
	private String currentPlayer;
	private List<IObserver> observers;
	private static final int SIZE = 3;

	public gameState() {
	        board = new String[SIZE][SIZE];
	        currentPlayer = "X";
	        observers = new ArrayList<>();
	    }

	@Override
	public boolean makeMove(int row, int col) {
		if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || board[row][col] != null) {
			return false;
		}
		board[row][col] = currentPlayer;
		notifyBoardUpdate();
		String state = checkGameState();
		if (state != null) {
			notifyGameStateUpdate(state, null);
		} else {
			currentPlayer = currentPlayer.equals("X") ? "O" : "X";
			notifyGameStateUpdate(null, currentPlayer);
		}
		return true;
	}

	@Override
	public String getGameState() {
		return checkGameState();
	}

	@Override
	public String getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public String getCell(int row, int col) {
		return board[row][col];
	}

	@Override
	public void reset() {
		board = new String[SIZE][SIZE];
		currentPlayer = "X";
		notifyBoardUpdate();
		notifyGameStateUpdate(null, currentPlayer);
	}

	@Override
	public void addObserver(IObserver observer) {
		observers.add(observer);
	}

	private String checkGameState() {
		// Check rows
		for (int i = 0; i < SIZE; i++) {
			if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
				return board[i][0];
			}
		}
		// Check columns
		for (int j = 0; j < SIZE; j++) {
			if (board[0][j] != null && board[0][j].equals(board[1][j]) && board[1][j].equals(board[2][j])) {
				return board[0][j];
			}
		}
		// Check diagonals
		if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
			return board[0][0];
		}
		if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
			return board[0][2];
		}
		// Check draw
		boolean full = true;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] == null) {
					full = false;
					break;
				}
			}
		}
		return full ? "Draw" : null;
	}

	private void notifyBoardUpdate() {
		for (IObserver observer : observers) {
			observer.onBoardUpdate(board);
		}
	}

	private void notifyGameStateUpdate(String state, String currentPlayer) {
		for (IObserver observer : observers) {
			observer.onGameStateUpdate(state, currentPlayer);
		}
	}

}
