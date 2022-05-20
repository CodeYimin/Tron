package screens;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import components.Slider;
import misc.Const;

public class MenuScreen extends JPanel {
    JLabel title;
    JButton playButton;
    Slider speedSlider;
    Slider widthSlider;
    Slider heightSlider;

    public MenuScreen(int defaultFps, int defaultWidth, int defaultHeight) {
        super.setLayout(new GridBagLayout());

        this.title = new JLabel("TRON", JLabel.CENTER);
        this.playButton = new JButton("Play");
        this.playButton.setFont(Const.DEFAULT_FONT.deriveFont(20f));
        this.speedSlider = new Slider("Game speed: ${value}", 10, 100, defaultFps, 45, 10);
        this.widthSlider = new Slider("Arena Width: ${value}", 30, 300, defaultWidth, 90, 30);
        this.heightSlider = new Slider("Arena Height: ${value}", 30, 300, defaultHeight, 90, 30);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridwidth = 3;

        gbc.gridy = 0;
        super.add(this.title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 50, 0);
        super.add(playButton, gbc);

        gbc.insets = new Insets(0, 15, 0, 15);
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        super.add(this.speedSlider, gbc);
        gbc.gridx = 1;
        super.add(this.widthSlider, gbc);
        gbc.gridx = 2;
        super.add(this.heightSlider, gbc);
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 0, 0);

        gbc.gridy = 3;
        super.add(new JPanel(), gbc);
    }

    public void addPlayButtonListener(ActionListener listener) {
        this.playButton.addActionListener(listener);
    }

    public void addSpeedSliderListener(ChangeListener listener) {
        this.speedSlider.addChangeListener(listener);
    }

    public void addWidthSliderListener(ChangeListener listener) {
        this.widthSlider.addChangeListener(listener);
    }

    public void addHeightSliderListener(ChangeListener listener) {
        this.heightSlider.addChangeListener(listener);
    }

    private void updateTitleSize() {
        if (this.title == null || this.title.getText().length() == 0) {
            return;
        }

        float fontSize = Math.min(super.getWidth() / this.title.getText().length(), super.getHeight() / 3);
        this.title.setFont(Const.DEFAULT_FONT.deriveFont(fontSize).deriveFont(Font.BOLD));
    }

    private void updateButtonSize() {
        if (this.playButton == null) {
            return;
        }

        float fontSize = Math.min(super.getWidth() / this.playButton.getText().length(), super.getHeight() / 8);
        this.playButton.setFont(Const.DEFAULT_FONT.deriveFont(fontSize));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.updateTitleSize();
        this.updateButtonSize();
    }
}
