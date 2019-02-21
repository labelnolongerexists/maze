package com.hhrb.maze;

import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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

  private int brushSize;
  private int rows;
  private int columns;

  private char[][] store;

  private final int WALL_LEFT = 0;
  private final int WALL_TOP = 1;
  private final int WALL_RIGHT = 2;
  private final int WALL_BOTTOM = 3;
  private List<Integer> WALLS = Lists.newArrayList(WALL_LEFT, WALL_TOP, WALL_RIGHT, WALL_BOTTOM);

  public RecursiveSplitMaze(int brushSize, int rowsColumns) {
    this(brushSize, rowsColumns, rowsColumns);
  }

  public RecursiveSplitMaze(int brushSize, int rows, int columns) {
    this.brushSize = brushSize;
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

  public boolean isWall(char c) {
    return c != ROAD;
  }

  public boolean isRoad(char c) {
    return c == ROAD;
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
    int start = isRoad(store[rowFrom - 1][column]) ? rowFrom + 1 : rowFrom;
    int to = isRoad(store[rowTo + 1][column]) ? rowTo - 1 : rowTo;
    for (int i = start; i <= to; i++) {
      for (int j = 0; j < columns; j++) {
        store[i][column] = c;
      }
    }
  }

  private void fillRow(int columnFrom, int columnTo, int row, char c) {
    char[] rowStore = store[row];
    int start = isRoad(rowStore[columnFrom - 1]) ? columnFrom + 1 : columnFrom;
    int to = isRoad(rowStore[columnTo + 1]) ? columnTo - 1 : columnTo;
    for (int i = start; i <= to; i++) {
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
      switch (wallNo) {
        case WALL_LEFT:
          setDoor(centerRow, random(cFrom + 1, centerColumn - 1));
          break;
        case WALL_TOP:
          setDoor(random(rFrom + 1, centerRow - 1), centerColumn);
          break;
        case WALL_RIGHT:
          setDoor(centerRow, random(centerColumn + 1, cTo - 1));
          break;
        case WALL_BOTTOM:
          setDoor(random(centerRow + 1, rTo - 1), centerColumn);
          break;
      }
    }
    setEntranceExit();
  }

  private void setEntranceExit() {
    store[1][0] = ROAD;
    store[rows - 2][columns - 1] = ROAD;
  }

  public void writeMaze(String path) throws Exception {
    int width = columns * brushSize;
    int height = rows * brushSize;
    // 创建BufferedImage对象
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // 获取Graphics2D
    Graphics2D g2d = image.createGraphics();
    // 画图
    g2d.setBackground(new Color(200, 200, 200));
    g2d.setPaint(new Color(100, 100, 100));
    g2d.clearRect(0, 0, width, height);

    for (int i = 0; i < store.length; i++) {
      for (int j = 0; j < store[i].length; j++) {
        if (isWall(store[i][j])) {
          g2d.fillRect(j * brushSize, i * brushSize, brushSize, brushSize);
        }
      }
    }

    //释放对象
    g2d.dispose();
    ImageIO.write(image, "png", new File(path + ".png"));
  }

  public static void main(String[] args) throws Exception {
    RecursiveSplitMaze maze = new RecursiveSplitMaze(20, 40);
    maze.createMaze();
    maze.dump("/Users/WuZijing/tmp_data/maze/recursive_maze");
    maze.writeMaze("/Users/WuZijing/tmp_data/maze/recursive_maze");
  }
}
