package com.company.line;

import com.company.ScreenPoint;
import com.company.pixel.PixelDrawer;

import java.awt.*;

public class BresenhamLineDrawer implements LineDrawer {
    private PixelDrawer pd;

    public BresenhamLineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();


        int dx = (int)(Math.abs(x2 - x1));
        int dy = (int)(Math.abs(y2 - y1));

        int d0 = 0;
        if (dx > dy) {
            for (int i = 0; i <= dx; i++) {
                pd.drawPixel(x1, y1, Color.BLACK);
                d0 += 2 * dy;
                if (y1 > y2 && d0 > dx) {
                    y1--;
                    d0 -= 2 * dx;
                } else if (y1 < y2 && d0 > dx) {
                    y1++;
                    d0 -= 2 * dx;
                }
                if (x1 > x2) {
                    x1--;
                } else {
                    x1++;
                }
            }
        } else {
            for (int i = 0; i <= dy; i++) {
                pd.drawPixel(x1, y1, Color.BLACK);
                d0 += 2 * dx;
                if (x1 > x2 && d0 > dy) {
                    x1--;
                    d0 -= 2 * dy;
                } else if (x1 < x2 && d0 > dy) {
                    x1++;
                    d0 -= 2 * dy;
                }
                if (y1 > y2) {
                    y1--;
                } else {
                    y1++;
                }
            }
        }

    }
}
