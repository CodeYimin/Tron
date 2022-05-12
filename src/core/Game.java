package core;

import java.awt.event.KeyListener;
import java.util.ArrayList;

import entities.Arena;
import entities.Player;

public class Game {
    private GameWindow window;
    private ArrayList<Player> players = new ArrayList<>();
    private Arena arena;

    public Game(String title, int width, int height) {
        window = new GameWindow(title, width, height);

        arena = new Arena(50, 50, players);
        addPaintListener(arena);
        addKeyListener(arena);

        Player player = new Player(arena);
        addPlayer(player);

        startLoop();
    }

    private void startLoop() {
        while (true) {
            update();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        for (Player player : players) {
            player.update();
        }

        window.getPanel().repaint();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addPaintListener(PaintListener paintListener) {
        window.getPanel().addPaintListener(paintListener);
    }

    public void addKeyListener(KeyListener keyListener) {
        window.addKeyListener(keyListener);
    }
}
