package com.hhrb.maze;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Z J Wu Date: 2019-02-18 Time: 13:06 Package: com.hhrb.maze
 */
public class Rect {

  private Point leftBottom;
  private int width;
  private int height;

  public Rect(double x, double y, int width, int height) {
    this.leftBottom = new Point(x, y);
    this.width = width;
    this.height = height;
  }

  public Point center() {
    double lbx = leftBottom.getX(), lby = leftBottom.getY();
    return new Point((lbx + this.width) / 2.0, (lby + this.height) / 2);
  }

  public List<Point> outline() {
    List<Point> outline = new ArrayList<>(4);
    double x = leftBottom.getX(), y = leftBottom.getY();
    // left-bottom
    outline.add(leftBottom);
    // right-bottom
    outline.add(new Point(x + width, y));
    // right-top
    outline.add(new Point(x + width, y + height));
    // left-top
    outline.add(new Point(x, y + height));
    return outline;
  }

  public static final boolean hasOverlap(Rect r1, Rect r2) {
    double a = (r1.width + r2.width) / 2, b = (r1.height + r2.height) / 2;
    Point c1 = r1.center(), c2 = r2.center();
    return ((Math.abs(c1.getX() - c2.getX())  ) < a) && ((Math.abs(c1.getY() - c2.getY())  
    ) < b
    );
  }

  public Point getLeftBottom() {
    return leftBottom;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
