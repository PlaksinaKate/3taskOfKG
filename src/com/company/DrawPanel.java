package com.company;

import com.company.arc.Arc;
import com.company.arc.ArcDrawer;
import com.company.arc.GraphicsArcDrawer;
import com.company.figure.RoundedPolygon;
import com.company.line.DDALineDrawer;
import com.company.line.Line;
import com.company.line.LineDrawer;
import com.company.line.WuLineDrawer;
import com.company.pixel.BufferedImagePixelDrawer;
import com.company.pixel.PixelDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);

    private Line xAxis = new Line(sc.getCornerX() - sc.getScreenWidth(), 0, sc.getScreenWidth(), 0);
    private Line yAxis = new Line(0, sc.getCornerY() - sc.getScreenHeight(), 0, sc.getScreenHeight());

    private ArrayList<RealPoint> coordinates = new ArrayList<>();

    private ArrayList<Line> allLines = new ArrayList<>();
    private Line currentNewLine = null;

    private ArrayList<RoundedPolygon> allRP = new ArrayList<>();
    private RoundedPolygon currentNewRoundedPolygon = null;

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        sc.setScreenWidth(getWidth());
        sc.setScreenHeight(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics gr = bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());

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

        drawSegments(gr);


//        g2d.setColor(Color.BLUE);
//        ArcDrawer ad = new GraphicsArcDrawer(gr);
//        RoundedPolygon rp = new RoundedPolygon();
//        //rp.drawRoundedPolygon(sc, ld, ad, coordinates);
//        rp.drawRoundedPolygon(sc, ld, ad);
        gr.dispose();

//        for (RoundedPolygon roundedP : allRP) {
//            rp.drawRoundedPolygon(sc, ld, ad, coordinates);
//        }
//        if (currentNewRoundedPolygon != null) {
//            rp.drawRoundedPolygon(sc, ld, ad);
//        }


        g.drawImage(bi, 0, 0, null);
    }


    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    private void drawSegments(Graphics g) {
        g.setColor(Color.BLUE);
        double step = sc.getRealWidth() / 4;
        for (double x = 0; x <= sc.getRealWidth() + Math.abs(sc.getScreenWidth()); x += step) {
            ScreenPoint point = sc.r2s(new RealPoint(x, 0));
            ScreenPoint oppositePoint = sc.r2s(new RealPoint(-x, 0));
            if (step >= 1) {
                g.drawString(String.valueOf((int) x), point.getX(), point.getY());
                g.drawString(String.valueOf((int) -x), oppositePoint.getX(), oppositePoint.getY());
            } else {
                String pattern = "#.#";
                DecimalFormat f = new DecimalFormat(pattern);
                String value = f.format(x).equals("0") ? "0" : f.format(x);
                g.drawString(value, point.getX(), point.getY());
                g.drawString(value, oppositePoint.getX(), oppositePoint.getY());
            }
        }

        step = sc.getRealHeight() / 4;
        for (double y = 0; y <= sc.getRealHeight() + Math.abs(sc.getScreenHeight()); y += step) {
            ScreenPoint point = sc.r2s(new RealPoint(0, y));
            ScreenPoint oppositePoint = sc.r2s(new RealPoint(0, -y));
            if (step >= 1) {
                g.drawString(String.valueOf((int) y), point.getX(), point.getY());
                g.drawString(String.valueOf((int) -y), oppositePoint.getX(), oppositePoint.getY());
            } else {
                String pattern = "#.#";
                DecimalFormat f = new DecimalFormat(pattern);
                String value = f.format(y).equals("0") ? "0" : f.format(y);
                g.drawString(value, point.getX(), point.getY());
                g.drawString(value, oppositePoint.getX(), oppositePoint.getY());
            }
        }
    }


    private ScreenPoint lastPosition = null;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (coordinates.isEmpty()) {
            coordinates.add(sc.s2r(new ScreenPoint(e.getX(), e.getY())));
        } else {
            RealPoint prev = coordinates.get(coordinates.size() - 1);
            currentNewLine = new Line(prev, sc.s2r(new ScreenPoint(e.getX(), e.getY())));
            coordinates.add(sc.s2r(new ScreenPoint(e.getX(), e.getY())));
        }
//        RealPoint last = coordinates.get(coordinates.size() - 1);
//        RealPoint first = coordinates.get(1);
//        currentNewLine = new Line(last, first);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            lastPosition = new ScreenPoint(e.getX(), e.getY());
//        } else if (e.getButton() == MouseEvent.BUTTON1) {
//            currentNewLine = new Line(sc.s2r(new ScreenPoint(e.getX(), e.getY())), sc.s2r(new ScreenPoint(e.getX(), e.getY())));
//        }
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
