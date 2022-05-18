package core;

import javax.swing.JFrame;

import misc.Dimension;
import panels.Arena;

public class Game extends JFrame {
    public static int FRAMES_PER_SECOND = 100;
    private Arena arena;

    public Game(String title, int width, int height) {
        super(title);

        // Set up the window
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup arena panel
        this.arena = new Arena(this, new Dimension(300, 200));
        this.add(this.arena);

        // Make the window and panels visible
        this.setVisible(true);

        // Start game loop
        this.startLoop();
    }

    private void startLoop() {
        while (true) {
            this.arena.repaint();
            this.arena.update();

            try {
                Thread.sleep(1000 / Game.FRAMES_PER_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void endRound() {
        
    }
}
