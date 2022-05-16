package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import misc.Vector;
import panels.Arena;

public class Player implements KeyListener {
    private Arena arena;
    private PlayerControls controls;
    private Color color;
    private int score;
    private boolean frozen = false;

    // Position
    private Vector defaultHeadPosition;
    private Vector headPosition;
    private boolean[][] bodyPositions;

    // Velocity
    private Vector defaultHeadVelocity;
    private Vector headVelocity;
    private Vector prevHeadVelocity;

    public Player(Arena arena, PlayerControls controls, Color color, Vector defaultHeadPosition, Vector defaultHeadVelocity) {
        this.arena = arena;
        this.controls = controls;
        this.color = color;
        this.defaultHeadPosition = defaultHeadPosition;
        this.defaultHeadVelocity = defaultHeadVelocity;
        this.score = 0;

        reset();
    }

    public Vector getHeadPosition() {
        return this.headPosition;
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

    public boolean getFrozen() {
        return this.frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public boolean collidesWith(Player other) {
        if (this.getArena() != other.getArena()) {
            return false;
        }

        boolean headCollidesWithBody = other.getBodyPositions()[this.headPosition.getX()][this.headPosition.getY()];
        boolean headCollidesWithHead = other.getHeadPosition().equals(this.headPosition);

        if (other == this) {
            return headCollidesWithBody;
        } else {
            return headCollidesWithBody || headCollidesWithHead;
        }
    }

    public void reset() {
        this.headPosition = this.defaultHeadPosition.clone();
        this.headVelocity = this.defaultHeadVelocity.clone();
        this.bodyPositions = new boolean[this.arena.getGrid().width][this.arena.getGrid().height];
        this.frozen = false;
    }

    public void move() throws PlayerMoveOutOfBoundsException {
        if (this.frozen || this.headVelocity.equals(Vector.ZERO)) {
            return;
        }

        // Create new head position and check if it is in bounds
        Vector newHeadPosition = this.headPosition.add(this.headVelocity);
        if (!newHeadPosition.inBounds(this.arena.getGrid().width, this.arena.getGrid().height)) {
            throw new PlayerMoveOutOfBoundsException();
        }

        // Previous head position is now part of the body
        this.bodyPositions[this.headPosition.getX()][this.headPosition.getY()] = true;
        // Update head position
        this.headPosition = newHeadPosition;
        this.prevHeadVelocity = this.headVelocity.clone();
    }

    public void draw(Graphics g) {
        int tileSize = this.arena.getScreenTileSize();
        int offsetX = this.arena.getScreenOffsetX();
        int offsetY = this.arena.getScreenOffsetY();

        // Draw body
        g.setColor(color);
        for (int i = 0; i < this.bodyPositions.length; i++) {
            for (int j = 0; j < this.bodyPositions[i].length; j++) {
                if (this.bodyPositions[i][j]) {
                    g.fillRect(
                            i * tileSize + offsetX,
                            j * tileSize + offsetY,
                            tileSize,
                            tileSize);
                }
            }
        }

        // Draw head
        g.setColor(Color.WHITE);
        g.fillRect(
                this.headPosition.getX() * tileSize + offsetX,
                this.headPosition.getY() * tileSize + offsetY,
                tileSize,
                tileSize);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.controls == null) {
            return;
        }

        Vector newHeadVelocity;

        int key = e.getKeyCode();
        if (key == this.controls.getLeftKey()) {
            newHeadVelocity = Vector.LEFT;
        } else if (key == this.controls.getRightKey()) {
            newHeadVelocity = Vector.RIGHT;
        } else if (key == this.controls.getUpKey()) {
            newHeadVelocity = Vector.UP;
        } else if (key == this.controls.getDownKey()) {
            newHeadVelocity = Vector.DOWN;
        } else {
            return;
        }

        // Prevent doing instant 180 degree turn
        if (newHeadVelocity.equals(this.prevHeadVelocity.multiply(-1))) {
            this.headVelocity = this.prevHeadVelocity;
        } else {
            this.headVelocity = newHeadVelocity;
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
