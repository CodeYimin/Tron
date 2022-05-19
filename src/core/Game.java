package core;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import player.Slider;
import screens.GameScreen;

public class Game extends JFrame {
    public int fps = 30;
    Slider slider;

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

        // Make the window and panels visible
        super.setVisible(true);

        this.slider = new Slider();
        this.slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Game.this.fps = Game.this.slider.getValue();
            }
        });
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
                Thread.sleep(1000 / this.fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addUpdatable(Updatable updatable) {
        this.updatables.add(updatable);
    }
}
