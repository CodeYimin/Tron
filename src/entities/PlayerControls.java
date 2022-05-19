package entities;

import java.awt.event.KeyEvent;

public class PlayerControls {
    public static final PlayerControls NORMAL_CONTROLS = new PlayerControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
    public static final PlayerControls HACKER_CONTROLS = new PlayerControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q);
    public static final PlayerControls INVERTED_CONTROLS = new PlayerControls(KeyEvent.VK_DOWN, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);
    private final int upKey;
    private final int downKey;
    private final int leftKey;
    private final int rightKey;
    private final int hackKey;

    public PlayerControls(int upKey, int downKey, int leftKey, int rightKey) {
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.hackKey = KeyEvent.VK_UNDEFINED;
    }

    public PlayerControls(int upKey, int downKey, int leftKey, int rightKey, int hackKey) {
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.hackKey = hackKey;
    }

    public int getUpKey() {
        return this.upKey;
    }

    public int getDownKey() {
        return this.downKey;
    }

    public int getLeftKey() {
        return this.leftKey;
    }

    public int getRightKey() {
        return this.rightKey;
    }

    public int getHackKey() {
        return this.hackKey;
    }
}
