package com.company;

import com.company.figure.RoundedPolygon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OtherFrame extends JFrame {
    ArrayList<RealPoint> coordinates;
    double radius;

    OtherFrame(RoundedPolygon rp) {
        coordinates = rp.getTops();
        radius = rp.getR();
    }

    public OtherFrame() throws HeadlessException {
    }

    public RoundedPolygon getRoundedPolygon() {
        return new RoundedPolygon(coordinates, radius);
    }

    public interface RoundedPolygonReadyListener {
        void created(RoundedPolygon rp);
    }

    private RoundedPolygonReadyListener listener = null;

    public void setReadyListener(RoundedPolygonReadyListener l) {
        listener = l;
    }

//    public void onClick() {
//        if (listener != null) {
//            listener.created(getRoundedPolygon());
//        }
//        dispose();
//    }
}
