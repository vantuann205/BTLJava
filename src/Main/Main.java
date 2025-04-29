package Main;


import Controller.gameController;
import Model.gameState;
import View.gameView;

public class Main {
    public static void main(String[] args) {
        gameState model = new gameState();
        gameView view = new gameView();
        new gameController(model, view);
    }
}
