package com.company.figure;

import com.company.RealPoint;
import com.company.ScreenConverter;
import com.company.ScreenPoint;
import com.company.arc.ArcDrawer;
import com.company.arc.BresenhamArcDrawer;
import com.company.arc.GraphicsArcDrawer;
import com.company.line.Line;
import com.company.line.LineDrawer;
import com.company.line.WuLineDrawer;
import com.company.pixel.PixelDrawer;

import java.awt.*;

public class RoundedPolygon{
    ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
    int[] xFromFrame;
    int[] yFromFrame;
    int nFromFrame;

    Graphics g;
    //private GraphicsArcDrawer ga = g;
    private Graphics2D gr = (Graphics2D) g;

//    public void addValue(int[] x, int[] y, int n) {
//
//    }

    public void drawRoundedPolygon(ScreenConverter sc, PixelDrawer pd) {
        int[] x;
        int[] y;
        int n;

        x = readArrayFromFrame(xFromFrame);
        y = readArrayFromFrame(yFromFrame);
        n = readNumberFromFrame(nFromFrame);

        double s1 = x[0] * y[1];
        double s2 = y[0] * x[1];
        int p = (int) Math.sqrt(Math.pow((x[1] - x[0]), 2) + Math.pow((y[1] - y[0]), 2));

        for (int i = 1; i <= n - 1; i++) {
            if (i < n - 1) {
                s1 += x[i] * y[i + 1];
                s2 += y[i] * x[i + 1];
                p += (int) Math.sqrt(Math.pow((x[i + 1] - x[i]), 2) + Math.pow((y[i + 1] - y[i]), 2));
            } else if (i == n - 1) {
                s1 += x[i] * y[1];
                s2 += y[i] * x[1];
                p += (int) Math.sqrt(Math.pow((x[1] - x[i]), 2) + Math.pow((y[1] - y[i]), 2));
            }
        }
        double s = (s1 - s2) / 2;

        double radius = s * 2 / p;

        for (int i = 0; i <= n - 1; i++) {

            int x1;
            int y1;
            int x2;
            int y2;
            int x3;
            int y3;

            if (i == 0) {
                x1 = x[n - 1];
                y1 = y[n - 1];
                x2 = x[i];
                y2 = y[i];
                x3 = x[i + 1];
                y3 = y[i + 1];
            } else if (i == n - 1) {
                x1 = x[i - 1];
                y1 = y[i - 1];
                x2 = x[i];
                y2 = y[i];
                x3 = x[0];
                y3 = y[0];
            } else {
                x1 = x[i - 1];
                y1 = y[i - 1];
                x2 = x[i];
                y2 = y[i];
                x3 = x[i + 1];
                y3 = y[i + 1];
            }


            //вектор 1
            double dx1 = x2 - x1;
            double dy1 = y2 - y1;

            //вектор 2
            double dx2 = x2 - x3;
            double dy2 = y2 - y3;

            double angle = (Math.atan2(dy1, dx1) - Math.atan2(dy2, dx2)) / 2;

            // длина отрезка между угловой точкой и точками пересечения с окружностью радиуса

            double segment = radius / Math.abs(Math.tan(angle));

            double length1 = Math.sqrt(dx1 * dx1 + dy1 * dy1);
            double length2 = Math.sqrt(dx2 * dx2 + dy2 * dy2);
            double length = Math.min(length1, length2);

            if (segment > length) {
                segment = length;
                radius = length * Math.abs(Math.tan(angle));

            }

            ScreenPoint p1Cross = new ScreenPoint(x2 - dx1 * segment / length1, y2 - dy1 * segment / length1);
            ScreenPoint p2Cross = new ScreenPoint(x2 - dx2 * segment / length2, y2 - dy2 * segment / length2);

            double dx = x2 * 2 - p1Cross.getX() - p2Cross.getX();
            double dy = y2 * 2 - p1Cross.getY() - p2Cross.getY();

            double l = Math.sqrt(dx * dx - dy * dy);
            double d = Math.sqrt(segment * segment - radius * radius);

            ScreenPoint circlePoint = new ScreenPoint(x2 - dx * d / l, y2 - dy * d / l);
            double startAngle = Math.atan2(p1Cross.getY() - circlePoint.getY(), p1Cross.getX() - circlePoint.getX());
            double arcAngle = Math.atan2(p2Cross.getY() - circlePoint.getY(), p2Cross.getX() - circlePoint.getX());
            double sweepAngle = arcAngle - startAngle;

            if (sweepAngle < 0) {
                startAngle = arcAngle;
                sweepAngle = -sweepAngle;
            }

            if (sweepAngle > Math.PI)
                sweepAngle = Math.PI - sweepAngle;

            Line line1 = new Line(x1, y1, p1Cross.getX(), p1Cross.getY());
            Line line2 = new Line(p2Cross.getX(), p2Cross.getY(), x3, y3);
            LineDrawer ld = new WuLineDrawer(pd);
            drawLine(ld, line1);
            drawLine(ld, line2);

            int left = (int) (circlePoint.getX() - radius);
            int top = (int) (circlePoint.getY() - radius);
            int diameter = (int) (2 * radius);

            ScreenPoint pForArc = new ScreenPoint(left, top, diameter, diameter, (int) (sweepAngle * 180 / Math.PI), (int) (sweepAngle * 180 / Math.PI));
            //ArcDrawer ga = new GraphicsArcDrawer(gr);
            //ga.drawArc(pForArc);

//            ArcDrawer ga = new BresenhamArcDrawer(pd);
//            ga.drawArc(pForArc);

        }

    }

    public static  int[] readArrayFromFrame(int[] x) {
        return x;
    }

    public static int readNumberFromFrame(int n) {
        return n;
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }


    public void moveMarker(RealPoint from, RealPoint to) {

    }
}
