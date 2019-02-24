package com.qyer.dora.maze.generator;

import static com.qyer.dora.maze.Constants.ACCESSABLE;
import static com.qyer.dora.maze.Constants.BLOCK;
import static com.qyer.dora.maze.Constants.C_ACCESSIBLE;
import static com.qyer.dora.maze.Constants.C_BLOCKED;
import static com.qyer.dora.maze.Constants.G_WALL;
import static com.qyer.dora.maze.Constants.R;
import static com.qyer.dora.maze.Constants.ROAD;

import com.qyer.dora.shape.RCPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 13:45 Package: com.qyer.dora.maze.generator
 */
public abstract class AbstractMazeGenerator {

  protected int brushSize;
  protected int rows;
  protected int columns;
  protected byte[][] store;

  public AbstractMazeGenerator(int brushSize, int rowsColumns) {
    this(brushSize, rowsColumns, rowsColumns);
  }

  public AbstractMazeGenerator(int brushSize, int rows, int columns) {
    this.brushSize = brushSize;
    this.rows = rows;
    this.columns = columns;
    this.store = new byte[rows][columns];
  }

  protected void border(byte border) {
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

  protected void fill(byte type, int rowFrom, int rowTo, int columnFrom, int columnTo) {
    for (int i = rowFrom; i < rowTo; i++) {
      for (int j = columnFrom; j < columnTo; j++) {
        store[i][j] = type;
      }
    }
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

  public boolean isBlocked(byte b) {
    return b != ACCESSABLE;
  }

  public boolean isAccessible(byte b) {
    return b == ACCESSABLE;
  }

  public byte getContent(RCPoint point) {
    return store[point.getRow()][point.getColumn()];
  }

  protected void setEntranceExit() {
    store[1][0] = ACCESSABLE;
    store[rows - 2][columns - 1] = ACCESSABLE;
  }

  public byte getContent(int r, int c) {
    return store[r][c];
  }

  public void dump(String path) throws IOException {
    File file = new File(path);
    try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
          switch (store[i][j]) {
            case ACCESSABLE:
              pw.write(ROAD);
              break;
            default:
              pw.write(G_WALL);
              break;
          }
        }
        pw.write('\n');
      }
    }
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

  protected int closedRandom(int fromClosed, int toClosed) {
    if (fromClosed > toClosed) {
      return fromClosed;
    }
    return R.nextInt(toClosed + 1 - fromClosed) + fromClosed;
  }

  public BufferedImage makeImage() {
    int width = columns * brushSize;
    int height = rows * brushSize;
    int border = 5;

    // 创建BufferedImage对象
    BufferedImage image = new BufferedImage(width + border * 2, height + border * 2,
                                            BufferedImage.TYPE_INT_RGB);
    // 获取Graphics2D
    Graphics2D g2d = null;
    try {
      g2d = image.createGraphics();
      // 画图
      g2d.setBackground(C_ACCESSIBLE);
      g2d.clearRect(0, 0, width + border * 2, height + border * 2);

      final int b = brushSize;
      for (int i = 0; i < store.length; i++) {
        for (int j = 0; j < store[i].length; j++) {
          if (isBlocked(store[i][j])) {
            g2d.setPaint(C_BLOCKED);
            g2d.fillRect(border + j * b, border + i * b, b, b);
          } else {
            g2d.setPaint(C_ACCESSIBLE);
            g2d.fillRect(border + j * b, border + i * b, b, b);
          }
        }
        g2d.setPaint(C_ACCESSIBLE);
        g2d.fillRect(border + store[i].length * b, border + i * b, b, b);
      }
      g2d.setPaint(C_ACCESSIBLE);
      g2d.fillRect(height + border, 0, width, border);
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

  public abstract void createMaze() throws Exception;

}
