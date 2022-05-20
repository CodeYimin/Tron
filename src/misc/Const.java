package misc;

import java.awt.Font;
import java.awt.event.KeyEvent;

import player.PlayerControls;

public class Const {
    public static final int DEFAULT_WINDOW_WIDTH = 800;
    public static final int DEFAULT_WINDOW_HEIGHT = 600;
    public static final WidthHeight DEFAULT_ARENA_SIZE = new WidthHeight(250, 100);
    public static final int DEFAULT_FPS = 30;
    public static final PlayerControls DEFAULT_PLAYER_ONE_CONTROLS = new PlayerControls(
            KeyEvent.VK_W,
            KeyEvent.VK_S,
            KeyEvent.VK_A,
            KeyEvent.VK_D,
            KeyEvent.VK_Q);
    public static final PlayerControls DEFAULT_PLAYER_TWO_CONTROLS = new PlayerControls(
            KeyEvent.VK_UP,
            KeyEvent.VK_DOWN,
            KeyEvent.VK_LEFT,
            KeyEvent.VK_RIGHT);
    public static final Font DEFAULT_FONT = new Font("Calibri", Font.PLAIN, 12);
    public static final int MAX_WINS = 3;
}
