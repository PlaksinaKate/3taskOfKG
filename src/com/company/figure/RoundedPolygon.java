package com.company.figure;

import com.company.RealPoint;
import com.company.ScreenConverter;
import com.company.ScreenPoint;
import com.company.arc.ArcDrawer;
import com.company.arc.ArcInfo;
import com.company.line.Line;
import com.company.line.LineDrawer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoundedPolygon {
    ArrayList<RealPoint> tops = new ArrayList<>();
    double r = 0.5;

    public void drawRoundedPolygon(ScreenConverter sc, LineDrawer ld, ArcDrawer ad) {
        RealPoint pd1 = new RealPoint(-1, -2);
        RealPoint pd2 = new RealPoint(2, 2);
        RealPoint pd3 = new RealPoint(5, 1);
        tops.add(pd1);
        tops.add(pd2);
        tops.add(pd3);

        Map<Integer, Double> mX = new HashMap<>();
        Map<Integer, Double> mY = new HashMap<>();
        int count = 0;

        for (int i = 0; i < tops.size(); i++) {

            double x1;
            double y1;
            double x2;
            double y2;
            double x3;
            double y3;

            if (i == 0) {
                x1 = tops.get(tops.size() - 1).getX();
                y1 = tops.get(tops.size() - 1).getY();
                x2 = tops.get(i).getX();
                y2 = tops.get(i).getY();
                x3 = tops.get(i + 1).getX();
                y3 = tops.get(i + 1).getY();
            } else if (i == tops.size() - 1) {
                x1 = tops.get(i - 1).getX();
                y1 = tops.get(i - 1).getY();
                x2 = tops.get(i).getX();
                y2 = tops.get(i).getY();
                x3 = tops.get(0).getX();
                y3 = tops.get(0).getY();
            } else {
                x1 = tops.get(i - 1).getX();
                y1 = tops.get(i - 1).getY();
                x2 = tops.get(i).getX();
                y2 = tops.get(i).getY();
                x3 = tops.get(i + 1).getX();
                y3 = tops.get(i + 1).getY();
            }


            //вектор 1
            double dx1 = x2 - x1;
            double dy1 = y2 - y1;

            //вектор 2
            double dx2 = x2 - x3;
            double dy2 = y2 - y3;

            double angle = (Math.atan2(dy1, dx1) - Math.atan2(dy2, dx2)) / 2;

            // длина отрезка между угловой точкой и точками пересечения с окружностью радиуса

            double segment = r / Math.abs(Math.tan(angle));

            double length1 = Math.sqrt(dx1 * dx1 + dy1 * dy1);
            double length2 = Math.sqrt(dx2 * dx2 + dy2 * dy2);

            double length = Math.min(length1, length2);

            if (segment > length) {
                segment = length;
                r = length * Math.abs(Math.tan(angle));
            }
            double p1X = x2 - dx1 * segment / length1;
            double p1Y = y2 - dy1 * segment / length1;
            double p2X = x2 - dx2 * segment / length2;
            double p2Y = y2 - dy2 * segment / length2;

            double dx = x2 * 2 - p1X - p2X;
            double dy = y2 * 2 - p1Y - p2Y;


            double l = Math.sqrt(dx * dx - dy * dy);
            double d = Math.sqrt(segment * segment - r * r);

            double circlePointX = x2 - dx * d / l;
            double circlePointY = y2 - dy * d / l;

            double startAngle = Math.atan2(p1Y - circlePointY, p1X - circlePointX);
            double arcAngle = Math.atan2(p2Y - circlePointY, p2X - circlePointX);
            double sweepAngle = arcAngle - startAngle;

            if (sweepAngle < 0) {
                startAngle = arcAngle;
                sweepAngle = -sweepAngle;
            }

            if (sweepAngle > Math.PI)
                sweepAngle = Math.PI - sweepAngle;

            count++;
            mX.put(count, p1X);
            mY.put(count, p1Y);
            count++;
            mX.put(count, p2X);
            mY.put(count, p2Y);

            int left = (int) (circlePointX - r);
            int top = (int) (circlePointY - r);
            int diameter = (int) (2 * r);

            RealPoint pCoordinate = new RealPoint(left, top);
            RealPoint pR = new RealPoint(diameter, diameter);
            ArcInfo p = new ArcInfo(sc.r2s(pCoordinate).getX(), sc.r2s(pCoordinate).getY(), sc.r2s(pR).getX(), sc.r2s(pR).getY(), (int) (startAngle * 180 / Math.PI), (int) (sweepAngle * 180 / Math.PI));
            ad.drawArc(p);
        }
        for (int i = 1; i <= mX.size(); i = i + 2) {
            Line l;
            if (i == 1) {
                l = new Line(mX.get(mX.size()), mY.get(mY.size()), mX.get(i), mY.get(i));
            } else {
                l = new Line(mX.get(i - 1), mY.get(i - 1), mX.get(i), mY.get(i));
            }

            ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
        }

    }


    public void moveMarker(RealPoint from, RealPoint to) {

    }

    public ArrayList<RealPoint> getTops() {
        return tops;
    }

    public void setTops(ArrayList<RealPoint> tops) {
        this.tops = tops;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }
}
