package components;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import misc.Const;

public class Slider extends Box {
    private String labelText;
    private JLabel label;
    private JSlider slider;

    public Slider(String labelText, int min, int max, int value, int majorTickSpacing, int minorTickSpacing) {
        super(BoxLayout.Y_AXIS);

        this.slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
        this.slider.setPaintTicks(true);
        this.slider.setPaintLabels(true);
        this.slider.setMajorTickSpacing(majorTickSpacing);
        this.slider.setMinorTickSpacing(minorTickSpacing);
        this.slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Slider.this.updateLabel();
            }
        });

        this.labelText = labelText;
        this.label = new JLabel();
        this.updateLabel();

        this.label.setFont(Const.DEFAULT_FONT.deriveFont(18f));
        this.slider.setFont(Const.DEFAULT_FONT.deriveFont(18f));

        super.add(this.label);
        super.add(this.slider);
    }

    public JLabel getLabel() {
        return this.label;
    }

    public JSlider getSlider() {
        return this.slider;
    }

    public int getValue() {
        return this.slider.getValue();
    }

    public void addChangeListener(ChangeListener listener) {
        slider.addChangeListener(listener);
    }

    private void updateLabel() {
        label.setText(labelText.replaceAll("\\$\\{value\\}", String.valueOf(this.slider.getValue())));
    }
}
