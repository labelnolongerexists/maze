package com.hhrb.maze;

/**
 * User: Z J Wu Date: 2019-02-18 Time: 13:05 Package: com.hhrb.maze
 */
public class Point {

  private double  x;
  private double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  @Override
  public String toString() {
    return new StringBuilder().append('(').append(x).append(',').append(y).append(')').toString();
  }
}
