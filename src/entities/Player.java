package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import components.Arena;
import misc.XY;

public class Player implements KeyListener {
    // Player States
    public static final int ALIVE = 1;
    public static final int DEAD = 0;
    public static final int INACTIVE = -1;

    // General variables
    private Arena arena;
    private PlayerControls controls;
    private Color color;
    private int score;
    private int state;

    // Position
    private XY defaultHeadPosition;
    private XY headPosition;
    private boolean[][] bodyPositions;

    // Velocity
    private XY defaultHeadVelocity;
    private XY headVelocity;
    private XY prevHeadVelocity;

    // Getters and setters
    public XY getHeadPosition() {
        return this.headPosition;
    }

    public Arena getArena() {
        return this.arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
        reset();
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

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public Player(Arena arena, XY defaultHeadPosition, XY defaultHeadVelocity, PlayerControls controls, Color color) {
        this.arena = arena;
        this.defaultHeadPosition = defaultHeadPosition;
        this.defaultHeadVelocity = defaultHeadVelocity;
        this.controls = controls;
        this.color = color;
        this.score = 0;

        reset();
    }

    public void reset() {
        if(this.controls != PlayerControls.HACKER_CONTROLS) {
            this.controls = PlayerControls.NORMAL_CONTROLS;
        }
        this.bodyPositions = new boolean[this.arena.getDimensions().getWidth()][this.arena.getDimensions().getHeight()];
        this.headPosition = this.defaultHeadPosition;
        this.headVelocity = this.defaultHeadVelocity;
        this.state = Player.ALIVE;
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
        if (this.state == Player.DEAD || this.state == Player.INACTIVE || this.headVelocity.equals(XY.ZERO)) {
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
