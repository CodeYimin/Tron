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

public class MenuScreen extends JPanel {
    public static final String TITLE = "TRON";
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
        this.speedSlider = new Slider("Game speed: ${value}", 10, 100, 30, 45, 5);
        this.widthSlider = new Slider("Arena Width: ${value}", 30, 100, 50, 35, 5);
        this.heightSlider = new Slider("Arena Height: ${value}", 30, 100, 50, 35, 5);

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

    private void updateTitleSize() {
        if (this.title == null) {
            return;
        }

        int fontSize = Math.min(super.getWidth() / MenuScreen.TITLE.length(), super.getHeight() / 3);
        this.title.setFont(new Font("Calibri", Font.BOLD, fontSize));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.updateTitleSize();
    }
}
