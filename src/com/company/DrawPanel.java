package com.company;

import com.company.arc.Arc;
import com.company.arc.ArcDrawer;
import com.company.arc.GraphicsArcDrawer;
import com.company.figure.RoundedPolygon;
import com.company.line.*;
import com.company.pixel.BufferedImagePixelDrawer;
import com.company.pixel.PixelDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.MAX_VALUE;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener, OtherFrame.RoundedPolygonReadyListener {
    ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);

    private Line xAxis = new Line(sc.getCornerX() - sc.getScreenWidth(), 0, sc.getScreenWidth(), 0);
    private Line yAxis = new Line(0, sc.getCornerY() - sc.getScreenHeight(), 0, sc.getScreenHeight());

    private ArrayList<RealPoint> coordinates = new ArrayList<>();

    private ArrayList<Line> allLines = new ArrayList<>();
    private Line currentNewLine = null;

    private ArrayList<RoundedPolygon> allRP = new ArrayList<>();
    private RoundedPolygon currentNewRoundedPolygon = null;
    private Map<Integer, ArrayList<RealPoint>> tops = new HashMap();
    private int count = 0;

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

        LineDrawer ld = new BresenhamLineDrawer(pd);
        drawLine(ld, xAxis);
        drawLine(ld, yAxis);

        drawSegments(gr);

        for (Line l : allLines) {
            drawLine(ld, l);
        }
        if (currentNewLine != null) {
            drawLine(ld, currentNewLine);
        }


        g2d.setColor(Color.BLACK);
        ArcDrawer ad = new GraphicsArcDrawer(gr);

        drawCirclePoint(gr);

        for (RoundedPolygon roundedP : allRP) {
            roundedP.drawRoundedPolygon(sc, ld, ad);
        }
        if (currentNewRoundedPolygon != null) {
            currentNewRoundedPolygon.drawRoundedPolygon(sc, ld, ad);
        }


        gr.dispose();
        g.drawImage(bi, 0, 0, null);
    }

    private void drawCirclePoint(Graphics g2d) {
        g2d.setColor(Color.BLACK);
        int temp = 0;
        for (Map.Entry<Integer, ArrayList<RealPoint>> circle : tops.entrySet()) {
            while (temp != circle.getValue().size()) {
                g2d.drawOval((int) (sc.r2s(circle.getValue().get(temp)).getX() - 0.05 / 2), (int) (sc.r2s(circle.getValue().get(temp)).getY() + 0.05 / 2), (int) (0.05 * sc.getScreenWidth() / sc.getRealWidth()), (int) (0.05 * sc.getScreenWidth() / sc.getRealWidth()));
                temp++;
            }
            temp = 0;
        }
    }


    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    private void drawSegments(Graphics g) {
        g.setColor(Color.BLACK);
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
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (coordinates.isEmpty()) {
                coordinates.add(sc.s2r(new ScreenPoint(e.getX(), e.getY())));
            } else {
                if ((sc.s2r(new ScreenPoint(e.getX(), e.getY())).getX() >= (coordinates.get(0).getX() - 0.2) && sc.s2r(new ScreenPoint(e.getX(), e.getY())).getX() <= (coordinates.get(0).getX() + 0.2)) && (sc.s2r(new ScreenPoint(e.getX(), e.getY())).getY() >= (coordinates.get(0).getY() - 0.2) && sc.s2r(new ScreenPoint(e.getX(), e.getY())).getY() <= (coordinates.get(0).getY() + 0.2))) {
                    currentNewLine = new Line(coordinates.get(coordinates.size() - 1), coordinates.get(0));
                } else {
                    RealPoint prev = coordinates.get(coordinates.size() - 1);
                    currentNewLine = new Line(prev, sc.s2r(new ScreenPoint(e.getX(), e.getY())));
                    coordinates.add(sc.s2r(new ScreenPoint(e.getX(), e.getY())));
                }
                currentNewRoundedPolygon = new RoundedPolygon(coordinates, 0);
                //allRP.add(currentNewRoundedPolygon);
            }
        }
//        boolean check;
//        int maxY = 0;
//        int minY = 0;
//        for (int i = 1; i < coordinates.size(); i++) {
//            if (coordinates.get(i).getY() > coordinates.get(maxY).getY())
//                maxY = i;
//
//
//            if (coordinates.get(i).getY() < coordinates.get(minY).getY())
//                minY = i;
//        }
//
//        for (int i = sc.r2s(coordinates.get(maxY)).getY(); i <= sc.r2s(coordinates.get(minY)).getY(); i++) {
//
//        }
        ScreenPoint sp = new ScreenPoint(e.getX(), e.getY());
        for (RoundedPolygon rp : allRP) {
            if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 2 && rp.checkInside(new RealPoint(sc.s2r(sp).getX(), sc.s2r(sp).getY()))) {
                EditAction(rp);
            }
        }
