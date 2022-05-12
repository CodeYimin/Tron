package rendering;

import java.awt.Dimension;
import java.awt.Graphics;

import misc.Vector;

public interface Renderer {

    public void render(Graphics g, Dimension panelSize, Vector offset);
}
