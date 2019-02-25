package com.qyer.dora.maze.generator;

import static com.qyer.dora.maze.Constants.ACCESSABLE;
import static com.qyer.dora.maze.Constants.BLOCK;
import static com.qyer.dora.maze.Constants.R;

import com.google.common.collect.Lists;
import com.qyer.dora.shape.RCPoint;
import com.qyer.dora.shape.RCSegment;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 13:44 Package: com.qyer.dora.maze.generator
 */
public class PrimMazeGenerator extends AbstractMazeGenerator {

  private static final int STEP = 2;

  private PrimMazeGenerator(int brushSize, int rows, int columns) {
    super(brushSize, rows, columns);
    maze.fill(BLOCK, 0, rows, 0, columns);
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
    final List<RCSegment> candidate = Lists.newLinkedList();
    RCPoint initPoint = new RCPoint(1, 1);
    candidate.add(new RCSegment(initPoint, initPoint));
    while (CollectionUtils.isNotEmpty(candidate)) {
      RCSegment rcSegment = candidate.remove(R.nextInt(candidate.size()));
      RCPoint frontier = rcSegment.getFrom(), wall = rcSegment.getTo();
      if (maze.isAccessible(frontier)) {
        continue;
      }
      maze.updateVal(frontier, ACCESSABLE);
      maze.updateVal(wall, ACCESSABLE);
      int row = frontier.getRow(), col = frontier.getColumn();
      if (row >= maze.firstRow() + STEP && maze.isBlocked(row - STEP, col)) {
        candidate
          .add(new RCSegment(new RCPoint(row - STEP, col), new RCPoint(row - STEP + 1, col)));
      }
      if (col >= maze.firstColumn() + STEP && maze.isBlocked(row, col - STEP)) {
        candidate
          .add(new RCSegment(new RCPoint(row, col - STEP), new RCPoint(row, col - STEP + 1)));
      }
      if (row <= maze.lastRow() - STEP && maze.isBlocked(row + STEP, col)) {
        candidate
          .add(new RCSegment(new RCPoint(row + STEP, col), new RCPoint(row + STEP - 1, col)));
      }
      if (col <= maze.lastColumn() - STEP && maze.isBlocked(row, col + STEP)) {
        candidate
          .add(new RCSegment(new RCPoint(row, col + STEP), new RCPoint(row, col + STEP - 1)));
      }
    }
    maze.border(BLOCK);
    maze.defaultEntrance();
    maze.defaultExit();
  }

}
