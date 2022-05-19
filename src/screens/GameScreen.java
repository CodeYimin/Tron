package screens;

import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import components.Arena;
import components.Scoreboard;
import core.Updatable;
import misc.WidthHeight;

public class GameScreen extends Box implements Updatable {
    private JPanel arenaContainer;
    private Arena arena;
    private Scoreboard scoreboard;

    public GameScreen() {
        super(BoxLayout.Y_AXIS);

        this.arenaContainer = new JPanel(new GridBagLayout());
        this.arena = new Arena(new WidthHeight(300, 200));
        this.scoreboard = new Scoreboard(0, 100);

        arenaContainer.add(this.arena);

        super.add(this.arenaContainer);
        super.add(this.scoreboard);
    }

    @Override
    public void update() {
        this.arena.update();
        this.scoreboard.update();
    }
}
