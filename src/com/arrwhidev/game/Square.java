package com.arrwhidev.game;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Square extends GameObject {

    private static final int MIN_SIDE = 50;
    private static final int MAX_SIDE = 50;
    private static final double MIN_VELOCITY = 0.5;
    private static final double MAX_VELOCITY = 7;

    private int canvasWidth, canvasHeight;

    public Square(int canvasWidth, int canvasHeight) {
        ThreadLocalRandom r = ThreadLocalRandom.current();

        // Random dimensions.
        int side = r.nextInt(MIN_SIDE, MAX_SIDE + 1);
        this.w = side;
        this.h = side;

        // Random position in bounds.
        this.x = r.nextInt(0, (int) (canvasWidth - this.w) + 1);
        this.y = r.nextInt(0, (int) (canvasHeight - this.h) + 1);

        // Random velocity.
        this.velX = r.nextDouble(MIN_VELOCITY, MAX_VELOCITY + 1);
        this.velY = r.nextDouble(MIN_VELOCITY, MAX_VELOCITY + 1);

        // Random color.
        this.c = Color.getHSBColor(r.nextFloat(), r.nextFloat(), r.nextFloat());

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    public void render(Graphics2D g) {
        super.render(g);
        g.fillRect((int) x, (int) y, (int) w, (int) h);
    }

    public void update(double delta) {
        // Reverse velocity when touch perimeter.
        if(y + h >= canvasHeight || y <= 0) velY *= -1;
        if(x + w >= canvasWidth || x <= 0) velX *= -1;

        y += velY;
        x += velX;
    }
}
