package screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import audio.Music;
import core.Screen;
import misc.Const;

public class GameOverScreen extends Screen {
    private JLabel message;
    private JButton playAgainButton;
    private JButton returnButton;
    private Music music;

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

    @Override
    public void onEnter() {
        this.music = new Music(Const.BACKGROUND_MUSIC);
        this.music.setStartSeconds(3);
        this.music.start();
    }

    @Override
    public void onExit() {
        this.music.close();
    }
}
