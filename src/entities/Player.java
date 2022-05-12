package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import misc.Vector;
import panels.Arena;

public class Player implements KeyListener {
    private Arena arena;
    private boolean[][] bodyPositions;
    private Vector headPosition = new Vector(0, 0);
    private Vector headVelocity = new Vector(0, 0);
    private boolean frozen = false;

    public Player(Arena arena) {
        setArena(arena);
    }

    public Vector getHeadPosition() {
        return this.headPosition;
    }

    public void setHeadPosition(Vector position) {
        this.headPosition = position;
    }

    public Arena getArena() {
        return this.arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
        resetBodyPositions();
    }

    public boolean[][] getBodyPositions() {
        return this.bodyPositions;
    }

    public Vector getHeadVelocity() {
        return this.headVelocity;
    }

    public void setHeadVelocity(Vector velocity) {
        this.headVelocity = velocity;
    }

    public void resetBodyPositions() {
        bodyPositions = new boolean[arena.getGrid().width][arena.getGrid().height];
    }

    public void reset() {
        headPosition = new Vector(0, 0);
        headVelocity = new Vector(0, 0);
        resetBodyPositions();
    }

    public void update() {
        // Create new head position and check if it is in bounds
        Vector newHeadPosition = headPosition.add(headVelocity);
        if (!newHeadPosition.inBounds(arena.getGrid().width, arena.getGrid().height)) {
            // Player about to go out of bounds
            return;
        }

        // Previous head position is now part of the body
        bodyPositions[headPosition.getX()][headPosition.getY()] = true;
        // Update head position
        setHeadPosition(newHeadPosition);
    }

    public void draw(Graphics g) {
        int tileSize = arena.getScreenTileSize();
        int offsetX = arena.getScreenOffsetX();
        int offsetY = arena.getScreenOffsetY();

        g.setColor(Color.RED);
        for (int i = 0; i < bodyPositions.length; i++) {
            for (int j = 0; j < bodyPositions[i].length; j++) {
                if (bodyPositions[i][j]) {
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

        if (newDirection.equals(headVelocity.multiply(-1))) {
            return;
        }

        headVelocity = newDirection;
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
