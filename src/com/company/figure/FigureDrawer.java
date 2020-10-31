package com.company.figure;

import com.company.RealPoint;
import com.company.ScreenConverter;
import com.company.pixel.PixelDrawer;

import java.util.List;

public interface FigureDrawer {
    void drawRoundedPolygon(ScreenConverter sc, PixelDrawer pd);
    void moveMarker(RealPoint from, RealPoint to);
    List<RealPoint> getMarkers = null;
}
