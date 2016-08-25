package com.arrwhidev.game;

import java.awt.*;

/**
 * Created by arran on 25/08/16.
 */
public abstract class GameObject {

    protected Color c;
    protected double velX, velY;
    protected int w, h;
    protected int[] verticiesX;
    protected int[] verticiesY;
    protected Point center;

    public void render(Graphics2D g) {
        g.setColor(c);
    }

    public abstract void update(double delta);
}
