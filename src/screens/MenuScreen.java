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

    public MenuScreen() {
        super.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_START;

        this.title = new JLabel("TRON", JLabel.CENTER);
        this.playButton = new JButton("Play");
        this.playButton.setFont(Const.DEFAULT_FONT.deriveFont(20f));
        this.speedSlider = new Slider("Game speed: ${value}", 10, 100, Const.DEFAULT_FPS, 45, 10);
        this.widthSlider = new Slider("Arena Width: ${value}", 30, 300, Const.DEFAULT_ARENA_SIZE.getWidth(), 90, 30);
        this.heightSlider = new Slider("Arena Height: ${value}", 30, 300, Const.DEFAULT_ARENA_SIZE.getHeight(), 90, 30);

        JPanel sliders = new JPanel(new GridBagLayout());
        GridBagConstraints slidersGbc = new GridBagConstraints();
        slidersGbc.insets = new Insets(0, 15, 0, 15);
        sliders.add(this.speedSlider, slidersGbc);
        sliders.add(this.widthSlider, slidersGbc);
        sliders.add(this.heightSlider, slidersGbc);

        gbc.gridy = 0;
        super.add(this.title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 50, 0);
        super.add(playButton, gbc);

        gbc.gridy = 2;
        super.add(sliders, gbc);

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
