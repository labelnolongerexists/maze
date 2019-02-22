package com.qyer.dora.maze;

import static com.qyer.dora.maze.Constants.ACCESSABLE;
import static com.qyer.dora.maze.Constants.BLOCK;
import static com.qyer.dora.maze.Constants.G_WALL;
import static com.qyer.dora.maze.Constants.ROAD;

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
public class RecursiveDivisionMaze {

  private final Random R = new Random();

  private int brushSize;
  private int rows;
  private int columns;

  private byte[][] store;

  private final int WALL_LEFT = 0;
  private final int WALL_TOP = 1;
  private final int WALL_RIGHT = 2;
  private final int WALL_BOTTOM = 3;
  private List<Integer> WALLS = Lists.newArrayList(WALL_LEFT, WALL_TOP, WALL_RIGHT, WALL_BOTTOM);

  public RecursiveDivisionMaze(int brushSize, int rowsColumns) {
    this(brushSize, rowsColumns, rowsColumns);
  }

  public RecursiveDivisionMaze(int brushSize, int rows, int columns) {
    this.brushSize = brushSize;
    this.rows = rows;
    this.columns = columns;
    this.store = new byte[rows][columns];
    border();
  }

  private void border() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (i == 0 || i == rows - 1) {
          store[i][j] = BLOCK;
          continue;
        } else if (j == 0 || j == columns - 1) {
          store[i][j] = BLOCK;
          continue;
        }
        store[i][j] = ACCESSABLE;
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

  public boolean isBlock(int i) {
    return i != ACCESSABLE;
  }

  public boolean isAccessable(int i) {
    return i == ACCESSABLE;
  }

  public void dump() throws IOException {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        switch (store[i][j]) {
          case ACCESSABLE:
            System.out.print(ROAD);
            break;
          case BLOCK:
            System.out.print(G_WALL);
            break;
        }
      }
      System.out.println();
    }
  }

  private int random(int fromClosed, int toClosed) {
    return R.nextInt(toClosed + 1 - fromClosed) + fromClosed;
  }

  private void fillColumn(int rowFrom, int rowTo, int column, byte entity) {
    int start = isAccessable(store[rowFrom - 1][column]) ? rowFrom + 1 : rowFrom;
    int to = isAccessable(store[rowTo + 1][column]) ? rowTo - 1 : rowTo;
    for (int i = start; i <= to; i++) {
      for (int j = 0; j < columns; j++) {
        store[i][column] = entity;
      }
    }
  }

  private void fillRow(int columnFrom, int columnTo, int row, byte entity) {
    byte[] rowStore = store[row];
    int start;
    if (isAccessable(rowStore[columnFrom - 1]))
      start = columnFrom + 1;
    else
      start = columnFrom;
    int to;
    if (isAccessable(rowStore[columnTo + 1]))
      to = columnTo - 1;
    else
      to = columnTo;
    for (int i = start; i <= to; i++) {
      rowStore[i] = entity;
    }
  }

  private void setDoor(int x, int y) {
    store[x][y] = ACCESSABLE;
  }

  private boolean canSplit(int a, int b) {
    return b - a > 4;
  }

  private List<Integer> randomWalls() {
    Collections.shuffle(WALLS);
    return WALLS.subList(0, 3);
  }

  public void createMaze() throws IOException {
    createMaze(0, rows - 1, 0, columns - 1);
    setEntranceExit();
  }

  private void makeDoors(List<Integer> wallIds, int centerRow, int centerColumn, int cFrom, int cTo,
                         int rFrom, int rTo) {
    for (Integer wallId : wallIds) {
      switch (wallId) {
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
  }

  public void createMaze(int rFrom, int rTo, int cFrom, int cTo) throws IOException {
    if (!canSplit(cFrom, cTo) || !canSplit(rFrom, rTo)) {
      return;
    }
    // Split area into 4 rooms randomly
    // Randomly choose center point
    int vWallFrom = cFrom + 2, vWallTo = cTo - 2, centerColumn = random(vWallFrom, vWallTo);
    int hWallFrom = rFrom + 2, hWallTo = rTo - 2, centerRow = random(hWallFrom, hWallTo);

    // Build horizontally wall
    fillRow(cFrom + 1, cTo - 1, centerRow, BLOCK);
    // Build vertical wall
    fillColumn(rFrom + 1, rTo - 1, centerColumn, BLOCK);

    makeDoors(randomWalls(), centerRow, centerColumn, cFrom, cTo, rFrom, rTo);

    // LeftTopArea
    createMaze(rFrom, centerRow, cFrom, centerColumn);
    // RightTopArea
    createMaze(rFrom, centerRow, centerColumn, cTo);
    // LeftBottomArea
    createMaze(centerRow, rTo, cFrom, centerColumn);
    // RightBottomArea
    createMaze(centerRow, rTo, centerColumn, cTo);
    //    System.out.println("Center=(R:" + centerRow + ",C:" + centerColumn + ")");
  }

  private void setEntranceExit() {
    store[1][0] = ACCESSABLE;
    store[rows - 2][columns - 1] = ACCESSABLE;
  }

  public BufferedImage makeImage() {
    int width = columns * brushSize;
    int height = rows * brushSize;
    // 创建BufferedImage对象
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // 获取Graphics2D
    Graphics2D g2d = null;
    try {
      g2d = image.createGraphics();
      // 画图
      g2d.setBackground(new Color(200, 200, 200));
      g2d.setPaint(new Color(100, 100, 100));
      g2d.clearRect(0, 0, width, height);

      for (int i = 0; i < store.length; i++) {
        for (int j = 0; j < store[i].length; j++) {
          if (isBlock(store[i][j])) {
            g2d.fillRect(j * brushSize, i * brushSize, brushSize, brushSize);
          }
        }
      }
    } finally {
      //释放对象
      if (g2d != null) {
        g2d.dispose();
      }
    }
    return image;
  }

  public void writeMaze(String path) throws Exception {
    ImageIO.write(makeImage(), "png", new File(path + ".png"));
  }

  public static void main(String[] args) throws Exception {
    RecursiveDivisionMaze maze = new RecursiveDivisionMaze(10, 200);
    maze.createMaze();
    //    maze.dump();
    maze.dump("/Users/WuZijing/tmp_data/maze/recursive_maze");
    maze.writeMaze("/Users/WuZijing/tmp_data/maze/recursive_maze");
  }
}
