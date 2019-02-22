package com.qyer.dora.maze.generator;

import static com.qyer.dora.maze.Constants.ACCESSABLE;
import static com.qyer.dora.maze.Constants.BLOCK;

import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * User: Z J Wu Date: 2019-02-20 Time: 18:26 Package: com.hhrb.maze
 */
public class RecursiveDivisionMazeGenerator extends AbstractMazeGenerator {

  private final int MIN = 2;

  private final int WALL_LEFT = 0;
  private final int WALL_TOP = 1;
  private final int WALL_RIGHT = 2;
  private final int WALL_BOTTOM = 3;
  private List<Integer> WALLS = Lists.newArrayList(WALL_LEFT, WALL_TOP, WALL_RIGHT, WALL_BOTTOM);

  public RecursiveDivisionMazeGenerator(int brushSize, int rowsColumns) {
    super(brushSize, rowsColumns);
  }

  public RecursiveDivisionMazeGenerator(int brushSize, int rows, int columns) {
    super(brushSize, rows, columns);
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
    return (b - a) > (MIN * 2);
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
          setDoor(centerRow, closedRandom(cFrom + 1, centerColumn - 1));
          break;
        case WALL_TOP:
          setDoor(closedRandom(rFrom + 1, centerRow - 1), centerColumn);
          break;
        case WALL_RIGHT:
          setDoor(centerRow, closedRandom(centerColumn + 1, cTo - 1));
          break;
        case WALL_BOTTOM:
          setDoor(closedRandom(centerRow + 1, rTo - 1), centerColumn);
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
    int vWallFrom = cFrom + MIN, vWallTo = cTo - MIN, centerColumn = closedRandom(vWallFrom,
                                                                                  vWallTo);
    int hWallFrom = rFrom + MIN, hWallTo = rTo - MIN, centerRow = closedRandom(hWallFrom, hWallTo);

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
      Color roadC = new Color(150, 150, 150), wallC = new Color(50, 50, 50);
      g2d.setBackground(wallC);
      g2d.clearRect(0, 0, width, height);

      final int r = brushSize, b = brushSize;
      for (int i = 0; i < store.length; i++) {
        for (int j = 0; j < store[i].length; j++) {
          if (isWall(store[i][j])) {
            g2d.setPaint(wallC);
            g2d.fillRect(j * b, i * b, b, b);
          } else {
            g2d.setPaint(roadC);
            g2d.fillRect(j * r, i * r, r, r);
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
    int step = 40;
    RecursiveDivisionMazeGenerator maze = new RecursiveDivisionMazeGenerator(10, step,
                                                                             (int) (step * 2.5));
    maze.createMaze();
    //    maze.dump();
    maze.dump("/Users/WuZijing/tmp_data/maze/recursive_maze");
    //    maze.writeMaze("/Users/WuZijing/tmp_data/maze/recursive_maze");
  }
}
