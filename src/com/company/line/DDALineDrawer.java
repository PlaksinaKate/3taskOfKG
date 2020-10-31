package com.company.line;

import com.company.ScreenPoint;
import com.company.pixel.PixelDrawer;

import java.awt.*;

public class DDALineDrawer implements LineDrawer {
    private PixelDrawer pd;

    public DDALineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();


        double dx = x2 - x1;
        double dy = y2 - y1;
        if (Math.abs(dx) > Math.abs(dy)) {
            double k = dy / dx;

            if (x1 > x2) {
                double tmp = x1;
                x1 = x2;
                x2 = tmp;
                tmp = y1;
                y1 = y2;
                y2 = tmp;
            }

            for (double j = x1; j <= x2; j++) {
                double i = k * (j - x1) + y1;
                pd.drawPixel(
                        j, (int) i, Color.red
                );
            }

        } else {
            double k = dx / dy;

            if (y1 > y2) {
                double tmp = x1;
                x1 = x2;
                x2 = tmp;
                tmp = y1;
                y1 = y2;
                y2 = tmp;
            }

            for (double i = y1; i <= y2; i++) {
                double j = k * (i - y1) + x1;
                pd.drawPixel(
                        (int) j, i, Color.BLUE
                );
            }

        }
    }
}

