package entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.PaintListener;
import misc.Vector;

public class Player implements PaintListener, KeyListener {
    private boolean[][] occupiedSquares;
    private Vector position = new Vector(0, 0);
    private Vector direction = new Vector(0, 0);
    private double speed = 1;

    public Player() {

    }

    public Vector getPosition() {
        return this.position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getVelocity() {
        return this.direction;
    }

    public void setVelocity(Vector velocity) {
        this.direction = velocity;
    }

    public void update() {
        setPosition(position.add(direction.multiply(speed)));
    }

    @Override
    public void onPaintComponent(Graphics g, Dimension panelSize) {
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
