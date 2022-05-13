package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import misc.Vector;
import panels.Arena;

public class Player implements KeyListener {
    private int upKey;
    private int downKey;
    private int leftKey;
    private int rightKey;

    private Color color;

    private Arena arena;
    private boolean[][] bodyPositions;
    private Vector headPosition = new Vector(0, 0);
    private Vector headVelocity = new Vector(0, 0);
    private boolean frozen = false;

    public Player(int upKey, int downKey, int leftKey, int rightKey, Color color, Arena arena) {
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.color = color;
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
        // Do nothing
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
        g.setColor(Color.BLACK);
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
        if (key == leftKey) {
            newDirection = Vector.LEFT;
        } else if (key == rightKey) {
            newDirection = Vector.RIGHT;
        } else if (key == upKey) {
            newDirection = Vector.UP;
        } else if (key == downKey) {
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
