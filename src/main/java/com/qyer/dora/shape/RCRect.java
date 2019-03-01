package com.qyer.dora.shape;

import static com.qyer.dora.maze.Utils.between;

/**
 * User: Z J Wu Date: 2019-03-01 Time: 11:35 Package: com.qyer.dora.shape
 */
public class RCRect {

  private RCPoint point;
  private int pointRow;
  private int pointCol;

  private int rows;
  private int columns;

  private int width;
  private int height;

  public RCRect(RCPoint leftTopPoint, int width, int height) {
    this.point = leftTopPoint;
    this.pointRow = leftTopPoint.getRow();
    this.pointCol = leftTopPoint.getColumn();
    this.width = width;
    this.height = height;
    this.columns = width;
    this.rows = height;
  }

  public RCPoint getLeftTop() {
    return this.point;
  }

  public RCPoint getRightTop() {
    return RCPoint.right(pointRow, pointCol, width);
  }

  public RCPoint getLeftBottom() {
    return RCPoint.bottom(pointRow, pointCol, height);
  }

  public RCPoint getRightBottom() {
    return RCPoint.of(pointRow + rows, pointCol + columns);
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public boolean in(RCPoint p) {
    int pr = p.getRow(), pc = p.getColumn();
    return between(pr, pointRow, pointRow + rows) && between(pc, pointCol, pointCol + columns);
  }

  public boolean onBorder(RCPoint p) {
    return false;
  }
}
