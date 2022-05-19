package screens;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import components.Arena;
import components.Scoreboard;
import core.Updatable;
import misc.Const;
import misc.WidthHeight;
import misc.XY;
import player.Player;
import player.PlayerControls;

public class GameScreen extends JPanel implements Updatable {
    private JPanel arenaContainer;
    private Arena arena;
    private Scoreboard scoreboard;
    private ArrayList<Player> players = new ArrayList<>();

    public GameScreen(WidthHeight arenaSize) {
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        super.setFocusable(true);

        this.arenaContainer = new JPanel(new GridBagLayout());
        this.arena = new Arena(arenaSize);
        this.scoreboard = new Scoreboard();

        arenaContainer.add(this.arena);

        super.add(this.arenaContainer);
        super.add(this.scoreboard);

        XY player1DefaultPos = XY.ZERO;
        XY player2DefaultPos = new XY(arenaSize.getWidth() - 1, arenaSize.getHeight() - 1);
        XY player1DefaultVelocity = XY.DOWN;
        XY player2DefaultVelocity = XY.UP;
        PlayerControls player1Controls = Const.DEFAULT_PLAYER_ONE_CONTROLS;
        PlayerControls player2Controls = Const.DEFAULT_PLAYER_TWO_CONTROLS;
        Color player1Color = new Color(157, 239, 255);
        Color player2Color = new Color(253, 193, 1);
        Player player1 = new Player(this.arena, player1DefaultPos, player1DefaultVelocity, player1Controls, player1Color);
        Player player2 = new Player(this.arena, player2DefaultPos, player2DefaultVelocity, player2Controls, player2Color);
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
