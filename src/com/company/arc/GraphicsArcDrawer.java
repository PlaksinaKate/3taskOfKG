package com.company.arc;

import com.company.ScreenPoint;

import java.awt.*;

public class GraphicsArcDrawer implements ArcDrawer {
    private Graphics g;

    public GraphicsArcDrawer(Graphics g) {
        this.g = g;
    }

    @Override
    public void drawArc(ScreenPoint p) {
        int x = p.getX();
        int y = p.getY();
        int width = p.getWidth();
        int height = p.getHeight();
        int startAngle = p.getStartAngle();
        int arcAngle = p.getArcAngle();
        g.drawArc(x, y, width, height, startAngle, arcAngle);
    }
}
