package core;

import java.awt.Dimension;

import javax.swing.JFrame;

import panels.Arena;

public class Game extends JFrame {
    public static int FRAMES_PER_SECOND = 100;
    private Arena arena;

    public Game(String title, int width, int height) {
        super(title);

        // Set up the window
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup arena panel
        arena = new Arena(new Dimension(300, 200));
        add(arena);

        setVisible(true);

        // Start game loop
        startLoop();
    }

    private void startLoop() {
        while (true) {
            arena.repaint();
            arena.update();

            try {
                Thread.sleep(1000/Game.FRAMES_PER_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
