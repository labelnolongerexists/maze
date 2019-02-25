package com.qyer.dora.maze.generator;

import static com.qyer.dora.maze.Constants.ACCESSIBLE;
import static com.qyer.dora.maze.Constants.BLOCK;
import static com.qyer.dora.maze.Constants.C_ACCESSIBLE;
import static com.qyer.dora.maze.Constants.C_BACKGROUND;
import static com.qyer.dora.maze.Constants.C_BLOCKED;
import static com.qyer.dora.maze.Constants.DEFAULT_BORDER;
import static com.qyer.dora.maze.Constants.G_WALL;
import static com.qyer.dora.maze.Constants.ROAD;

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

  public BufferedImage makeImage(Maze maze) {
    int r = maze.getRows(), c = maze.getColumns();
    int width = maze.getColumns() * brushSize, height = maze.getRows() * brushSize;

    final int b = brushSize, bd = DEFAULT_BORDER;
    // 创建BufferedImage对象
    BufferedImage image = new BufferedImage(width + bd * 2, height + bd * 2,
                                            BufferedImage.TYPE_INT_RGB);
    // 获取Graphics2D
    Graphics2D g2d = null;
    try {
      g2d = image.createGraphics();
      // 画图
      g2d.setBackground(C_BACKGROUND);
      g2d.clearRect(0, 0, width + bd * 2, height + bd * 2);

      for (int i = 0; i < r; i++) {
        for (int j = 0; j < c; j++) {
          if (maze.isBlocked(maze.getContent(i, j))) {
            g2d.setPaint(C_BLOCKED);
            g2d.fillRect(bd + j * b, bd + i * b, b, b);
          } else {
            g2d.setPaint(C_ACCESSIBLE);
            g2d.fillRect(bd + j * b, bd + i * b, b, b);
          }
        }
        g2d.setPaint(C_ACCESSIBLE);
        g2d.fillRect(bd + c * b, bd + i * b, b, b);
      }
      g2d.setPaint(C_ACCESSIBLE);
      g2d.fillRect(height + bd, 0, width, bd);
    } finally {
      //释放对象
      if (g2d != null) {
        g2d.dispose();
      }
    }
    return image;
  }

  public void writeMaze(Maze maze, String path) throws Exception {
    ImageIO.write(makeImage(maze), "png", new File(path + ".png"));
  }

  public abstract void createMaze() throws Exception;

}
