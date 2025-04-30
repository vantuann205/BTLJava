package Interfaces;

import Model.gameException;

public interface IModel {
    boolean makeMove(int row, int col) throws gameException;
    String getGameState();
    String getCurrentPlayer();
    String getCell(int row, int col) throws gameException;
    void reset();
    void addObserver(IObserver observer) throws gameException;
}