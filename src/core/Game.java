package core;

import java.awt.event.KeyListener;
import java.util.ArrayList;

import entities.GameObject;
import entities.Player;

public class Game {
    private GameWindow window;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    public Game(String title, int width, int height) {
        window = new GameWindow(title, width, height);

        Player player = new Player();
        addGameObject(player);

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
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }

        window.getPanel().repaint();
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        if (gameObject instanceof GamePaintingListener) {
            window.getPanel().addPaintListener((GamePaintingListener) gameObject);
        }
        if (gameObject instanceof KeyListener) {
            window.addKeyListener((KeyListener) gameObject);
        }
    }
}
