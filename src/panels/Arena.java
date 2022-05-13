package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import entities.Player;
import entities.PlayerMoveOutOfBoundsException;
import misc.Vector;

public class Arena extends JPanel {
    private Dimension grid;
    private double gridLineThicknessPercentage = 0.1;
    private ArrayList<Player> players = new ArrayList<>();

    public Arena(Dimension gridSize, ArrayList<Player> players) {
        this.grid = gridSize;
        this.players = players;

        setFocusable(true);

        addPlayer(new Player(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, Color.BLUE, this));
        addPlayer(new Player(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, Color.RED, this));

        for (Player player : players) {
            addKeyListener(player);
        }
    }

    public Dimension getGrid() {
        return this.grid;
    }

    public int getScreenTileSize() {
        return (int) Math.min(getWidth() / grid.width, getHeight() / grid.height);
    }

    public int getScreenGridlineThickness() {
        return (int) (getScreenTileSize() * gridLineThicknessPercentage);
    }

    public int getScreenHeight() {
        return getScreenTileSize() * grid.height;
    }

    public int getScreenWidth() {
        return getScreenTileSize() * grid.width;
    }

    public int getScreenOffsetX() {
        return (getWidth() - getScreenWidth()) / 2;
    }

    public int getScreenOffsetY() {
        return (getHeight() - getScreenHeight()) / 2;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void update() {
        boolean[][] allPlayerBodyPositions = new boolean[grid.width][grid.height];

        for (Player player : players) {
            try {
                player.move();
            } catch (PlayerMoveOutOfBoundsException e) {
                // Player went out of bounds
            }

            for (int i = 0; i < player.getBodyPositions().length; i++) {
                for (int j = 0; j < player.getBodyPositions()[i].length; j++) {
                    if (player.getBodyPositions()[i][j]) {
                        allPlayerBodyPositions[i][j] = true;
                    }
                }
            }
        }

        for (Player player : players) {
            Vector headPos = player.getHeadPosition();

            if (allPlayerBodyPositions[headPos.getX()][headPos.getY()]) {
                // Player hit a body part and lost
            }

            for (Player otherPlayer : players) {
                Vector otherHeadPos = otherPlayer.getHeadPosition();
                if (player != otherPlayer && headPos.equals(otherHeadPos)) {
                    // Head on collision
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the grid
        int tileSize = getScreenTileSize();
        int gridlineThickness = getScreenGridlineThickness();

        int offsetX = getScreenOffsetX();
        int offsetY = getScreenOffsetY();

        g.setColor(Color.BLACK);

        for (int i = 1; i < grid.width; i++) {
            g.fillRect(
                    i * tileSize + offsetX,
                    0 + offsetY,
                    gridlineThickness,
                    tileSize * grid.height);
        }

        for (int i = 1; i < grid.height; i++) {
            g.fillRect(
                    0 + offsetX,
                    i * tileSize + offsetY,
                    tileSize * grid.width,
                    gridlineThickness);
        }

        // Draw Players
        for (Player player : players) {
            player.draw(g);
        }
    }
}
