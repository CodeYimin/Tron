package entities;
import java.awt.event.KeyEvent;

public class PlayerControls {
    private int upKey;
    private int downKey;
    private int leftKey;
    private int rightKey;
    private int hackKey;

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
