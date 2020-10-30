package com.company;

public class RealPoint {
    private double x;
    private double y;
    private double width;
    private double height;
    private double startAngle;
    private double arcAngle;

    public RealPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public RealPoint(double x, double y, double width, double height, double startAngle, double arcAngle) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
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
}
