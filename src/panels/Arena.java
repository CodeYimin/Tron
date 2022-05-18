package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import core.Game;
import entities.Player;
import entities.PlayerControls;
import entities.PlayerMoveOutOfBoundsException;
import misc.Dimension;
import misc.Vector;

public class Arena extends JPanel {
    private Game game;
    private Dimension grid;
    private ArrayList<Player> players = new ArrayList<>();
    private boolean hackUsed = false;

    public Arena(Game game, Dimension grid) {
        this.game = game;
        this.grid = grid;

        this.setFocusable(true);

        Color player1Color = new Color(157, 239, 255);
        Color player2Color = new Color(253, 193, 1);

        PlayerControls player1Controls = new PlayerControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q);
        PlayerControls player2Controls = new PlayerControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        Player player1 = new Player(this, new Vector(0, 0), Vector.DOWN, player1Controls, player1Color);
        Player player2 = new Player(this, new Vector(this.grid).subtract(1), Vector.UP, player2Controls, player2Color);
        this.players.add(player1);
        this.players.add(player2);

        for (Player player: this.players) {
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
        for (Player player: this.players) {
            try {
                player.move();
            } catch (PlayerMoveOutOfBoundsException ex) {
                // Player will go out of bounds
                playersLost.add(player);
            }
        }

        // Check for collisions
        for(Player playerA: players) {
            boolean playerACollided = false;
            for(Player playerB: players) {
                if(playerA.collidesWith(playerB)) {
                    playerACollided = true;
                }
            }
            if(playerACollided) playersLost.add(playerA);
        }

        // When someone loses
        if (playersLost.size() > 0) {
            for (Player player: playersLost) {
                player.setState(Player.DEAD);
            }
        }

        int playersRemaining = 0;
        for(Player player: players) {
            if(player.getState() == Player.ALIVE) playersRemaining++;
        }
        if(playersRemaining <= 1) {
            game.endRound();
        }
    }

    public void useHack(Player playerUsed) {
        if(this.hackUsed) {
            return;
        }
        PlayerControls newControls = new PlayerControls(KeyEvent.VK_DOWN, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);
        for(Player player: players) {
            if(player != playerUsed) {
                player.setControls(newControls);
            }
        }
        new PlayerControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        this.hackUsed = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Draw the arena play area
        g.setColor(Color.BLACK);
        g.fillRect(getScreenOffsetX(), getScreenOffsetY(), getScreenWidth(), getScreenHeight());

        // Draw Players
        for (Player player: this.players) {
            player.draw(g);
        }
    }
}
