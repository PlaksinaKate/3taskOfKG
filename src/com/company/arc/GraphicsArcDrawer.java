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
    public void drawArc(ArcInfo p) {
        int x = (int) p.getP().getX();
        int y = (int) p.getP().getY();
        int width = p.getWidth();
        int height = p.getHeight();
        int startAngle = p.getStartAngle();
        int arcAngle = p.getArcAngle();
        g.drawArc(x, y, width, height, startAngle, arcAngle);
    }

}
