package entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import core.PaintListener;

public class Arena implements PaintListener, KeyListener {
    private int width;
    private int height;
    private double gridLineThicknessPercentage = 0.1;
    private ArrayList<Player> players = new ArrayList<>();

    public Arena(int width, int height, ArrayList<Player> players) {
        this.width = width;
        this.height = height;
        this.players = players;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getScreenTileSize(Dimension panelSize) {
        return (int) Math.min(panelSize.getWidth() / getWidth(), panelSize.getHeight() / getHeight());
    }

    public int getScreenGridlineThickness(Dimension panelSize) {
        return (int) (getScreenTileSize(panelSize) * gridLineThicknessPercentage);
    }

    public int getScreenHeight(Dimension panelSize) {
        return getScreenTileSize(panelSize) * getHeight();
    }

    public int getScreenWidth(Dimension panelSize) {
        return getScreenTileSize(panelSize) * getWidth();
    }

    public int getScreenOffsetX(Dimension panelSize) {
        return (panelSize.width - getScreenWidth(panelSize)) / 2;
    }

    public int getScreenOffsetY(Dimension panelSize) {
        return (panelSize.height - getScreenHeight(panelSize)) / 2;
    }

    @Override
    public void onPaintComponent(Graphics g, Dimension panelSize) {
        int tileSize = getScreenTileSize(panelSize);
        int gridlineThickness = getScreenGridlineThickness(panelSize);

        int offsetX = getScreenOffsetX(panelSize);
        int offsetY = getScreenOffsetY(panelSize);

        // Draw Grid
        g.setColor(Color.BLACK);

        for (int i = 1; i < getWidth(); i++) {
            g.fillRect(
                    i * tileSize + offsetX,
                    0 + offsetY,
                    gridlineThickness,
                    tileSize * getHeight());
        }

        for (int i = 1; i < getHeight(); i++) {
            g.fillRect(
                    0 + offsetX,
                    i * tileSize + offsetY,
                    tileSize * getWidth(),
                    gridlineThickness);
        }

        // Draw Players
        for (Player player : players) {
            player.onPaintComponent(g, panelSize);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (Player player : players) {
            player.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (Player player : players) {
            player.keyReleased(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        for (Player player : players) {
            player.keyTyped(e);
        }
    }
}
