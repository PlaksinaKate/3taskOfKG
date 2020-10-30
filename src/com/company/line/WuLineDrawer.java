package com.company.line;

import com.company.ScreenPoint;
import com.company.pixel.PixelDrawer;

import java.awt.*;

public class WuLineDrawer implements LineDrawer{
    private PixelDrawer pd;
    private int xStart, xFinish, yStart, yFinish;

    public WuLineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }
    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int count = 1;
        int d0 = 0;
        if (dx > dy) {
            for (int i = 0; i <= dx; i++) {
                drawPixelAsWu(x1, y1, x2, y2, dx, dy, d0, count);
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
                count++;
            }
        } else {
            for (int i = 0; i <= dy; i++) {
                drawPixelAsWu(x1, y1, x2, y2, dx, dy, d0, count);
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
                count++;
            }
        }
    }
    public void drawPixelAsWu(int x1, int y1, int x2, int y2, int dx, int dy, int d0, int count) {
        int d;
        boolean replace = dx > dy;
        if (replace) {
            d = dx != 0 ? (255 * d0) / (2 * dx) : 255;
        } else {
            d = dy != 0 ? (255 * d0) / (2 * dy) : 255;
        }
        int dPos = Math.max(0, d);

        Color c1 = setColor(255 - Math.abs(d), Color.BLUE);
        Color c2 = setColor(Math.abs(d), Color.BLUE);

        if (count == 1) {
            xStart = x1;
            xFinish = x2;
            yStart = y1;
            yFinish = y2;
        }
        if (replace) {
            pd.drawPixel(x1, y1, c1);
            if (dx != 0) {
                if (y1 > y2 || (yStart > yFinish && y1 == y2)) {

                    if (dPos > 0)
                        pd.drawPixel(x1, y1 - 1, c2);
                    else
                        pd.drawPixel(x1, y1 + 1, c2);

                } else if (y1 < y2 || (yStart < yFinish && y1 == y2)) {

                    if (dPos > 0)
                        pd.drawPixel(x1, y1 + 1, c2);
                    else
                        pd.drawPixel(x1, y1 - 1, c2);

                }
            }
        } else {
            pd.drawPixel(x1, y1, c1);
            if (dy != 0) {
                if (x1 < x2 || (xStart < xFinish && x1 == x2)) {
                    if (dPos > 0) {
                        pd.drawPixel(x1 + 1, y1, c2);
                    } else {
                        pd.drawPixel(x1 - 1, y1, c2);
                    }
                } else if (x1 > x2 || (xStart > xFinish && x1 == x2)) {
                    if (dPos > 0) {
                        pd.drawPixel(x1 - 1, y1, c2);
                    } else {
                        pd.drawPixel(x1 + 1, y1, c2);
                    }
                }
            }
        }

    }

    private Color setColor(int n, Color c) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), n);
    }


}
