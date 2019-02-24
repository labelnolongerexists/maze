package com.qyer.dora.maze.generator;

import static com.qyer.dora.maze.Constants.BLOCK;
import static com.qyer.dora.maze.Constants.BRUSH_MIDDLE;
import static com.qyer.dora.maze.Constants.R;

import com.google.common.collect.Lists;
import com.qyer.dora.maze.Constants;
import com.qyer.dora.maze.Utils;
import com.qyer.dora.shape.RCPoint;
import com.qyer.dora.shape.RCSegment;
import org.apache.commons.collections4.CollectionUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 13:44 Package: com.qyer.dora.maze.generator
 */
public class PrimMazeGenerator extends AbstractMazeGenerator {

  private static final int D_LEFT = 0;
  private static final int D_TOP = 1;
  private static final int D_RIGHT = 2;
  private static final int D_BOTTOM = 3;

  public PrimMazeGenerator(int brushSize, int rowsColumns) {
    this(brushSize, rowsColumns, rowsColumns);
  }

  public PrimMazeGenerator(int brushSize, int rows, int columns) {
    super(brushSize, ((rows % 2) == 0) ? (rows + 1) : rows,
          ((columns % 2) == 0) ? (columns + 1) : columns);
    fill(BLOCK, 0, rows, 0, columns);
  }

  public static final PrimMazeGenerator createGenerator(int brushSize, int rowsColumns) {
    return createGenerator(brushSize, rowsColumns, rowsColumns);
  }

  public static final PrimMazeGenerator createGenerator(int brushSize, int rows, int columns) {
    int r = ((rows % 2) == 0) ? (rows + 1) : rows;
    int c = ((columns % 2) == 0) ? (columns + 1) : columns;
    return new PrimMazeGenerator(brushSize, r, c);
  }

  private void tryAddPoint(LinkedBlockingQueue<RCPoint> q, Set<RCPoint> visited, RCPoint p) {
    if (p == null || visited.contains(p)) {
      return;
    }
    q.add(p);
  }

  public void createMaze() throws IOException {
    int s = 2;
    final List<RCSegment> candidate = Lists.newLinkedList();
    int row = closedRandom(s, rows - 1 - s), col = closedRandom(s, columns - 1 - s);
    RCPoint initPoint = new RCPoint(1, 1);

    candidate.add(new RCSegment(initPoint, initPoint));
    while (CollectionUtils.isNotEmpty(candidate)) {
      RCSegment rcSegment = candidate.remove(R.nextInt(candidate.size()));
      RCPoint frontier = rcSegment.getFrom(), wall = rcSegment.getTo();
      if (isAccessible(getContent(frontier))) {
        continue;
      }
      updateMazePoint(frontier, Constants.ACCESSABLE);
      updateMazePoint(wall, Constants.ACCESSABLE);
      row = frontier.getRow();
      col = frontier.getColumn();
      if (row >= firstRow() + s && isBlocked(getContent(row - s, col))) {
        candidate.add(new RCSegment(new RCPoint(row - s, col), new RCPoint(row - s + 1, col)));
      }
      if (col >= firstColumn() + s && isBlocked(getContent(row, col - s))) {
        candidate.add(new RCSegment(new RCPoint(row, col - s), new RCPoint(row, col - s + 1)));
      }

      if (row <= lastRow() - s && isBlocked(getContent(row + s, col))) {
        candidate.add(new RCSegment(new RCPoint(row + s, col), new RCPoint(row + s - 1, col)));
      }
      if (col <= lastColumn() - s && isBlocked(getContent(row, col + s))) {
        candidate.add(new RCSegment(new RCPoint(row, col + s), new RCPoint(row, col + s - 1)));
      }
    }
    border(BLOCK);
    setEntranceExit();
  }

  private void updateMazePoint(RCPoint p, byte type) {
    store[p.getRow()][p.getColumn()] = type;
  }

  public static void main(String[] args) throws IOException {
    PrimMazeGenerator maze = PrimMazeGenerator.createGenerator(BRUSH_MIDDLE, 80);
    maze.createMaze();
    BufferedImage image = maze.makeImage();
    try (OutputStream os = new FileOutputStream(new File("d:/maze.png"))) {
      Utils.writeImage(image, "png", os);
    }
  }
}
