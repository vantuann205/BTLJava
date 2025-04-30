package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import Interfaces.IView;

public class gameView extends JFrame implements IView {
    private static final long serialVersionUID = 1L;
    private JButton[][] buttons;
    private JLabel statusLabel;
    private JButton resetButton;
    private static final int SIZE = 3;

    public gameView() {
        try {
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Failed to initialize game view: " + e.getMessage(), 
                "Initialization Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void setMoveListener(ActionListener listener) {
        if (listener == null) {
            JOptionPane.showMessageDialog(this, 
                "Cannot set null move listener", 
                "Configuration Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.addActionListener(listener);
            }
        }
    }

    @Override
    public void setResetListener(ActionListener listener) {
        if (listener == null) {
            JOptionPane.showMessageDialog(this, 
                "Cannot set null reset listener", 
                "Configuration Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        resetButton.addActionListener(listener);
    }

    @Override
    public void onBoardUpdate(String[][] board) {
        try {
            if (board == null || board.length != SIZE || board[0].length != SIZE) {
                throw new IllegalArgumentException("Invalid board state");
            }
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    buttons[i][j].setText(board[i][j] == null ? "" : board[i][j]);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Failed to update board: " + e.getMessage(), 
                "Update Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onGameStateUpdate(String state, String currentPlayer) {
        try {
            if (state != null) {
                if (state.equals("Draw")) {
                    statusLabel.setText("Game Draw!");
                } else {
                    statusLabel.setText("Player " + state + " wins!");
                    for (JButton[] row : buttons) {
                        for (JButton button : row) {
                            button.setEnabled(false);
                        }
                    }
                }
            } else {
                if (currentPlayer == null) {
                    throw new IllegalArgumentException("Current player cannot be null when game is ongoing");
                }
                statusLabel.setText("Player " + currentPlayer + "'s turn");
                for (JButton[] row : buttons) {
                    for (JButton button : row) {
                        button.setEnabled(true);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Failed to update game state: " + e.getMessage(), 
                "Update Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}