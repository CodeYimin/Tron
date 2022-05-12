package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import entities.Player;

public class Arena extends JPanel {
    private Dimension grid;
    private double gridLineThicknessPercentage = 0.1;
    private ArrayList<Player> players = new ArrayList<>();

    public Arena(Dimension gridSize, ArrayList<Player> players) {
        this.grid = gridSize;
        this.players = players;

        setFocusable(true);

        addPlayer(new Player(this));

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
        for (Player player : players) {
            player.update();
        }

        for (Player player1 : players) {
            for (Player player2 : players) {
                if (player1 != player2) {
                    if (player1.getHeadPosition().equals(player2.getHeadPosition())) {
                        // Head on collision
                    } else if (player2.getBodyPositions()[player1.getHeadPosition().getX()][player1.getHeadPosition()
                            .getY()]) {
                        // Head of player1 hits body of player 2
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Paint the grid
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
