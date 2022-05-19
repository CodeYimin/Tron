package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import entities.Player;
import entities.PlayerControls;
import entities.PlayerMoveOutOfBoundsException;
import misc.WidthHeight;
import misc.XY;

public class Arena extends JPanel {
    private WidthHeight grid;
    private ArrayList<Player> players = new ArrayList<>();
    private boolean hackUsed = false;

    public WidthHeight getGrid() {
        return this.grid;
    }

    public int getScreenTileSize() {
        return Math.min(this.getParent().getWidth() / this.grid.getWidth(), this.getParent().getHeight() / this.grid.getHeight());
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

    public Arena(WidthHeight grid) {
        super.setFocusable(true);
        this.grid = grid;

        PlayerControls player1Controls = new PlayerControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q);
        PlayerControls player2Controls = new PlayerControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        Color player1Color = new Color(157, 239, 255);
        Color player2Color = new Color(253, 193, 1);
        Player player1 = new Player(this, new XY(0, 0), XY.DOWN, player1Controls, player1Color);
        Player player2 = new Player(this, new XY(this.grid).subtract(1), XY.UP, player2Controls, player2Color);
        this.players.add(player1);
        this.players.add(player2);

        for (Player player : this.players) {
            super.addKeyListener(player);
        }
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
        for (Player playerA : players) {
            boolean playerACollided = false;
            for (Player playerB : players) {
                if (playerA.headCollidesWith(playerB)) {
                    playerACollided = true;
                }
            }
            if (playerACollided) {
                playersLost.add(playerA);
            }
            ;
        }

        // When someone loses
        if (playersLost.size() > 0) {
            for (Player player : playersLost) {
                player.setState(Player.DEAD);
            }
        }

        // Get how many players remaining to determine whether to end the game
        int playersRemaining = 0;
        for (Player player : players) {
            if (player.getState() == Player.ALIVE) {
                playersRemaining++;
            }
        }
        if (playersRemaining <= 1) {
            // game.endRound();
        }

        // Rerender the screen
        super.repaint();
    }

    public void useHack(Player hacker) {
        if (this.hackUsed) {
            return;
        }

        for (Player player : players) {
            if (player != hacker) {
                PlayerControls controls = player.getControls();
                PlayerControls invertedControls = new PlayerControls(
                        controls.getDownKey(),
                        controls.getUpKey(),
                        controls.getRightKey(),
                        controls.getLeftKey(),
                        controls.getHackKey());

                player.setControls(invertedControls);
            }
        }

        this.hackUsed = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw Players
        for (Player player : this.players) {
            player.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = new Dimension(getScreenWidth(), getScreenHeight());
        return dimension;
    }
}
