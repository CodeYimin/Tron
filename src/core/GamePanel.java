package core;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    ArrayList<PaintListener> paintListeners = new ArrayList<PaintListener>();

    public void addPaintListener(PaintListener listener) {
        paintListeners.add(listener);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (PaintListener listener : paintListeners) {
            listener.onPaintComponent(g, getSize());
        }
    }
}
