package core;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    ArrayList<GamePaintingListener> paintListeners = new ArrayList<GamePaintingListener>();

    public void addPaintListener(GamePaintingListener listener) {
        paintListeners.add(listener);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (GamePaintingListener listener : paintListeners) {
            listener.onPaintingComponent(g);
        }
    }
}
