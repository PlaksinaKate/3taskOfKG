//package com.company.arc;
//
//import com.company.ScreenPoint;
//import com.company.pixel.PixelDrawer;
//
//import java.awt.*;
//
//public class BresenhamArcDrawer implements ArcDrawer {
//    private PixelDrawer pd;
//
//    public BresenhamArcDrawer(PixelDrawer pd) {
//        this.pd = pd;
//    }
//
//    @Override
//    public void drawArc(ScreenPoint p) {
//        double x = p.getX();
//        double y = p.getY();
//        int width = p.getWidth();
//        int height = p.getHeight();
//        int startAngle = p.getStartAngle();
//        int arcAngle = p.getArcAngle();
//
////
//        //drawpixel(x: 50 + Math.cos(i) * r, y: 100 + Math.sin(i) * r); // center point is (x = 50, y = 100)
////        for(int i = startAngle; i < arcAngle; i += 0.05)
////            pd.drawPixel(x + Math.cos(i) * width, y + Math.sin(i) * height, Color.BLUE);
//
////        int x2 = 0;
////        int y2 = 0;
////        int limit = 0;
////
////        if (startAngle <= 90) {
////            if (arcAngle > 90)
////                arcAngle = 90;
////
////            x2 = (int) Math.abs(width / 2 * Math.cos(arcAngle * Math.PI / 180));
////            y2 = (int) Math.abs(height / 2 * Math.sin(arcAngle * Math.PI / 180));
////            limit = (int) Math.abs(height / 2 * Math.sin(startAngle * Math.PI / 180));
////
////        } else if ((startAngle > 90 && arcAngle <= 180) || (startAngle <= 90 && arcAngle > 90) || (startAngle > 90 && arcAngle >= 180 && arcAngle < 270)) {
////            if (arcAngle > 180)
////                arcAngle = 180;
////            if (startAngle < 90)
////                startAngle = 90;
////
////            x2 = (int) Math.abs(width / 2 * Math.cos(startAngle * Math.PI / 180));
////            y2 = (int) Math.abs(height / 2 * Math.sin(startAngle * Math.PI / 180));
////            limit = (int) Math.abs(height / 2 * Math.sin(arcAngle * Math.PI / 180));
////
////        } else if ((startAngle > 180 && arcAngle <= 270) || (startAngle <= 180 && arcAngle > 180) || (startAngle > 180 && arcAngle >= 270)) {
////            if (arcAngle > 270)
////                arcAngle = 270;
////            if (startAngle < 180)
////                startAngle = 180;
////
////            x2 = (int) Math.abs(width / 2 * Math.cos(arcAngle * Math.PI / 180));
////            y2 = (int) Math.abs(height / 2 * Math.sin(arcAngle * Math.PI / 180));
////            limit = (int) Math.abs(height / 2 * Math.sin(startAngle * Math.PI / 180));
////
////        } else if ((startAngle > 270 && arcAngle <= 360) || (startAngle <= 270 && arcAngle > 270) || (startAngle > 270 && arcAngle >= 360)) {
////            if (arcAngle > 360)
////                arcAngle = 360;
////            if (startAngle < 270)
////                startAngle = 270;
////
////            x2 = (int) Math.abs(width / 2 * Math.cos(startAngle * Math.PI / 180));
////            y2 = (int) Math.abs(height / 2 * Math.sin(startAngle * Math.PI / 180));
////            limit = (int) Math.abs(height / 2 * Math.sin(arcAngle * Math.PI / 180));
////
////        }
////
////        int dx;
////        int dy;
////        int d0 = 2 - height;
////        if (y2 >= limit) {
////            if (startAngle <= 90 || (startAngle > 270 && startAngle <= 360))
////                pd.drawPixel((int)(x + x2), (int)(-y + y2), Color.BLUE);
////            else
////                pd.drawPixel((int)(-x + x2), (int)(y + y2), Color.BLUE);
////            //repaint();
////            if (d0 < 0) {
////                dy = 2 * d0 - 2 * y2 - 1;
////                if (dy < 0) {
////                    x2++;
////                    d0 += 2 * x2 + 1;
////                } else if (dy > 0) {
////                    x2++;
////                    y2--;
////                    d0 += 2 * x2 - 2 * y2 + 2;
////                }
////            } else if (d0 > 0) {
////                dx = 2 * d0 - 2 * x2 - 1;
////                if (dx <= 0) {
////                    x2++;
////                    y2--;
////                    d0 += 2 * x2 - 2 * y2 + 2;
////                } else if (dx > 0) {
////                    y2--;
////                    d0 -= 2 * y2 + 1;
////                }
////            } else if (d0 == 0) {
////                x2++;
////                y2--;
////                d0 += 2 * x2 - 2 * y2 + 2;
////            }
////
////        }
//
//        if (startAngle < 0)
//            startAngle = 360 + startAngle;
//        if (arcAngle < 0)
//            arcAngle = 360 + arcAngle;
//        if (startAngle > 360)
//            startAngle -= 360;
//        if (arcAngle > 360)
//            arcAngle -= 360;
//
//        if (startAngle + arcAngle > 360) {
//           // ScreenPoint point = new ScreenPoint(x, y, width, height, 0, startAngle + arcAngle - 360);
//            drawArc(point);
//            arcAngle = 360 - startAngle;
//        }
//
//        int x2 = 0;
//        int y2 = height;
//        int gap = 0;
//        int delta = (2 - 2 * height);
//        int angle;
//        while (y >= 0) {
//
//            if ((int) x + x2 < width && (int) y - y2 < height && (int) x + x2 >= 0 && (int) y - y2 >= 0) {
//                //if (pd.drawPixel(x+x2,y-y2,Color.BLUE) > 0) {
//                angle = getAngle((int) x, (int) y, (int) x + x2, (int) y - y2);
//                if ((angle >= startAngle) && (angle <= startAngle + arcAngle)) {
//                    if (0 <= angle && angle <= 90) {
//                        pd.drawPixel(x + x2, y - y2, Color.BLUE);
//                    }
//                }
//                //}
//            }
//
//            if ((int) x + x < width && (int) y + y2 < height && (int) x + x2 >= 0 && (int) y + y2 >= 0) {
//                // if (_texture.GetPixel((int) point.x + x, (int) point.y + y).a > 0) {
//                angle = getAngle((int) x, (int) y, (int) x + x2, (int) y + y2);
//                if ((angle >= startAngle) && (angle <= startAngle + arcAngle)) {
//                    if (90 < angle && angle <= 180) {
//                        pd.drawPixel(x + x2, y + y2, Color.BLUE);
//                    }
//                }
//                //}
//            }
//
//            if ((int) x - x2 < width && (int) y + y2 < height && (int) x - x >= 0 && (int) y + y >= 0) {
//                // if (_texture.GetPixel((int) point.x - x, (int) point.y + y).a > 0) {
//                angle = getAngle((int) x, (int) y, (int) x - x2, (int) y + y2);
//                if ((angle >= startAngle) && (angle <= startAngle + arcAngle)) {
//                    if (180 < angle && angle <= 270) {
//                        pd.drawPixel((int) x - x2, (int) y + y2, Color.BLUE);
//                    }
//                }
//                //}
//            }
//
//            if ((int) x - x2 < width && (int) y - y2 < height && (int) x - x2 >= 0 && (int) y - y2 >= 0) {
//                //if (_texture.GetPixel((int) point.x - x, (int) point.y - y).a > 0) {
//                angle = getAngle((int) x, (int) y, (int) x - x2, (int) y - y2);
//                if ((angle >= startAngle) && (angle <= startAngle + arcAngle)) {
//                    if (270 < angle && angle <= 360) {
//                        pd.drawPixel((int) x - x2, (int) y - y2, Color.BLUE);
//                    }
//                }
//                // }
//            }
//
//            gap = (int) (2 * (delta + y2) - 1);
//            if (delta < 0 && gap <= 0) {
//                x2++;
//                delta += 2 * x2 + 1;
//                continue;
//            }
//            if (delta > 0 && gap > 0) {
//                y2--;
//                delta -= 2 * y2 + 1;
//                continue;
//            }
//            x2++;
//            delta += 2 * (x2 - y2);
//            y2--;
//        }
//    }
//
//    int getAngle(int centerX, int centerY, int pointX, int pointY) {
//        int x = pointX - centerX;
//        int y = pointY - centerY;
//
//        if (x == 0)
//            return (y > 0) ? 180 : 0;
//
//        int a = (int) (Math.atan2(y, x) * 180 / Math.PI);
//        a += 90;
//
//        if ((x < 0) && (y < 0))
//            a += 360;
//
//        return a;
//    }
//
//}
//
//
//
