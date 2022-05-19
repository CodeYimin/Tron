package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import misc.WidthHeight;
import player.Player;
import player.PlayerControls;
import player.PlayerMoveOutOfBoundsException;

public class Arena extends JPanel {
    private WidthHeight dimensions;
    private boolean hackUsed = false;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<MatchEndListener> matchEndListeners = new ArrayList<>();

    public WidthHeight getDimensions() {
        return this.dimensions;
    }

    public int getScreenTileSize() {
        return Math.min(super.getParent().getWidth() / this.dimensions.getWidth(), super.getParent().getHeight() / this.dimensions.getHeight());
    }

    public int getScreenHeight() {
        return this.getScreenTileSize() * this.dimensions.getHeight();
    }

    public int getScreenWidth() {
        return this.getScreenTileSize() * this.dimensions.getWidth();
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        super.addKeyListener(player);
    }

    public void addMatchEndListener(MatchEndListener matchEndListener) {
        this.matchEndListeners.add(matchEndListener);
    }

    public Arena(WidthHeight grid) {
        super.setFocusable(true);
        this.dimensions = grid;
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
            if (playerA.isAlive() && playerACollided) {
                playersLost.add(playerA);
            }
        }

        // When someone loses
        if (playersLost.size() > 0) {
            for (Player player : playersLost) {
                player.setAlive(false);
            }
        }

        // Get how many players remaining
        int playersRemaining = 0;
        for (Player player : players) {
            if (player.isAlive()) {
                playersRemaining++;
            }
        }

        // Emit match end events
        if (playersRemaining == 0) {
            for (MatchEndListener matchEndListener : this.matchEndListeners) {
                matchEndListener.matchEnded(null);
            }
        } else if (playersRemaining == 1) {
            // Track if winner is found to avoid case where a match end listener resets the
            // players, resulting in dead players becoming alive again
            // before the loop ends, making them another winner
            boolean winnerFound = false;
            for (Player player : players) {
                if (!winnerFound && player.isAlive()) {
                    for (MatchEndListener matchEndListener : this.matchEndListeners) {
                        matchEndListener.matchEnded(player);
                        winnerFound = true;
                    }
                }
            }
        }

        // Rerender the screen
        super.repaint();
    }

    public void reset() {
        for (Player player : this.players) {
            player.respawn();
        }
    }

    public void useHack(Player hacker) {
        if (this.hackUsed) {
            return;
        }

        for (Player player : players) {
            if (player != hacker) {
                PlayerControls originalControls = player.getControls();
                PlayerControls invertedControls = new PlayerControls(
                        originalControls.getDownKey(), originalControls.getUpKey(),
                        originalControls.getRightKey(), originalControls.getLeftKey());

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

    public static interface PlayerWinListener {
        public void playerWon(Player player);
    }

    public static interface MatchEndListener {
        public void matchEnded(Player winner);
    }
}
