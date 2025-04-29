package Interfaces;

public interface IObserver {
    void onBoardUpdate(String[][] board);
    void onGameStateUpdate(String state, String currentPlayer);
} 