//        if (e.getButton() == MouseEvent.BUTTON3) {
//            EditAction();
//        }
        repaint();
    }

    private int j, temp1;
    private boolean check;

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            lastPosition = new ScreenPoint(e.getX(), e.getY());

        ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());
        check = false;
        int r = (int) (0.05 * sc.getScreenWidth() / sc.getRealWidth());
        int temp = 0;
        for (int i = 1; i <= tops.size(); i++) {
            while (temp != tops.get(i).size()) {
                RealPoint topsPoint = new RealPoint(tops.get(i).get(temp).getX(), tops.get(i).get(temp).getY());
                ScreenPoint topsScreenPoint = new ScreenPoint(sc.r2s(topsPoint).getX(), sc.r2s(topsPoint).getY());
                if (e.getButton() == MouseEvent.BUTTON1 && currentPosition.getX() >= (topsScreenPoint.getX() - r) && currentPosition.getX() <= (topsScreenPoint.getX() + r) && currentPosition.getY() <= (topsScreenPoint.getY() + r) && currentPosition.getY() >= (topsScreenPoint.getY() - r)) {
                    //                        //lastPosition = sc.r2s(tops.getValue().get(temp));
//                        ScreenPoint deltaScreen2 = new ScreenPoint(currentPosition.getX() - lastPosition.getX(), currentPosition.getY() - lastPosition.getY());
//
//                        RealPoint deltaReal2 = sc.s2r(deltaScreen2);
//                        RealPoint zeroReal2 = sc.s2r(new ScreenPoint(0, 0));
//
//                        RealPoint vector2 = new RealPoint(deltaReal2.getX() - zeroReal2.getX(), deltaReal2.getY() - zeroReal2.getY());
//                        sc.setCornerX(sc.getCornerX() - vector2.getX());
//                        sc.setCornerY(sc.getCornerY() - vector2.getY());
// в экранных координатах
                    check = true;
                    j = i;
                    temp1 = temp;

                }
                temp++;
            }
            temp = 0;
        }

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            lastPosition = null;
            if (currentNewRoundedPolygon != null) {
                allRP.add(currentNewRoundedPolygon);
                currentNewRoundedPolygon = null;
            }
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            if (check) {
                ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());
                tops.get(j).get(temp1).setX(sc.s2r(currentPosition).getX());
                tops.get(j).get(temp1).setY(sc.s2r(currentPosition).getY());

            }
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

    @Override
    public void created(RoundedPolygon rp) {
        allRP.add(rp);
    }

    void EditAction(RoundedPolygon rp) {
        OtherFrame dialog = new OtherFrame();
        dialog.setReadyListener(this);
        dialog.setTitle("Радиус");
        dialog.setLocation(500, 150);
        JLabel r = new JLabel("Введите радиус");
        r.setBounds(10, 30, 100, 40);
        dialog.add(r);

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0.1, 0.0, MAX_VALUE, 0.1));
        spinner.setBounds(150, 30, 100, 40);
        dialog.add(spinner);

        JButton b = new JButton();
        b.setText("Отрисовать скругленный многоугольник");
        b.setBounds(0, 95, 300, 50);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                double r = (double) spinner.getValue();
                if (coordinates.size() == 0) {
                    boolean result = false;
                    for (int i = 1; i <= tops.size(); i++) {
                        if (rp.getTops().size() == tops.get(i).size()) {
                            for (int j = 0; j < tops.get(i).size(); j++) {
                                if (rp.getTops().get(j) == tops.get(i).get(j)) {
                                    result = true;
                                }
                            }
                            if (result) {
                                allRP.remove(allRP.get(i - 1));
                                currentNewRoundedPolygon = new RoundedPolygon(tops.get(i), r);
                                allRP.add(i - 1, currentNewRoundedPolygon);
                                break;
                            }
                        }
                    }
//                    currentNewRoundedPolygon = new RoundedPolygon(tops.get(tops.), r);
//                    allRP.remove();
//                    allRP.add(currentNewRoundedPolygon);
////                    for (RoundedPolygon rp:allRP) {
////                        for (int i = 0; i < tops.size(); i++) {
////                            if(rp.checkInside(new RealPoint(rp.getTops().get(0).getX(),rp.getTops().get(0).getY()))){
////
////                            }
////
////                        }
////                    }

                } else {
                    count++;
                    allRP.remove(allRP.size() - 1);
                    tops.put(count, new ArrayList<>(coordinates));
                    coordinates.clear();
                    currentNewRoundedPolygon = new RoundedPolygon(tops.get(count), r);
                    allRP.add(currentNewRoundedPolygon);
                }
                currentNewRoundedPolygon = null;
                currentNewLine = null;
                dialog.dispose();
                repaint();
            }
        });
        dialog.add(b);
        dialog.setLayout(null);
        dialog.setSize(300, 200);
        dialog.setVisible(true);
//        if (currentNewRoundedPolygon.getR() == 0)
//            allRP.remove(currentNewRoundedPolygon);
    }


}
