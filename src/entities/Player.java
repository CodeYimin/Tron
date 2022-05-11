package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.GamePaintingListener;
import misc.Vector;

public class Player extends GameObject implements GamePaintingListener, KeyListener {
    private boolean[][] occupiedSquares;

    public Player() {

    }

    @Override
    public void onPaintingComponent(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) getPosition().getX(), (int) getPosition().getY(), 100, 100);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            setVelocity(Vector.LEFT);
        } else if (key == KeyEvent.VK_RIGHT) {
            setVelocity(Vector.RIGHT);
        } else if (key == KeyEvent.VK_UP) {
            setVelocity(Vector.UP);
        } else if (key == KeyEvent.VK_DOWN) {
            setVelocity(Vector.DOWN);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Do nothing
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }
}
