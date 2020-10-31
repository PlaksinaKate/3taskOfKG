package com.company;

import com.company.frame.FrameMain;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        MainWindow frame = new MainWindow();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        FrameMain f = new FrameMain();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(500, 500);
        f.setVisible(true);



    }
}
