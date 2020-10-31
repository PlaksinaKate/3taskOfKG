package com.company.arc;

import com.company.ScreenPoint;
import com.company.pixel.PixelDrawer;

import java.awt.*;

public class GraphicsArcDrawer implements ArcDrawer {
    Graphics g;

    public GraphicsArcDrawer(Graphics g) {
        this.g = g;
    }

    @Override
    public void drawArc(ScreenPoint p) {
        double x = p.getX();
        double y = p.getY();
        int width = p.getWidth();
        int height = p.getHeight();
        int startAngle = p.getStartAngle();
        int arcAngle = p.getArcAngle();
        //setColor(Color.BLUE);
        g.drawArc((int) x, (int) y, width, height, startAngle, arcAngle);
    }

    public Color setColor(Color c) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue());
    }
}
