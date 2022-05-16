package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import entities.Player;
import entities.PlayerControls;
import entities.PlayerMoveOutOfBoundsException;
import misc.Vector;

public class Arena extends JPanel {
    private Dimension grid;
    private ArrayList<Player> players = new ArrayList<>();

    public Arena(Dimension grid) {
        this.grid = grid;

        setFocusable(true);

        PlayerControls player1Controls = new PlayerControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);
        PlayerControls player2Controls = new PlayerControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        Player player1 = new Player(this, player1Controls, Color.ORANGE, new Vector(0, 0), Vector.DOWN);
        Player player2 = new Player(this, player2Controls, Color.RED, new Vector(grid.width - 1, grid.height - 1), Vector.UP);
        players.add(player1);
        players.add(player2);

        for (Player player: players) {
            addKeyListener(player);
        }
    }

    public Dimension getGrid() {
        return this.grid;
    }

    public int getScreenTileSize() {
        return (int) Math.min(getWidth() / grid.width, getHeight() / grid.height);
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

    public void update() {
        ArrayList<Player> playersLost = new ArrayList<Player>();

        // Move players
        for (Player player: players) {
            try {
                player.move();
            } catch (PlayerMoveOutOfBoundsException ex) {
                // Player will go out of bounds
                playersLost.add(player);
            }
        }

        // Check for collisions
        for (Player player: players) {
            for (Player otherPlayer: players) {
                if (player.collidesWith(otherPlayer)) {
                    playersLost.add(player);
                }
            }
        }

        // When someone loses
        if (playersLost.size() > 0) {
            for (Player player : playersLost) {
                player.setFrozen(true);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the arena play area
        g.setColor(Color.BLACK);
        g.fillRect(getScreenOffsetX(), getScreenOffsetY(), getScreenWidth(), getScreenHeight());

        // Draw Players
        for (Player player : players) {
            player.draw(g);
        }
    }
}
