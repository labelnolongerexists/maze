package com.hhrb.maze;

import java.util.Random;

/**
 * User: Z J Wu Date: 2019-02-20 Time: 18:26 Package: com.hhrb.maze
 */
public class RecursiveSplitMaze {

  private final Random R = new Random();

  public static final char ROAD = ' ';
  public static final char WALL = "@".charAt(0);

  private int rows;
  private int columns;

  private char[][] store;

  public RecursiveSplitMaze(int rowsColumns) {
    this(rowsColumns, rowsColumns);
  }

  public RecursiveSplitMaze(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    this.store = new char[rows][columns];
    border();
  }

  private void border() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (i == 0 || i == rows - 1 || j == 0 || j == columns - 1) {
          store[i][j] = WALL;
          continue;
        }
        store[i][j] = ROAD;
      }
    }
  }

  public void dump() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        System.out.print(store[i][j]);
      }
      System.out.println();
    }
  }

  public void createMaze() {
    createMaze(0, columns - 1, 0, rows - 1);
  }

  private int random(int fromClosed, int toClosed) {
    return R.nextInt(toClosed + 1 - fromClosed) + fromClosed;
  }

  private void fillColumn(int rowFrom, int rowTo, int column, char c) {
    for (int i = rowFrom; i <= rowTo; i++) {
      for (int j = 0; j < columns; j++) {
        store[i][column] = c;
      }
    }
  }

  private void fillRow(int columnFrom, int columnTo, int row, char c) {
    char[] rowStore = store[row];
    for (int i = columnFrom; i <= columnTo; i++) {
      rowStore[i] = c;
    }
  }

  private boolean canSplit(int a, int b) {
    return b - a > 4;
  }

  public void createMaze(int x, int xTo, int y, int yTo) {
    if (!canSplit(x, xTo) || !canSplit(y, yTo)) {
      return;
    }
    // Split area into 4 rooms randomly
    // Build vertical wall
    int vWallFrom = x + 2, vWallTo = xTo - 2, centerX = random(vWallFrom, vWallTo);
    // Build horizontally wall
    int hWallFrom = y + 2, hWallTo = yTo - 2, centerY = random(hWallFrom, hWallTo);
    //    System.out.println("x=" + x + ", xTo=" + xTo + ", vWall=" + vWall + ", hWall=" + hWall);

    fillRow(x, xTo, centerY, WALL);
    fillColumn(y, yTo, centerX, WALL);

    // LeftTopArea
    createMaze(x, centerX, y, centerY);
    // RightTopArea
    createMaze(centerX, xTo, y, centerY);
    // LeftBottomArea
    createMaze(x, centerX, centerY, yTo);
    // RightBottomArea
    createMaze(centerX, xTo, centerY, yTo);
  }

  public static void main(String[] args) {
    RecursiveSplitMaze maze = new RecursiveSplitMaze(80);
    maze.createMaze();
    maze.dump();
  }
}
