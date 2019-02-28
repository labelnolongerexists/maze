package com.qyer.dora.maze.m;

import static com.qyer.dora.maze.Constants.ACCESSIBLE;
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

  private PrimMazeGenerator(int rows, int columns) {
    super(rows, columns);
    grid.fill(BLOCK, 0, rows, 0, columns);
  }

  public static final PrimMazeGenerator createGenerator(int rowsColumns) {
    return createGenerator(rowsColumns, rowsColumns);
  }

  public static final PrimMazeGenerator createGenerator(int rows, int columns) {
    int r = ((rows % 2) == 0) ? (rows + 1) : rows;
    int c = ((columns % 2) == 0) ? (columns + 1) : columns;
    return new PrimMazeGenerator(r, c);
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
      if (grid.isAccessible(frontier)) {
        continue;
      }
      grid.updateVal(frontier, ACCESSIBLE);
      grid.updateVal(wall, ACCESSIBLE);
      int row = frontier.getRow(), col = frontier.getColumn();
      if (row >= grid.firstRow() + STEP && grid.isBlocked(row - STEP, col)) {
        candidate
          .add(new RCSegment(new RCPoint(row - STEP, col), new RCPoint(row - STEP + 1, col)));
      }
      if (col >= grid.firstColumn() + STEP && grid.isBlocked(row, col - STEP)) {
        candidate
          .add(new RCSegment(new RCPoint(row, col - STEP), new RCPoint(row, col - STEP + 1)));
      }
      if (row <= grid.lastRow() - STEP && grid.isBlocked(row + STEP, col)) {
        candidate
          .add(new RCSegment(new RCPoint(row + STEP, col), new RCPoint(row + STEP - 1, col)));
      }
      if (col <= grid.lastColumn() - STEP && grid.isBlocked(row, col + STEP)) {
        candidate
          .add(new RCSegment(new RCPoint(row, col + STEP), new RCPoint(row, col + STEP - 1)));
      }
    }
    grid.border(BLOCK);
    grid.defaultEntrance();
    grid.defaultExit();
  }

}
