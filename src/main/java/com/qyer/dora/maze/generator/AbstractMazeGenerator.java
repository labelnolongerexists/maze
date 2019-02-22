package com.qyer.dora.maze.generator;

import static com.qyer.dora.maze.Constants.ACCESSABLE;
import static com.qyer.dora.maze.Constants.BLOCK;
import static com.qyer.dora.maze.Constants.G_WALL;
import static com.qyer.dora.maze.Constants.R;
import static com.qyer.dora.maze.Constants.ROAD;

import com.qyer.dora.shape.RCPoint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 13:45 Package: com.qyer.dora.maze.generator
 */
public class AbstractMazeGenerator {

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
    border();
  }

  protected void border() {
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

  protected void fillWith(byte type) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        store[i][j] = type;
      }
    }
  }

  public boolean isWall(byte b) {
    return b != ACCESSABLE;
  }

  public boolean isAccessable(byte b) {
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

}
