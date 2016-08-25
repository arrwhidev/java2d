package com.arrwhidev.game;

import javax.swing.*;

/**
 * Created by arran on 25/08/16.
 */
public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new GamePanel());
        window.pack();
        window.setVisible(true);
    }
}