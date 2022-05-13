package entities;

public class PlayerControls {
    private int upKey;
    private int downKey;
    private int leftKey;
    private int rightKey;

    public PlayerControls(int upKey, int downKey, int leftKey, int rightKey) {
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }

    public int getUpKey() {
        return upKey;
    }

    public int getDownKey() {
        return downKey;
    }

    public int getLeftKey() {
        return leftKey;
    }

    public int getRightKey() {
        return rightKey;
    }
}
