package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import audio.Music;
import misc.Const;
import misc.WidthHeight;
import screens.GameScreen;
import screens.MenuScreen;

public class Game extends JFrame {
    public int fps = Const.DEFAULT_FPS;
    private ArrayList<Updatable> updatables = new ArrayList<>();
    private MenuScreen menuScreen;
    private WidthHeight arenaSize = Const.DEFAULT_ARENA_SIZE;
    Music music;

    public Game(String title, int width, int height) {
        super(title);
        super.setSize(width, height);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.menuScreen = new MenuScreen();
        menuScreen.addPlayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.super.setVisible(false);
                Game.super.remove(menuScreen);
                GameScreen gameScreen = new GameScreen(arenaSize);
                Game.this.addUpdatable(gameScreen);
                Game.super.add(gameScreen);
                Game.super.setVisible(true);
            }
        });
        menuScreen.addSpeedSliderListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                Game.this.fps = slider.getValue();
            }
        });
        menuScreen.addWidthSliderListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                Game.this.arenaSize = new WidthHeight(slider.getValue(), Game.this.arenaSize.getHeight());
            }
        });
        menuScreen.addHeightSliderListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                Game.this.arenaSize = new WidthHeight(Game.this.arenaSize.getWidth(), slider.getValue());
            }
        });
        super.add(menuScreen);

        super.setVisible(true);
        this.startLoop();
    }

    private void startLoop() {
        music = new Music("src/audio/Music.wav");
        music.start();
        music.loop();
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
