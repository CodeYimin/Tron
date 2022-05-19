package entities;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import javax.swing.event.ChangeListener;

public class Slider {
	JFrame frame;
	JPanel panel;
	JLabel label;
	JSlider slider;

	public Slider() {

		frame = new JFrame("Slider Demo");
		panel = new JPanel();
		label = new JLabel();
		slider = new JSlider(25, 100, 50);

		slider.setPreferredSize(new Dimension(400, 200));

		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(10);

		slider.setPaintTrack(true);
		slider.setMajorTickSpacing(25);

		slider.setPaintLabels(true);
		slider.setFont(new Font("Arial", Font.PLAIN, 15));
		label.setFont(new Font("Arial", Font.PLAIN, 25));

		slider.setOrientation(SwingConstants.VERTICAL);

		label.setText("Game Speed " + slider.getValue());

		panel.add(slider);
		panel.add(label);
		frame.add(panel);
		frame.setSize(420, 420);
		frame.setVisible(true);
	}

	public int getValue() {
		return slider.getValue();
	}

	public void addChangeListener(ChangeListener listener) {
		slider.addChangeListener(listener);
	}
}
