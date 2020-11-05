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
        int x = (int) p.getX();
        int y = (int) p.getY();
        int width = p.getWidth();
        int height = p.getHeight();
        int startAngle = p.getStartAngle();
        int arcAngle = p.getArcAngle();
        g.setColor(Color.BLUE);
        g.drawArc( x, y, width, height, startAngle, arcAngle);
    }

}
