package entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import core.PaintListener;

public class Grid implements PaintListener {
    private int width;
    private int height;
    private double gridLineThicknessPercentage = 0.1;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
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

    @Override
    public void onPaintComponent(Graphics g, Dimension panelSize) {
        int tileSize = (int) Math.min(panelSize.getWidth() / getWidth(), panelSize.getHeight() / getHeight());
        int gridLineThickness = (int) (tileSize * gridLineThicknessPercentage);

        int heightPixels = tileSize * getHeight();
        int widthPixels = tileSize * getWidth();

        int offsetX = (panelSize.width - widthPixels) / 2;
        int offsetY = (panelSize.height - heightPixels) / 2;

        for (int i = 1; i < getWidth(); i++) {
            g.fillRect(
                    i * tileSize + offsetX,
                    0 + offsetY,
                    gridLineThickness,
                    tileSize * getHeight());
        }

        for (int i = 1; i < getHeight(); i++) {
            g.fillRect(
                    0 + offsetX,
                    i * tileSize + offsetY,
                    tileSize * getWidth(),
                    gridLineThickness);
        }

        g.setColor(Color.BLACK);
    }
}
