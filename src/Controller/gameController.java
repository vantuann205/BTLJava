package Controller;
import Interfaces.IModel;
import Interfaces.IView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameController {
    private IModel model;
    private IView view;

    public gameController(IModel model, IView view) {
        this.model = model;
        this.view = view;
        this.model.addObserver(view);

        view.setMoveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] coords = e.getActionCommand().split(",");
                int row = Integer.parseInt(coords[0]);
                int col = Integer.parseInt(coords[1]);
                gameController.this.model.makeMove(row, col);
            }
        });

        this.view.setResetListener(e -> this.model.reset());
    }
}