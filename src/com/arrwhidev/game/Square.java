package com.arrwhidev.game;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by arran on 25/08/16.
 */
public class Square extends GameObject {

    private static final int MIN_SIDE = 50;
    private static final int MAX_SIDE = 50;
    private static final double MIN_VELOCITY = 0.5;
    private static final double MAX_VELOCITY = 7;

    private int canvasWidth;
    private int canvasHeight;

    public Square(int canvasWidth, int canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        ThreadLocalRandom r = ThreadLocalRandom.current();

        // Random dimensions.
        int side = r.nextInt(MIN_SIDE, MAX_SIDE + 1);
        this.w = side;
        this.h = side;

        // Random position in bounds.
        int topLeftX = r.nextInt(0, (canvasWidth - this.w) + 1);
        int topLeftY = r.nextInt(0, (canvasHeight - this.h) + 1);

        // Random velocity.
        this.velX = r.nextDouble(MIN_VELOCITY, MAX_VELOCITY + 1);
        this.velY = r.nextDouble(MIN_VELOCITY, MAX_VELOCITY + 1);

        // Random color.
        this.c = Color.getHSBColor(r.nextFloat(), r.nextFloat(), r.nextFloat());

        // Construct verticies
        this.verticiesX = new double[] {
            topLeftX,
            topLeftX + side,
            topLeftX + side,
            topLeftX
        };
        this.verticiesY = new double[] {
            topLeftY,
            topLeftY,
            topLeftY + side,
            topLeftY + side
        };
        calculateCenter();
    }

    private void calculateCenter() {
        this.center = new Point((int)verticiesX[0] + (w / 2), (int)verticiesY[0] + (h / 2));
    }

    public void render(Graphics2D g) {
        super.render(g);

        // Fill the polygon.
        // Using GeneralPath because Polygon only supports ints as coords.
        // GeneralPath allows me to use doubles instead, which are more accurate.
        GeneralPath polygon = new GeneralPath();
        polygon.moveTo(verticiesX[0], verticiesY[0]);
        for(int i = 1; i < verticiesX.length; i++) {
            polygon.lineTo(verticiesX[i], verticiesY[i]);
        }
        g.fill(polygon);

        // Draw center
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.WHITE);
        g.drawLine(center.x, center.y, center.x, center.y);
    }

    public void update(double delta) {
        // Reverse velocity when touch perimeter.
        if(verticiesY[0] + h >= canvasHeight || verticiesY[0] <= 0) velY *= -1;
        if(verticiesX[0] + w >= canvasWidth || verticiesX[0] <= 0) velX *= -1;

        // Apply velocity to verticies
        for(int i = 0; i < verticiesX.length; i++) {
            verticiesX[i] += velX;
            verticiesY[i] += velY;
        }

        // Update center.
        calculateCenter();
    }

    // TODO - Rotate stuff later...
    /*
    public Vertex rotateVertex(Vertex pt, Vertex center, double angle) {
        double newX = center.x + (pt.x-center.x)*Math.cos(angle) - (pt.y-center.y)*Math.sin(angle);
        double newY = center.y + (pt.x-center.x)*Math.sin(angle) + (pt.y-center.y)*Math.cos(angle);
        return new Vertex(newX, newY);
    }
    */
}
