package com.qyer.dora.maze.generator;

import static com.qyer.dora.maze.Constants.BLOCK;
import static com.qyer.dora.maze.Constants.BRUSH_MIDDLE;
import static com.qyer.dora.maze.Constants.R;

import com.google.common.collect.Lists;
import com.qyer.dora.maze.Constants;
import com.qyer.dora.shape.RCPoint;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
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

  private final List<Integer> DIRECTIONS = Lists.newArrayList(D_LEFT, D_TOP, D_RIGHT, D_BOTTOM);

  public PrimMazeGenerator(int brushSize, int rowsColumns) {
    this(brushSize, rowsColumns, rowsColumns);
  }

  public PrimMazeGenerator(int brushSize, int rows, int columns) {
    super(brushSize, ((rows % 2) == 0) ? (rows + 1) : rows,
          ((columns % 2) == 0) ? (columns + 1) : columns);
    fillWith(BLOCK);
  }

  private void tryAddPoint(LinkedBlockingQueue<RCPoint> q, Set<RCPoint> visited, RCPoint p) {
    if (p == null || visited.contains(p)) {
      return;
    }
    q.add(p);
  }

  public void createMaze() {
    int s = 2;
    final List<RCPoint[]> candidate = Lists.newLinkedList();
    int r = closedRandom(2, rows - 3), c = closedRandom(2, columns - 3);
    candidate.add(new RCPoint[]{new RCPoint(r, c), new RCPoint(r, c)});
    while (CollectionUtils.isNotEmpty(candidate)) {
      RCPoint[] segment = candidate.remove(R.nextInt(candidate.size()));
      RCPoint p1 = segment[0], p2 = segment[1];
      if (isAccessable(getContent(p2))) {
        continue;
      }
      updateMazePoint(p1, Constants.ACCESSABLE);
      updateMazePoint(p2, Constants.ACCESSABLE);
      r = p2.getRow();
      c = p2.getColumn();
      if (r > s && isWall(getContent(r - s, c))) {
        candidate.add(new RCPoint[]{new RCPoint(r - s + 1, c), new RCPoint(r - s, c)});
      }
      if (c > s && isWall(getContent(r, c - s))) {
        candidate.add(new RCPoint[]{new RCPoint(r, c + 1 - s), new RCPoint(r, c - s)});
      }
      if (r < rows - 1 - s && isWall(getContent(r + s, c))) {
        candidate.add(new RCPoint[]{new RCPoint(r + s - 1, c), new RCPoint(r + s, c)});
      }
      if (c < columns - 1 - s && isWall(getContent(r, c + s))) {
        candidate.add(new RCPoint[]{new RCPoint(r, c + s - 1), new RCPoint(r, c + s)});
      }
    }
    setEntranceExit();
  }

  private void updateMazePoint(RCPoint p, byte type) {
    store[p.getRow()][p.getColumn()] = type;
  }

  public static void main(String[] args) throws IOException {
    PrimMazeGenerator pmg = new PrimMazeGenerator(BRUSH_MIDDLE, 20);
    pmg.createMaze();
    pmg.dump();
  }
}
