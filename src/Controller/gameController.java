package Controller;
import Interfaces.IModel;
import Interfaces.IView;
import Model.gameException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameController {
    private IModel model;
    private IView view;

    public gameController(IModel model, IView view) {
        try {
            if (model == null || view == null) {
                throw new gameException("Cannot initialize controller with null model or view");
            }
            this.model = model;
            this.view = view;
            this.model.addObserver(view);

            view.setMoveListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String[] coords = e.getActionCommand().split(",");
                        if (coords.length != 2) {
                            throw new gameException("Invalid move coordinates");
                        }
                        int row = Integer.parseInt(coords[0]);
                        int col = Integer.parseInt(coords[1]);
                        gameController.this.model.makeMove(row, col);
                    } catch (gameException ex) {
                        javax.swing.JOptionPane.showMessageDialog(null, 
                            ex.getMessage(), "Game Error", 
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException ex) {
                        javax.swing.JOptionPane.showMessageDialog(null, 
                            "Invalid coordinate format", "Game Error", 
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            this.view.setResetListener(e -> {
                try {
                    this.model.reset();
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, 
                        "Failed to reset game: " + ex.getMessage(), "Game Error", 
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (gameException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, 
                ex.getMessage(), "Initialization Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}