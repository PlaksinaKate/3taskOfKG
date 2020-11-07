package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        MainWindow frame = new MainWindow();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocation(200, 20);
        frame.setVisible(true);

    }
}
