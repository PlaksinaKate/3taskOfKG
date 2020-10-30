package com.company;

public class ScreenPoint {
    private int x;
    private int y;
    private int width;
    private int height;
    private int startAngle;
    private int arcAngle;

    public ScreenPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ScreenPoint(int x, int y, int width, int height, int startAngle, int arcAngle) {
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
