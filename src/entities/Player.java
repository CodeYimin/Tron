package entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.PaintListener;
import misc.Vector;

public class Player implements KeyListener, PaintListener {
    private Arena arena;
    private boolean[][] occupiedSquares;
    private Vector position = new Vector(0, 0);
    private Vector velocity = new Vector(0, 0);

    public Player(Arena arena) {
        this.arena = arena;
        occupiedSquares = new boolean[arena.getWidth()][arena.getHeight()];
    }

    public Vector getPosition() {
        return this.position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Arena getArena() {
        return this.arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public boolean[][] getOccupiedSquares() {
        return this.occupiedSquares;
    }

    public void setOccupiedSquares(boolean[][] occupiedSquares) {
        this.occupiedSquares = occupiedSquares;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void update() {
        occupiedSquares[(int) position.getX()][(int) position.getY()] = true;
        Vector newPosition = position.add(velocity);
        setPosition(newPosition);
    }

    @Override
    public void onPaintComponent(Graphics g, Dimension panelSize) {
        int tileSize = arena.getScreenTileSize(panelSize);
        int offsetX = arena.getScreenOffsetX(panelSize);
        int offsetY = arena.getScreenOffsetY(panelSize);

        g.setColor(Color.RED);
        for (int i = 0; i < occupiedSquares.length; i++) {
            for (int j = 0; j < occupiedSquares[i].length; j++) {
                if (occupiedSquares[i][j]) {
                    g.fillRect(
                            i * tileSize + offsetX,
                            j * tileSize + offsetY,
                            tileSize,
                            tileSize);
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Vector newDirection;

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            newDirection = Vector.LEFT;
        } else if (key == KeyEvent.VK_RIGHT) {
            newDirection = Vector.RIGHT;
        } else if (key == KeyEvent.VK_UP) {
            newDirection = Vector.UP;
        } else if (key == KeyEvent.VK_DOWN) {
            newDirection = Vector.DOWN;
        } else {
            return;
        }

        if (newDirection.equals(velocity.multiply(-1))) {
            return;
        }

        velocity = newDirection;
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
