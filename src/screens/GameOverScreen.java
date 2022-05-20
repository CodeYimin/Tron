package screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import misc.Const;

public class GameOverScreen extends JPanel {
    JLabel message;
    JButton playAgainButton;
    JButton returnButton;

    public GameOverScreen(String message) {
        super.setLayout(new GridBagLayout());

        this.message = new JLabel(message);
        this.message.setFont(Const.DEFAULT_FONT.deriveFont(50f));
        this.playAgainButton = new JButton("Play Again");
        this.returnButton = new JButton("Return to Menu");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        super.add(this.message, gbc);
        gbc.gridwidth = 1;

        gbc.gridy = 1;
        gbc.gridx = 0;
        super.add(this.playAgainButton, gbc);
        gbc.gridx = 1;
        super.add(this.returnButton, gbc);
    }

    public void addPlayAgainButtonListener(ActionListener listener) {
        this.playAgainButton.addActionListener(listener);
    }

    public void addReturnButtonListener(ActionListener listener) {
        this.returnButton.addActionListener(listener);
    }
}
