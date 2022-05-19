package screens;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import components.Arena;
import components.Scoreboard;
import core.Updatable;
import entities.Player;
import entities.PlayerControls;
import misc.WidthHeight;
import misc.XY;

public class GameScreen extends Box implements Updatable {
    private JPanel arenaContainer;
    private Arena arena;
    private Scoreboard scoreboard;
    private ArrayList<Player> players = new ArrayList<>();

    public GameScreen() {
        super(BoxLayout.Y_AXIS);

        this.arenaContainer = new JPanel(new GridBagLayout());
        this.arena = new Arena(new WidthHeight(150, 100));
        this.scoreboard = new Scoreboard();

        arenaContainer.add(this.arena);

        super.add(this.arenaContainer);
        super.add(this.scoreboard);

        PlayerControls player1Controls = PlayerControls.HACKER_CONTROLS;
        PlayerControls player2Controls = PlayerControls.NORMAL_CONTROLS;
        Color player1Color = new Color(157, 239, 255);
        Color player2Color = new Color(253, 193, 1);
        Player player1 = new Player(this.arena, new XY(0, 0), XY.DOWN, player1Controls, player1Color);
        Player player2 = new Player(this.arena, new XY(this.arena.getDimensions()).subtract(1), XY.UP, player2Controls, player2Color);
        this.addPlayer(player1);
        this.addPlayer(player2);

        this.arena.addMatchEndListener(new MatchEndListener());
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        this.arena.addPlayer(player);
        this.scoreboard.addPlayer(player);
    }

    @Override
    public void update() {
        this.arena.update();
        this.scoreboard.update();
    }

    private class MatchEndListener implements Arena.MatchEndListener {
        public void matchEnded(Player winner) {
            if (winner != null) {
                winner.addScore(1);
            }

            GameScreen.this.arena.reset();
        }
    }
}
