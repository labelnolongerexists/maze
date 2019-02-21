package com.hhrb.maze;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * User: Z J Wu Date: 2019-02-20 Time: 18:26 Package: com.hhrb.maze
 */
public class RecursiveSplitMaze {

  private final Random R = new Random();

  public static final char ROAD = ' ';
  public static final char H_WALL = "@".charAt(0);
  public static final char V_WALL = "@".charAt(0);

  private int rows;
  private int columns;

  private char[][] store;

  private final int WALL_LEFT = 0;
  private final int WALL_TOP = 1;
  private final int WALL_RIGHT = 2;
  private final int WALL_BOTTOM = 3;
  private List<Integer> WALLS = Lists.newArrayList(WALL_LEFT, WALL_TOP, WALL_RIGHT, WALL_BOTTOM);

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
        if (i == 0 || i == rows - 1) {
          store[i][j] = H_WALL;
          continue;
        } else if (j == 0 || j == columns - 1) {
          store[i][j] = V_WALL;
          continue;
        }
        store[i][j] = ROAD;
      }
    }
  }

  public void dump(String path) throws IOException {
    File file = new File(path);
    try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
          pw.write(store[i][j]);
        }
        pw.write('\n');
      }
    }
  }

  public void dump() throws IOException {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        System.out.print(store[i][j]);
      }
      System.out.println();
    }
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

  private void setDoor(int x, int y) {
    store[x][y] = ROAD;
  }

  private boolean canSplit(int a, int b) {
    return b - a > 4;
  }

  private List<Integer> randomWalls() {
    Collections.shuffle(WALLS);
    return WALLS.subList(0, 3);
  }

  public void createMaze() {
    createMaze(0, rows - 1, 0, columns - 1);
  }

  public void createMaze(int rFrom, int rTo, int cFrom, int cTo) {
    if (!canSplit(cFrom, cTo) || !canSplit(rFrom, rTo)) {
      return;
    }
    // Split area into 4 rooms randomly
    // Randomly choose center point
    int vWallFrom = cFrom + 2, vWallTo = cTo - 2, centerColumn = random(vWallFrom, vWallTo);
    int hWallFrom = rFrom + 2, hWallTo = rTo - 2, centerRow = random(hWallFrom, hWallTo);

    // Build horizontally wall
    fillRow(cFrom + 1, cTo - 1, centerRow, H_WALL);
    // Build vertical wall
    fillColumn(rFrom + 1, rTo - 1, centerColumn, V_WALL);

    // LeftTopArea
    createMaze(rFrom, centerRow, cFrom, centerColumn);
    // RightTopArea
    createMaze(rFrom, centerRow, centerColumn, cTo);
    // LeftBottomArea
    createMaze(centerRow, rTo, cFrom, centerColumn);
    // RightBottomArea
    createMaze(centerRow, rTo, centerColumn, cTo);

    //    System.out.println("Center=(R:" + centerRow + ",C:" + centerColumn + ")");
    List<Integer> wallsNeed2MakeDoor = randomWalls();
    for (Integer wallNo : wallsNeed2MakeDoor) {
      int doorFrom, doorTo, door;
      switch (wallNo) {
        case WALL_LEFT:
          doorFrom = cFrom + 1;
          doorTo = centerColumn - 1;
          door = random(doorFrom, doorTo);
          setDoor(centerRow, door);
          break;
        case WALL_TOP:
          doorFrom = rFrom + 1;
          doorTo = centerRow - 1;
          door = random(doorFrom, doorTo);
          setDoor(door, centerColumn);
          break;
        case WALL_RIGHT:
          doorFrom = centerColumn + 1;
          doorTo = cTo - 1;
          door = random(doorFrom, doorTo);
          setDoor(centerRow, door);
          break;
        case WALL_BOTTOM:
          doorFrom = centerRow + 1;
          doorTo = rTo - 1;
          door = random(doorFrom, doorTo);
          setDoor(door, centerColumn);
          break;
      }
    }
    setEntranceExit();
  }

  private void setEntranceExit() {
    store[1][0] = ROAD;
    store[rows - 2][columns - 1] = ROAD;
  }

  public static void main(String[] args) throws IOException {
    RecursiveSplitMaze maze = new RecursiveSplitMaze(100);
    maze.createMaze();
    maze.dump("/Users/WuZijing/tmp_data/maze/recursive_maze.txt");
    //    maze.dump();
  }
}
