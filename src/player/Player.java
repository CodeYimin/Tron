package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import components.Arena;
import misc.XY;

public class Player implements KeyListener {
    // Defaults
    private final PlayerControls defaultControls;
    private final XY defaultHeadPosition;
    private final XY defaultHeadVelocity;

    // Mostly unchanged variables
    private Arena arena;
    private PlayerControls controls;
    private Color color;
    private String name;

    // State variables
    private int score;
    private boolean alive;

    // Position
    private XY headPosition;
    private boolean[][] bodyPositions;

    // Velocity
    private XY headVelocity;
    private XY prevHeadVelocity;

    // Getters and setters
    public String getName() {
        return this.name;
    }

    public XY getHeadPosition() {
        return this.headPosition;
    }

    public Arena getArena() {
        return this.arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
        respawn();
    }

    public PlayerControls getControls() {
        return this.controls;
    }

    public void setControls(PlayerControls newPlayerControls) {
        this.controls = newPlayerControls;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean[][] getBodyPositions() {
        return this.bodyPositions;
    }

    public XY getHeadVelocity() {
        return this.headVelocity;
    }

    public void setHeadVelocity(XY velocity) {
        this.headVelocity = velocity;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public Player(String name, Arena arena, XY defaultHeadPosition, XY defaultHeadVelocity, PlayerControls defaultControls, Color color) {
        this.name = name;
        this.arena = arena;
        this.defaultHeadPosition = defaultHeadPosition;
        this.defaultHeadVelocity = defaultHeadVelocity;
        this.defaultControls = defaultControls;
        this.color = color;
        this.score = 0;

        respawn();
    }

    public void respawn() {
        this.controls = this.defaultControls;
        this.headPosition = this.defaultHeadPosition;
        this.headVelocity = this.defaultHeadVelocity;
        this.prevHeadVelocity = this.defaultHeadVelocity;
        this.bodyPositions = new boolean[this.arena.getDimensions().getWidth()][this.arena.getDimensions().getHeight()];
        this.alive = true;
    }

    public boolean headCollidesWith(Player other) {
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

    public void move() throws PlayerMoveOutOfBoundsException {
        if (!this.alive || this.headVelocity.equals(XY.ZERO)) {
            return;
        }

        // Create new head position and check if it is in bounds
        XY newHeadPosition = this.headPosition.add(this.headVelocity);
        if (!newHeadPosition.inBounds(this.arena.getDimensions())) {
            throw new PlayerMoveOutOfBoundsException();
        }

        // Previous head position is now part of the body
        this.bodyPositions[this.headPosition.getX()][this.headPosition.getY()] = true;
        // Update head position
        this.headPosition = newHeadPosition;
        this.prevHeadVelocity = this.headVelocity;
    }

    public void draw(Graphics g) {
        int tileSize = this.arena.getScreenTileSize();

        // Draw body
        g.setColor(color);
        for (int i = 0; i < this.bodyPositions.length; i++) {
            for (int j = 0; j < this.bodyPositions[i].length; j++) {
                if (this.bodyPositions[i][j]) {
                    g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                }
            }
        }

        // Draw head
        g.setColor(Color.WHITE);
        g.fillRect(this.headPosition.getX() * tileSize, this.headPosition.getY() * tileSize, tileSize, tileSize);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.controls == null) {
            return;
        }

        XY newHeadVelocity;

        int key = e.getKeyCode();
        if (key == this.controls.getLeftKey()) {
            newHeadVelocity = XY.LEFT;
        } else if (key == this.controls.getRightKey()) {
            newHeadVelocity = XY.RIGHT;
        } else if (key == this.controls.getUpKey()) {
            newHeadVelocity = XY.UP;
        } else if (key == this.controls.getDownKey()) {
            newHeadVelocity = XY.DOWN;
        } else {
            if (key == this.controls.getHackKey()) {
                this.arena.useHack(this);
            }
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
