package com.company.arc;

import com.company.RealPoint;

public class Arc {

    private RealPoint p;

    public Arc(RealPoint p) {
        this.p = p;
    }

    public Arc(double x, double y, double width, double height, double startAngle, double arcAngle) {
        p = new RealPoint(x, y, width, height, startAngle, arcAngle);
    }

    public RealPoint getP() {
        return p;
    }

    public void setP(RealPoint p) {
        this.p = p;
    }
}
