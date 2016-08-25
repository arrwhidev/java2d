package com.arrwhidev.game;

import java.awt.*;

/**
 * Created by arran on 25/08/16.
 */
public abstract class GameObject {

    protected Color c;
    protected double x, y, w, h, velX, velY;

    public void render(Graphics2D g) {
        g.setColor(c);
    }

    public abstract void update(double delta);
}
