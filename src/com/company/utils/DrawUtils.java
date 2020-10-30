package com.company.utils;

import com.company.arc.ArcDrawer;
import com.company.line.Line;
import com.company.line.LineDrawer;

public class DrawUtils {
    public static void drawRoundedPolygon(LineDrawer ld, ArcDrawer ad, int[] x, int[] y, int n) {
        for (int i = 1; i < n; i++) {
            Line firstPoint = new Line(x[i - 1], y[i - 1], 0, 0);
            Line secondPoint = new Line(0, 0, x[i], y[i]);
           // ld.drawLine(firstPoint, secondPoint);

        }
    }
}
