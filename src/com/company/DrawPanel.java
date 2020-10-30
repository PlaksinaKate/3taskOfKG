package com.company;

import com.company.arc.Arc;
import com.company.arc.ArcDrawer;
import com.company.arc.BresenhamArcDrawer;
import com.company.arc.GraphicsArcDrawer;
import com.company.line.DDALineDrawer;
import com.company.line.Line;
import com.company.line.LineDrawer;
import com.company.line.WuLineDrawer;
import com.company.pixel.BufferedImagePixelDrawer;
import com.company.pixel.PixelDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private Graphics g;
    private Line xAxis = new Line(-1, 0, 1, 0);
    private Line yAxis = new Line(0, -1, 0, 1);
    int[] x = {-1, 2, 5};
    int[] y = {-5, -7, 1};
    int n = 3;
    //private Arc arc = new Arc(1, 1, 5, 5, 0, 80);
    //private GraphicsArcDrawer arc = new GraphicsArcDrawer(g);

    ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
    ScreenConverter scForArc = new ScreenConverter(-2, 2, 4, 4, 800, 600, 0, 360);

    private ArrayList<Line> allLines = new ArrayList<>();
    private Line currentNewLine = null;
    private ArrayList<Arc> allArcs = new ArrayList<>();
    private Arc currentNewArc = null;

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        sc.setScreenWidth(getWidth());
        sc.setScreenHeight(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics gr = bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.dispose();

        PixelDrawer pd = new BufferedImagePixelDrawer(bi);

        LineDrawer ld = new WuLineDrawer(pd);
        drawLine(ld, xAxis);
        drawLine(ld, yAxis);

        for (Line l : allLines) {
            drawLine(ld, l);
        }

        if (currentNewLine != null) {
            drawLine(ld, currentNewLine);
        }


        //находим радиус окружности
        // double
        //drawRoundedPolygon(ld);
//        ArcDrawer ad = new BresenhamArcDrawer(pd);
//        drawArc(ad, arc);
//
//        for (Arc a : allArcs) {
//            drawArc(ad, a);
//        }
//
//        if (currentNewArc != null) {
//            drawArc(ad, currentNewArc);
//        }


        g.drawImage(bi, 0, 0, null);


    }

    private void drawRoundedPolygon(LineDrawer ld, ScreenPoint p1, ScreenPoint p2, ScreenPoint p3, double radius) {
//        for (int i = 1; i <= n; i++) {
//            Line point;
//            if (i == n) {
//                point = new Line(x[i - 1], y[i - 1], x[0], y[0]);
//            } else {
//                point = new Line(x[i - 1], y[i - 1], x[i], y[i]);
//            }
//
//            drawLine(ld, point);
//        }

        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();
        int x3 = p3.getX();
        int y3 = p3.getY();

        //вектор 1
        double dx1 = x2 - x1;
        double dy1 = y2 - y1;

        //вектор 2
        double dx2 = x2 - x3;
        double dy2 = y2 - y3;

        double angle = (Math.atan2(y2 - y1, x2 - x1) - Math.atan2(y2 - y3, x2 - x3)) / 2;

        // длина отрезка между угловой точкой и точками пересечения с окружностью радиуса

        double segment = radius / Math.atan(angle);

        double length1 = Math.sqrt(Math.pow(dx1, 2) + Math.pow(dy1, 2));
        double length2 = Math.sqrt(Math.pow(dx2, 2) + Math.pow(dy2, 2));
        double length = Math.min(length1, length2);

        if (segment > length) {
            segment = length;
            radius = length * Math.tan(angle);
        }

        ScreenPoint p1Cross = new ScreenPoint((int) (x2 - dx1 * segment / length1), (int) (y2 - dy1 * segment / length1));
        ScreenPoint p2Cross = new ScreenPoint((int) (x2 - dx2 * segment / length2), (int) (y2 - dy2 * segment / length2));

        double dx = x2 * 2 - p1Cross.getX() - p2Cross.getX();
        double dy = y2 * 2 - p1Cross.getY() - p2Cross.getY();

        double l = Math.sqrt(Math.pow(dx, 2) - Math.pow(dy, 2));
        double d = Math.sqrt(Math.pow(segment, 2) - Math.pow(segment, 2));

        ScreenPoint circlePoint = new ScreenPoint((int)())
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    private void drawArc(ArcDrawer ad, Arc arc) {
        ad.drawArc(scForArc.r2sForArc(arc.getP()));
    }

    private ScreenPoint lastPosition = null;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            lastPosition = new ScreenPoint(e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            currentNewLine = new Line(sc.s2r(new ScreenPoint(e.getX(), e.getY())), sc.s2r(new ScreenPoint(e.getX(), e.getY())));
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            lastPosition = null;
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            allLines.add(currentNewLine);
            currentNewLine = null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());

        if (lastPosition != null) {
            ScreenPoint deltaScreen = new ScreenPoint(currentPosition.getX() - lastPosition.getX(), currentPosition.getY() - lastPosition.getY());

            RealPoint deltaReal = sc.s2r(deltaScreen);
            RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));

            RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(), deltaReal.getY() - zeroReal.getY());

            sc.setCornerX(sc.getCornerX() - vector.getX());
            sc.setCornerY(sc.getCornerY() - vector.getY());
            lastPosition = currentPosition;
        }

        if (currentNewLine != null) {
            currentNewLine.setP2(sc.s2r(currentPosition));
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks < 0 ? 1.1 : 0.9;
        for (int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        sc.setRealWidth(sc.getRealWidth() * scale);
        sc.setRealHeight(sc.getRealHeight() * scale);
        repaint();
    }
}
