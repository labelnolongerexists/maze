package com.qyer.dora.maze.generator;

import static com.qyer.dora.maze.Constants.ACCESSIBLE;
import static com.qyer.dora.maze.Constants.BLOCK;
import static com.qyer.dora.maze.Constants.G_WALL;
import static com.qyer.dora.maze.Constants.ROAD;

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
  protected Maze maze;

  public AbstractMazeGenerator(int brushSize, int rowsColumns) {
    this(brushSize, rowsColumns, rowsColumns);
  }

  public AbstractMazeGenerator(int brushSize, int rows, int columns) {
    this.brushSize = brushSize;
    this.maze = new Maze(rows, columns);
  }

  public int getBrushSize() {
    return brushSize;
  }

  public void setBrushSize(int brushSize) {
    this.brushSize = brushSize;
  }

  public Maze getMaze() {
    return maze;
  }

  public void setMaze(Maze maze) {
    this.maze = maze;
  }

  public void dump(String path) throws IOException {
    File file = new File(path);
    try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
      for (int i = 0; i < maze.getRows(); i++) {
        for (int j = 0; j < maze.getColumns(); j++) {
          switch (maze.getContent(i, j)) {
            case ACCESSIBLE:
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
    for (int i = 0; i < maze.getRows(); i++) {
      for (int j = 0; j < maze.getColumns(); j++) {
        switch (maze.getContent(i, j)) {
          case ACCESSIBLE:
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

  public abstract void createMaze() throws Exception;

}
