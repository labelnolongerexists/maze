package com.hhrb.maze;

import java.util.Random;

/**
 * User: Z J Wu Date: 2019-02-20 Time: 18:26 Package: com.hhrb.maze
 */
public class RecrutiveSplitMaze {

  private Random random = new Random();

  public static final char ROAD = '.';
  public static final char WALL = '#';

  private int rows;
  private int columns;

  private char[][] store;

  public RecrutiveSplitMaze(int rows, int columns) {
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

  private void fillColumn(int column, char c) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        store[i][column] = c;
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

  private boolean canSplit(int a, int b) {
    return b - a > 2;
  }

  public void createMaze(int leftTop, int rightTop, int leftBottom, int rightBottom) {
    if (!canSplit(leftTop, rightTop) || !canSplit(leftBottom, rightBottom)) {
      return;
    }
    int randWallX = random.nextInt(rightTop - 1 - (leftTop)) + leftTop;
    fillColumn(randWallX, WALL);
  }

  public static void main(String[] args) {
    RecrutiveSplitMaze maze = new RecrutiveSplitMaze(5, 5);
    maze.createMaze(0, 4, 0, 4);
    maze.dump();
  }
}
