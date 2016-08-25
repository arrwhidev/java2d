package com.arrwhidev.game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arran on 25/08/16.
 */
public class GamePanel extends Canvas implements Runnable {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = WIDTH / 16 * 9;
    private static final double FPS = 60D;
    private static final double NS_PER_TICK = 1000000000D / FPS;

    private static final int NUM_SQUARE = 4;

    private int theFrames = 0, theTicks = 0;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    private Graphics2D g;
    private List<Square> sqs = new ArrayList<>();

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        init();
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    private void init() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        // Add game objects.
        for(int i = 0; i < NUM_SQUARE; i++) {
            sqs.add(new Square(WIDTH, HEIGHT));
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        int frames = 0;
        int ticks = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / NS_PER_TICK;
            lastTime = now;
            boolean shouldRender = true;

            while (delta >= 1) {
                ticks++;
                update(delta);
                delta -= 1;
                shouldRender = true;
            }

            if (shouldRender) {
                frames++;
                render();
                draw();
            }

            // Update fps data.
            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                theFrames = frames;
                theTicks = ticks;
                frames = 0;
                ticks = 0;
            }
        }
    }

    private void update(double delta) {
        sqs.stream().forEach(sq -> sq.update(delta));
    }

    private void render() {
        // Clear screen.
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Render game objects.
        sqs.stream().forEach(sq -> sq.render(g));

        // Render fps data.
        g.setColor(Color.green);
        g.drawString(String.format("FPS: %d Ticks: %d", theFrames, theTicks), 10, 10);
    }

    private void draw() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g2 = bs.getDrawGraphics();
        g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g2.dispose();
        bs.show();
    }
}