package com.company;

public class ScreenPoint {
    private double x;
    private double y;
    private int width;
    private int height;
    private int startAngle;
    private int arcAngle;

    public ScreenPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public ScreenPoint(double x, double y, int width, int height, int startAngle, int arcAngle) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public int getArcAngle() {
        return arcAngle;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
