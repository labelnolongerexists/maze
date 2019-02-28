package com.qyer.dora.maze;

import static com.qyer.dora.maze.Constants.ACCESSIBLE;

import com.qyer.dora.shape.RCPoint;

/**
 * User: Z J Wu Date: 2019-02-25 Time: 10:23 Package: com.qyer.dora.maze.generator
 */
public class TileBasedGrid {

  public static final RCPoint DEFAULT_ENTRANCE = new RCPoint(1, 0);

  private final int rows;
  private final int columns;

  private final byte[][] store;

  public TileBasedGrid(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    this.store = new byte[rows][columns];
  }

  public TileBasedGrid(int rowsColumns) {
    this(rowsColumns, rowsColumns);
  }

  public boolean isBorder(int r, int c) {
    return r <= firstRow() || r >= lastRow() || c <= firstColumn() || c >= lastColumn();
  }

  public void border(byte border) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (i == 0 || i == rows - 1) {
          store[i][j] = border;
        } else if (j == 0 || j == columns - 1) {
          store[i][j] = border;
        }
      }
    }
  }

  public void fill(byte type, int rowFrom, int rowTo, int columnFrom, int columnTo) {
    for (int i = rowFrom; i < rowTo; i++) {
      for (int j = columnFrom; j < columnTo; j++) {
        store[i][j] = type;
      }
    }
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public byte[][] getStore() {
    return store;
  }

  public int firstRow() {
    return 0;
  }

  public int firstColumn() {
    return 0;
  }

  public int lastRow() {
    return rows - 1;
  }

  public int lastColumn() {
    return columns - 1;
  }

  public boolean isBlocked(RCPoint point) {
    return isBlocked(point.getRow(), point.getColumn());
  }

  public boolean isBlocked(int r, int c) {
    return isBlocked(getContent(r, c));
  }

  public boolean isBlocked(byte b) {
    return b != ACCESSIBLE;
  }

  public boolean isAccessible(RCPoint point) {
    return isAccessible(point.getRow(), point.getColumn());
  }

  public boolean isAccessible(int r, int c) {
    return isAccessible(getContent(r, c));
  }

  public boolean isAccessible(byte b) {
    return b == ACCESSIBLE;
  }

  public byte getContent(RCPoint point) {
    return getContent(point.getRow(), point.getColumn());
  }

  public byte getContent(int r, int c) {
    return store[r][c];
  }

  public void defaultEntrance() {
    store[DEFAULT_ENTRANCE.getRow()][DEFAULT_ENTRANCE.getColumn()] = ACCESSIBLE;
  }

  public void defaultExit() {
    store[rows - 2][columns - 1] = ACCESSIBLE;
  }

  public void setEntrance(RCPoint entrance) {
    store[entrance.getRow()][entrance.getColumn()] = ACCESSIBLE;
  }

  public void setExit(RCPoint exit) {
    store[exit.getRow()][exit.getColumn()] = ACCESSIBLE;
  }

  public void updateVal(RCPoint p, byte v) {
    store[p.getRow()][p.getColumn()] = v;
  }

  public void updateVal(int r, int c, byte v) {
    store[r][c] = v;
  }

  public void updateRowSegment(int r, int cFrom, int cTo, byte v) {
    for (int i = cFrom; i < cTo; i++) {
      updateVal(r, i, v);
    }
  }

  public RCPoint top(int r, int c) {
    return top(r, c, 1);
  }

  public RCPoint top(int r, int c, int step) {
    return new RCPoint(r - step, c);
  }

  public RCPoint topRight(int r, int c) {
    return topRight(r, c, 1);
  }

  public RCPoint topRight(int r, int c, int step) {
    return new RCPoint(r - step, c + step);
  }

  public RCPoint right(int r, int c) {
    return right(r, c, 1);
  }

  public RCPoint right(int r, int c, int step) {
    return new RCPoint(r, c + step);
  }

  public RCPoint bottomRight(int r, int c) {
    return bottomRight(r, c, 1);
  }

  public RCPoint bottomRight(int r, int c, int step) {
    return new RCPoint(r + step, c + step);
  }

  public RCPoint bottom(int r, int c) {
    return bottom(r, c, 1);
  }

  public RCPoint bottom(int r, int c, int step) {
    return new RCPoint(r + step, c);
  }

  public RCPoint bottomLeft(int r, int c) {
    return bottomLeft(r, c, 1);
  }

  public RCPoint bottomLeft(int r, int c, int step) {
    return new RCPoint(r + step, c - step);
  }

  public RCPoint left(int r, int c) {
    return left(r, c, 1);
  }

  public RCPoint left(int r, int c, int step) {
    return new RCPoint(r, c - step);
  }

  public RCPoint topLeft(int r, int c) {
    return topLeft(r, c, 1);
  }

  public RCPoint topLeft(int r, int c, int step) {
    return new RCPoint(r - step, c - step);
  }

  public void updateSurrounded(int r, int c, byte b) {
    updateVal(top(r, c), b);
    updateVal(topRight(r, c), b);
    updateVal(right(r, c), b);
    updateVal(bottomRight(r, c), b);
    updateVal(bottom(r, c), b);
    updateVal(bottomLeft(r, c), b);
    updateVal(left(r, c), b);
    updateVal(topLeft(r, c), b);
  }

  public int surroundedAccessibleCnt(int r, int c) {
    return 8 - surroundedBlockCnt(r, c);
  }

  public int surroundedBlockCnt(int r, int c) {
    int cnt = 0;
    if (isBlocked(top(r, c))) {
      ++cnt;
    }
    if (isBlocked(topRight(r, c))) {
      ++cnt;
    }
    if (isBlocked(right(r, c))) {
      ++cnt;
    }
    if (isBlocked(bottomRight(r, c))) {
      ++cnt;
    }
    if (isBlocked(bottom(r, c))) {
      ++cnt;
    }
    if (isBlocked(bottomLeft(r, c))) {
      ++cnt;
    }
    if (isBlocked(left(r, c))) {
      ++cnt;
    }
    if (isBlocked(topLeft(r, c))) {
      ++cnt;
    }
    return cnt;
  }

  public String toPlainStr() {
    StringBuilder sb = new StringBuilder();
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < columns; c++) {
        if (isAccessible(r, c)) {
          sb.append('0');
        } else {
          sb.append('1');
        }
      }
    }
    return sb.toString();
  }

}
