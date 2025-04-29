package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import Interfaces.IView;

public class gameView extends JFrame implements IView {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton[][] buttons;
    private JLabel statusLabel;
    private JButton resetButton;
    private static final int SIZE = 3;

    public gameView() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(300, 350);

        // Board
        JPanel boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        buttons = new JButton[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].setActionCommand(i + "," + j);
                boardPanel.add(buttons[i][j]);
            }
        }

        // Status and reset
        JPanel bottomPanel = new JPanel();
        statusLabel = new JLabel("Player X's turn");
        resetButton = new JButton("Reset");
        bottomPanel.add(statusLabel);
        bottomPanel.add(resetButton);

        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void setMoveListener(ActionListener listener) {
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.addActionListener(listener);
            }
        }
    }

    @Override
    public void setResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    @Override
    public void onBoardUpdate(String[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j].setText(board[i][j] == null ? "" : board[i][j]);
            }
        }
    }

    @Override
    public void onGameStateUpdate(String state, String currentPlayer) {
        if (state != null) {
            if (state.equals("Draw")) {
                statusLabel.setText("Game Draw!");
            } else {
                statusLabel.setText("Player " + state + " wins!");
                // Thêm: Vô hiệu hóa các ô khi có người thắng
                for (JButton[] row : buttons) {
                    for (JButton button : row) {
                        button.setEnabled(false);
                    }
                }
            }
        } else {
            statusLabel.setText("Player "  + currentPlayer + "'s turn");
            // Thêm: Kích hoạt lại các ô khi game chưa kết thúc (hoặc sau reset)
            for (JButton[] row : buttons) {
                for (JButton button : row) {
                    button.setEnabled(true);
                }
            }
        }
    }
}