package components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import misc.Const;

public class MatchStatus extends JPanel {
    private JLabel status;

    public MatchStatus(String status) {
        this.status = new JLabel(status);
        super.add(this.status);
    }

    public void setText(String text) {
        this.status.setText(text);
    }

    private void updateFontSize() {
        if (this.status.getText().length() == 0) {
            return;
        }

        float fontSize = Math.min(super.getWidth() / this.status.getText().length() * 2, super.getHeight() / 1.5f);
        this.status.setFont(Const.DEFAULT_FONT.deriveFont(fontSize).deriveFont(Font.BOLD));
        this.status.setBorder(new EmptyBorder(super.getHeight() / 5, 0, 0, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        super.setPreferredSize(new Dimension(0, super.getParent().getHeight() / 15));
        this.updateFontSize();
    }
}
