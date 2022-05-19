package core;

import java.util.ArrayList;

import javax.swing.JFrame;

import entities.Slider;
import screens.GameScreen;

public class Game extends JFrame {
    public static int FRAMES_PER_SECOND = 30;

    private ArrayList<Updatable> updatables = new ArrayList<>();

    public Game(String title, int width, int height) {
        super(title);

        // Set up the window
        super.setSize(width, height);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the game screen
        GameScreen gameScreen = new GameScreen();
        this.addUpdatable(gameScreen);
        super.add(gameScreen);

        Slider slider = new Slider();

        // Make the window and panels visible
        super.setVisible(true);

        // Start game loop
        this.startLoop();
    }

    private void startLoop() {
        while (true) {
            // Update all updatables
            for (Updatable updatable : this.updatables) {
                updatable.update();
            }

            // Delay to match the desired FPS
            try {
                Thread.sleep(1000 / Game.FRAMES_PER_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addUpdatable(Updatable updatable) {
        this.updatables.add(updatable);
    }
}
