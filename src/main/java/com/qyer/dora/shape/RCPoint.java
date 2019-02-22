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

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public static RCPoint topNeighbor(RCPoint p, int topBorder) {
    if (p.getRow() - 1 < topBorder) {
      return null;
    }
    return new RCPoint(p.getRow() - 1, p.getColumn());
  }

  public static RCPoint underNeighbor(RCPoint p, int underBorder) {
    if (p.getRow() + 1 > underBorder) {
      return null;
    }
    return new RCPoint(p.getRow() + 1, p.getColumn());
  }

  public static RCPoint leftNeighbor(RCPoint p, int leftBorder) {
    if (p.getColumn() - 1 < leftBorder) {
      return null;
    }
    return new RCPoint(p.getRow(), p.getColumn() - 1);
  }

  public static RCPoint rightNeighbor(RCPoint p, int rightBorder) {
    if (p.getColumn() + 1 > rightBorder) {
      return null;
    }
    return new RCPoint(p.getRow(), p.getColumn() + 1);
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
