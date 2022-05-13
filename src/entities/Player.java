package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import misc.Vector;
import panels.Arena;

public class Player implements KeyListener {
    private Arena arena;
    private Vector defaultHeadPosition;
    private Vector defaultHeadVelocity;
    private PlayerControls controls;
    private Color color;

    private boolean[][] bodyPositions;
    private Vector headPosition;
    private Vector headVelocity;
    private boolean frozen = false;

    public Player(Arena arena, PlayerControls controls, Color color, Vector defaultHeadPosition, Vector defaultHeadVelocity) {
        this.arena = arena;
        this.controls = controls;
        this.color = color;
        this.defaultHeadPosition = defaultHeadPosition;
        this.defaultHeadVelocity = defaultHeadVelocity;

        reset();
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
        reset();
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

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public void reset() {
        headPosition = defaultHeadPosition.clone();
        headVelocity = defaultHeadVelocity.clone();
        bodyPositions = new boolean[arena.getGrid().width][arena.getGrid().height];
        frozen = false;
    }

    public void move() throws PlayerMoveOutOfBoundsException {
        if (frozen || headVelocity.equals(Vector.ZERO)) {
            return;
        }

        // Create new head position and check if it is in bounds
        Vector newHeadPosition = headPosition.add(headVelocity);
        if (!newHeadPosition.inBounds(arena.getGrid().width, arena.getGrid().height)) {
            throw new PlayerMoveOutOfBoundsException();
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

        // Draw body
        g.setColor(color);
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

        // Draw head
        g.setColor(color.darker().darker().darker());
        g.fillRect(
                getHeadPosition().getX() * tileSize + offsetX,
                getHeadPosition().getY() * tileSize + offsetY,
                tileSize,
                tileSize);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Vector newDirection;

        int key = e.getKeyCode();
        if (key == controls.getLeftKey()) {
            newDirection = Vector.LEFT;
        } else if (key == controls.getRightKey()) {
            newDirection = Vector.RIGHT;
        } else if (key == controls.getUpKey()) {
            newDirection = Vector.UP;
        } else if (key == controls.getDownKey()) {
            newDirection = Vector.DOWN;
        } else {
            return;
        }

        // Prevent doing instant 180 degree turn
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
