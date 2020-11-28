package com.company.figure;

import com.company.RealPoint;
import com.company.ScreenConverter;
import com.company.arc.ArcDrawer;
import com.company.arc.ArcInfo;
import com.company.line.Line;
import com.company.line.LineDrawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoundedPolygon {
    ArrayList<RealPoint> tops;
    double r;
    double r2;
    boolean check;

    public RoundedPolygon(ArrayList<RealPoint> tops, double r) {
        this.tops = tops;
        this.r = r;
    }

    public void drawRoundedPolygon(ScreenConverter sc, LineDrawer ld, ArcDrawer ad) {
        check = false;
        Map<Integer, RealPoint> points = new HashMap<>();
        int count = 0;
        if (tops.size() > 1) {
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

                double dx1 = x2 - x1;
                double dy1 = y2 - y1;

                double dx2 = x2 - x3;
                double dy2 = y2 - y3;

                double angle = (Math.atan2(dy1, dx1) - Math.atan2(dy2, dx2)) / 2; // угол между прямыми

                // длина отрезка между угловой точкой и точками пересечения с окружностью радиуса
                double segment = r / Math.abs(Math.tan(angle));

                // проверка длины сегмента и мин. длины прямых
                double length1 = Math.sqrt(dx1 * dx1 + dy1 * dy1);
                double length2 = Math.sqrt(dx2 * dx2 + dy2 * dy2);

                double length = Math.min(length1, length2);

                if (segment > length) {
                    segment = length;
                    r2 = r;
                    r = length * Math.abs(Math.tan(angle));
                    check = true;
                }
                if (!check && r2 != 0) {
                    r = r2;
                }

                double p1X = x2 - dx1 * segment / length1;
                double p1Y = y2 - dy1 * segment / length1;
                double p2X = x2 - dx2 * segment / length2;
                double p2Y = y2 - dy2 * segment / length2;

                RealPoint p1 = new RealPoint(p1X, p1Y);
                RealPoint p2 = new RealPoint(p2X, p2Y);
                count++;
                points.put(count, p1);
                count++;
                points.put(count, p2);

                //рассчет цента окружности
                double dx = x2 * 2 - p1X - p2X;
                double dy = y2 * 2 - p1Y - p2Y;

                double l = Math.sqrt(dx * dx + dy * dy);
                double d = Math.sqrt(segment * segment + r * r);

                double circlePointX = x2 - dx * d / l;
                double circlePointY = y2 - dy * d / l;

                //рассчет уголов
                double startAngle = Math.atan2(p1Y - circlePointY, p1X - circlePointX);
                double endAngle = Math.atan2(p2Y - circlePointY, p2X - circlePointX);

                double sweepAngle = endAngle - startAngle;
                // sweepAngle = sweepAngle * 180 / Math.PI;
                if (sweepAngle < 0) {
                    startAngle = endAngle;
                    sweepAngle = -sweepAngle;
                }
                if (sweepAngle > Math.PI) {
                    sweepAngle = -(sweepAngle + 2 * (Math.PI - sweepAngle));

                }


                double left = circlePointX - r;
                double top = circlePointY + r;
                RealPoint pCoordinate = new RealPoint(left, top);
                ArcInfo p = new ArcInfo((sc.r2s(pCoordinate)).getX(), (sc.r2s(pCoordinate)).getY(), (int) (2 * r * sc.getScreenWidth() / sc.getRealWidth()), (int) (2 * r * sc.getScreenHeight() / sc.getRealHeight()), (int) (startAngle * 180 / Math.PI), (int) (sweepAngle * 180 / Math.PI));
                ad.drawArc(p);
            }

            for (int i = 1; i <= points.size(); i = i + 2) {
                Line l;
                if (i == 1) {
                    l = new Line(points.get(points.size()).getX(), points.get(points.size()).getY(), points.get(i).getX(), points.get(i).getY());
                } else {
                    l = new Line(points.get(i - 1).getX(), points.get(i - 1).getY(), points.get(i).getX(), points.get(i).getY());
                }

                ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
            }
        }
    }

    public ArrayList<RealPoint> getTops() {
        return tops;
    }


    public double getR() {
        return r;
    }

    public void setTops(ArrayList<RealPoint> tops) {
        this.tops = tops;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean checkInside(RealPoint rp) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = tops.size() - 1; i < tops.size(); j = i++) {
            if ((tops.get(i).getY() > rp.getY()) != (tops.get(j).getY() > rp.getY()) &&
                    (rp.getX() < (tops.get(j).getX() - tops.get(i).getX()) * (rp.getY() - tops.get(i).getY()) / (tops.get(j).getY() - tops.get(i).getY()) + tops.get(i).getX())) {
                result = !result;
            }
        }
//        boolean result = false;
//        int j = tops.size() - 1;
//        for (int i = 0; i < tops.size(); i++) {
//            if ((tops.get(i).getY() < rp.getY() && tops.get(j).getY() >= rp.getY() || tops.get(j).getY() < rp.getY() && tops.get(i).getY() >= rp.getY()) &&
//                    (tops.get(i).getX() + (rp.getY() - tops.get(i).getY()) / (tops.get(j).getY() - tops.get(i).getY()) * (tops.get(i).getX() - tops.get(i).getX()) < rp.getX()))
//                result = !result;
//            j = i;
//        }

        return result;
    }
}
