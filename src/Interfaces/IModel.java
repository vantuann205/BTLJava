package Interfaces;

public interface IModel {
	boolean makeMove(int row, int col);

	String getGameState();

	String getCurrentPlayer();

	String getCell(int row, int col);

	void reset();

	void addObserver(IObserver observer);
}
