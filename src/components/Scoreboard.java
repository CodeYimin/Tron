package components;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Scoreboard extends JPanel {
    public Scoreboard(int minWidth, int minHeight) {
        Dimension minSize = new Dimension(minWidth, minHeight);
        super.setPreferredSize(minSize);
        super.setMinimumSize(minSize);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void update() {
        super.repaint();
    }
}
