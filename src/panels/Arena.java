package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import entities.Player;
import entities.PlayerControls;
import entities.PlayerMoveOutOfBoundsException;
import misc.Dimension;
import misc.Vector;

public class Arena extends JPanel {
    private Dimension grid;
    private ArrayList<Player> players = new ArrayList<>();

    public Arena(Dimension grid) {
        this.grid = grid;

        this.setFocusable(true);

        PlayerControls player1Controls = new PlayerControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);
        PlayerControls player2Controls = new PlayerControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        Player player1 = new Player(this, player1Controls, Color.ORANGE, new Vector(0, 0), Vector.DOWN);
        Player player2 = new Player(this, player2Controls, Color.RED, new Vector(this.grid).subtract(1), Vector.UP);
        this.players.add(player1);
        this.players.add(player2);

        for (Player player : this.players) {
            this.addKeyListener(player);
        }
    }

    public Dimension getGrid() {
        return this.grid;
    }

    public int getScreenTileSize() {
        return Math.min(this.getWidth() / this.grid.getWidth(), this.getHeight() / this.grid.getHeight());
    }

    public int getScreenHeight() {
        return this.getScreenTileSize() * this.grid.getHeight();
    }

    public int getScreenWidth() {
        return this.getScreenTileSize() * this.grid.getWidth();
    }

    public int getScreenOffsetX() {
        return (this.getWidth() - this.getScreenWidth()) / 2;
    }

    public int getScreenOffsetY() {
        return (this.getHeight() - this.getScreenHeight()) / 2;
    }

    public void update() {
        ArrayList<Player> playersLost = new ArrayList<Player>();

        // Move players
        for (Player player : this.players) {
            try {
                player.move();
            } catch (PlayerMoveOutOfBoundsException ex) {
                // Player will go out of bounds
                playersLost.add(player);
            }
        }

        // Check for collisions
        for (Player player : this.players) {
            for (Player otherPlayer : this.players) {
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
        for (Player player : this.players) {
            player.draw(g);
        }
    }
}
