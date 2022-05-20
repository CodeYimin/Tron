package screens;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JPanel;

import audio.Music;
import components.Arena;
import components.MatchStatus;
import components.Scoreboard;
import core.Screen;
import core.Updatable;
import misc.Const;
import misc.WidthHeight;
import misc.XY;
import player.Player;
import player.PlayerControls;

public class BattleScreen extends Screen implements Updatable {
    private JPanel arenaContainer;
    private Arena arena;
    private Scoreboard scoreboard;
    private MatchStatus matchStatus;
    private ArrayList<Player> players = new ArrayList<>();
    private int maxWins;
    private ArrayList<GameOverListener> gameOverListeners = new ArrayList<>();
    private Music music;
    private boolean gameOver;

    public BattleScreen(WidthHeight arenaSize, int maxWins) {
        super.setLayout(new GridBagLayout());
        super.setFocusable(true);

        this.maxWins = maxWins;
        this.arena = new Arena(arenaSize);
        this.arenaContainer = new JPanel(new GridBagLayout());
        GridBagConstraints arenaContainerGbc = new GridBagConstraints();
        arenaContainerGbc.anchor = GridBagConstraints.PAGE_START;
        arenaContainerGbc.gridy = 0;
        arenaContainerGbc.weighty = 0;
        this.arenaContainer.add(this.arena, arenaContainerGbc);
        arenaContainerGbc.gridy = 1;
        arenaContainerGbc.weighty = 1;
        this.arenaContainer.add(Box.createVerticalGlue(), arenaContainerGbc);

        this.scoreboard = new Scoreboard();
        this.matchStatus = new MatchStatus("Match in progress!");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridy = 0;
        super.add(this.scoreboard, gbc);

        gbc.gridy = 1;
        super.add(this.matchStatus, gbc);

        gbc.gridy = 2;
        gbc.weighty = 2;
        super.add(this.arenaContainer, gbc);

        XY player1DefaultPos = XY.ZERO;
        XY player2DefaultPos = new XY(arenaSize.getWidth() - 1, arenaSize.getHeight() - 1);
        XY player1DefaultVelocity = XY.DOWN;
        XY player2DefaultVelocity = XY.UP;
        PlayerControls player1Controls = Const.DEFAULT_PLAYER_ONE_CONTROLS;
        PlayerControls player2Controls = Const.DEFAULT_PLAYER_TWO_CONTROLS;
        Color player1Color = new Color(157, 239, 255);
        Color player2Color = new Color(253, 193, 1);
        Player player1 = new Player("Player 1", this.arena, player1DefaultPos, player1DefaultVelocity, player1Controls, player1Color);
        Player player2 = new Player("Player 2", this.arena, player2DefaultPos, player2DefaultVelocity, player2Controls, player2Color);
        this.addPlayer(player1);
        this.addPlayer(player2);

        this.arena.addMatchEndListener(new MatchEndListener());
    }

    public void addPlayer(Player player) {
        super.addKeyListener(player);
        this.players.add(player);
        this.arena.addPlayer(player);
        this.scoreboard.addPlayer(player);
    }

    public void addGameOverListener(GameOverListener listener) {
        this.gameOverListeners.add(listener);
    }

    @Override
    public void update() {
        this.arena.update();
    }

    @Override
    public void onEnter() {
        this.music = new Music(Const.BATTLE_MUSIC);
        music.start();
    }

    @Override
    public void onExit() {
        this.music.close();
    }

    private class MatchEndListener implements Arena.MatchEndListener {
        public void matchEnded(Player winner) {
            // Freeze all players
            BattleScreen.this.arena.killAllPlayers();

            // Return if game is already over
            if (BattleScreen.this.gameOver) {
                return;
            }

            Music roundOverMusic = new Music(Const.ROUND_OVER_MUSIC);
            Music roundStartMusic = new Music(Const.ROUND_START_MUSIC);
            roundOverMusic.start();

            // No winner, tie
            if (winner == null) {
                roundStartMusic.start();
                for (int i = 3; i > 0; i--) {
                    BattleScreen.this.matchStatus.setText("Tie! New round in " + i + "...");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // Winner found
                winner.addScore(1);

                // Check if winner has won the best of x
                if (winner.getScore() >= maxWins) {
                    BattleScreen.this.gameOver(winner);
                    return;
                }

                // New round starting
                roundStartMusic.start();
                for (int i = 3; i > 0; i--) {
                    BattleScreen.this.matchStatus.setText(winner.getName() + " wins! New round in " + i + "...");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // Start new round
            BattleScreen.this.arena.reset();
            BattleScreen.this.matchStatus.setText("Match in progress!");
        }
    }

    private void gameOver(Player winner) {
        this.gameOver = true;

        this.matchStatus.setText(winner.getName() + " won the best of " + maxWins + "! Press space to continue...");
        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    for (GameOverListener gameOverListener : gameOverListeners) {
                        gameOverListener.gameOver(winner);
                    }
                    BattleScreen.super.removeKeyListener(this);
                }
            }
        };

        super.addKeyListener(keyListener);
    }

    public static interface GameOverListener {
        public void gameOver(Player winner);
    }
}
