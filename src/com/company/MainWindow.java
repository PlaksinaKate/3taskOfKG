package com.company;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private DrawPanel dp;
    private JPanel panel;
    private JLabel r;
    private JSpinner spinner;

    public MainWindow() throws HeadlessException {
        dp = new DrawPanel();
        dp.setSize(1000, 900);
        this.add(dp);
    }
}
