package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import misc.Const;
import misc.WidthHeight;
import player.Player;
import screens.BattleScreen;
import screens.GameOverScreen;
import screens.MenuScreen;

public class Game extends JFrame {
    public int fps = Const.DEFAULT_FPS;
    private ArrayList<Updatable> updatables = new ArrayList<>();
    private WidthHeight arenaSize = Const.DEFAULT_ARENA_SIZE;

    public Game(String title, int width, int height) {
        super(title);
        super.setSize(width, height);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuScreen menuScreen = this.createMenuScreen();
        this.enterScreen(menuScreen);

        this.startLoop();
    }

    private void startLoop() {
        while (true) {
            // Update all updatables
            for (int i = 0; i < updatables.size(); i++) {
                updatables.get(i).update();
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

    public void removeUpdatable(Updatable updatable) {
        this.updatables.remove(updatable);
    }

    private MenuScreen createMenuScreen() {
        MenuScreen menuScreen = new MenuScreen(this.fps, this.arenaSize.getWidth(), this.arenaSize.getHeight());

        menuScreen.addPlayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.this.changeScreen(menuScreen, Game.this.createBattleScreen());
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

        return menuScreen;
    }

    private BattleScreen createBattleScreen() {
        BattleScreen battleScreen = new BattleScreen(arenaSize, Const.MAX_WINS);

        battleScreen.addGameOverListener(new BattleScreen.GameOverListener() {
            @Override
            public void gameOver(Player winner) {
                Game.this.removeUpdatable(battleScreen);
                Game.this.changeScreen(battleScreen, Game.this.createGameOverScreen(winner.getName() + " won!"));
            }
        });
        this.addUpdatable(battleScreen);

        return battleScreen;
    }

    private GameOverScreen createGameOverScreen(String message) {
        GameOverScreen gameOverScreen = new GameOverScreen(message);

        gameOverScreen.addPlayAgainButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.this.changeScreen(gameOverScreen, Game.this.createBattleScreen());
            }
        });
        gameOverScreen.addReturnButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.this.changeScreen(gameOverScreen, Game.this.createMenuScreen());
            }
        });

        return gameOverScreen;
    }

    private void changeScreen(Screen oldScreen, Screen newScreen) {
        this.exitScreen(oldScreen);
        this.enterScreen(newScreen);
    }

    public void exitScreen(Screen screen) {
        super.setVisible(false);
        screen.onExit();
        super.remove(screen);
    }

    public void enterScreen(Screen screen) {
        super.add(screen);
        screen.onEnter();
        super.setVisible(true);
    }
}
