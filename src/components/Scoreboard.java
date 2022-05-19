package components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import entities.Player;

public class Scoreboard extends JPanel {
    private ArrayList<PlayerScore> playerScores = new ArrayList<>();

    public Scoreboard() {
        super.setMinimumSize(new Dimension(0, 100));
    }

    public void addPlayer(Player player) {
        PlayerScore playerScore = new PlayerScore(player);
        playerScores.add(new PlayerScore(player));
        super.add(playerScore);
    }

    public void update() {
        super.repaint();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (PlayerScore playerScore : playerScores) {
            playerScore.repaint();
        }
    }

    private static class PlayerScore extends JPanel {
        private Player player;
        private JLabel playerScore;

        public PlayerScore(Player player) {
            this.player = player;

            super.setLayout(new GridBagLayout());
            super.setPreferredSize(new Dimension(200, 100));

            this.playerScore = new JLabel(Integer.toString(this.player.getScore()));
            this.playerScore.setFont(new Font("Calibri", Font.BOLD, 20));
            super.add(this.playerScore);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            this.playerScore.setText(Integer.toString(this.player.getScore()));

            g.setColor(this.player.getColor());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
