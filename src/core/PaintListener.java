package core;

import java.awt.Dimension;
import java.awt.Graphics;

public interface PaintListener {
    public void onPaintComponent(Graphics g, Dimension panelSize);
}
