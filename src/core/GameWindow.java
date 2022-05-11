package core;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    private GamePanel panel = new GamePanel();

    public GameWindow(String title, int width, int height) {
        super(title);

        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        add(panel);
    }

    public GamePanel getPanel() {
        return panel;
    }
}
