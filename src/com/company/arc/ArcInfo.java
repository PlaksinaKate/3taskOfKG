package com.company.arc;

import com.company.RealPoint;

public class ArcInfo {
    private double x;
    private double y;
    private int width;
    private int height;
    private int startAngle;
    private int arcAngle;
    private RealPoint p;

    public ArcInfo(double x, double y, int width, int height, int startAngle, int arcAngle) {
        this.p = new RealPoint(x, y);
        this.width = width;
        this.height = height;
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
    }

    public ArcInfo(RealPoint p) {
        this.p = p;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getArcAngle() {
        return arcAngle;
    }

    public void setArcAngle(int arcAngle) {
        this.arcAngle = arcAngle;
    }

    public RealPoint getP() {
        return p;
    }

    public void setP(RealPoint p) {
        this.p = p;
    }
}
