package com.qyer.dora.shape;

import java.util.Objects;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:29 Package: com.com.qyer.dora.shape
 */
public class RCPoint {

  private final int row;
  private final int column;

  public RCPoint(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public static final RCPoint of(int r, int c) {
    return new RCPoint(r, c);
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public static RCPoint top(int r, int c) {
    return top(r, c, 1);
  }

  public static RCPoint top(int r, int c, int step) {
    return new RCPoint(r - step, c);
  }

  public static RCPoint topRight(int r, int c) {
    return topRight(r, c, 1);
  }

  public static RCPoint topRight(int r, int c, int step) {
    return new RCPoint(r - step, c + step);
  }

  public static RCPoint right(int r, int c) {
    return right(r, c, 1);
  }

  public static RCPoint right(int r, int c, int step) {
    return new RCPoint(r, c + step);
  }

  public static RCPoint bottomRight(int r, int c) {
    return bottomRight(r, c, 1);
  }

  public static RCPoint bottomRight(int r, int c, int step) {
    return new RCPoint(r + step, c + step);
  }

  public static RCPoint bottom(int r, int c) {
    return bottom(r, c, 1);
  }

  public static RCPoint bottom(int r, int c, int step) {
    return new RCPoint(r + step, c);
  }

  public static RCPoint bottomLeft(int r, int c) {
    return bottomLeft(r, c, 1);
  }

  public static RCPoint bottomLeft(int r, int c, int step) {
    return new RCPoint(r + step, c - step);
  }

  public static RCPoint left(int r, int c) {
    return left(r, c, 1);
  }

  public static RCPoint left(int r, int c, int step) {
    return new RCPoint(r, c - step);
  }

  public static RCPoint topLeft(int r, int c) {
    return topLeft(r, c, 1);
  }

  public static RCPoint topLeft(int r, int c, int step) {
    return new RCPoint(r - step, c - step);
  }

  @Override
  public String toString() {
    return "RCPoint(R=" + row + ",C=" + column + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof RCPoint))
      return false;
    RCPoint rcPoint = (RCPoint) o;
    return row == rcPoint.row && column == rcPoint.column;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, column);
  }
}
