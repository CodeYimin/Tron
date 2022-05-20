package core;

import javax.swing.JPanel;

public abstract class Screen extends JPanel {
    public abstract void onEnter();

    public abstract void onExit();
}
