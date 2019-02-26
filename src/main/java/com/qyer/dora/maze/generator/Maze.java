package com.qyer.dora.maze.generator;

import static com.qyer.dora.maze.Constants.ACCESSIBLE;

import com.qyer.dora.shape.RCPoint;

/**
 * User: Z J Wu Date: 2019-02-25 Time: 10:23 Package: com.qyer.dora.maze.generator
 */
public class Maze {

  public static final RCPoint DEFAULT_ENTRANCE = new RCPoint(1, 0);

  private final int rows;
  private final int columns;

  private final byte[][] store;

  public Maze(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    this.store = new byte[rows][columns];
  }

  public Maze(int rowsColumns) {
    this(rowsColumns, rowsColumns);
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

  protected int firstRow() {
    return 0;
  }

  protected int firstColumn() {
    return 0;
  }

  protected int lastRow() {
    return rows - 1;
  }

  protected int lastColumn() {
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
}
