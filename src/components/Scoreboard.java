package components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import player.Player;

public class Scoreboard extends JPanel {
    private ArrayList<PlayerScore> playerScores = new ArrayList<>();

    public Scoreboard() {
        super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    public void addPlayer(Player player) {
        PlayerScore playerScore = new PlayerScore(player);
        playerScores.add(new PlayerScore(player));
        super.add(playerScore);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.setPreferredSize(new Dimension(300, super.getParent().getHeight() / 8));
    }

    private static class PlayerScore extends JPanel {
        private Player player;
        private JLabel playerScore;

        public PlayerScore(Player player) {
            this.player = player;

            super.setLayout(new GridBagLayout());

            this.playerScore = new JLabel(Integer.toString(this.player.getScore()));
            super.add(this.playerScore);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            this.playerScore.setFont(new Font("Calibri", Font.BOLD, super.getHeight() / 5));
            this.playerScore.setText(Integer.toString(this.player.getScore()));

            g.setColor(this.player.getColor());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
