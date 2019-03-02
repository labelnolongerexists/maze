package com.qyer.dora.maze.m;

import static com.qyer.dora.maze.Constants.ACCESSIBLE;
import static com.qyer.dora.maze.Constants.BLOCK;
import static com.qyer.dora.maze.Constants.H_BLOCK;

import com.google.common.collect.Lists;
import com.qyer.dora.maze.Utils;

import java.util.Collections;
import java.util.List;

/**
 * User: Z J Wu Date: 2019-02-25 Time: 17:06 Package: com.qyer.dora.maze.generator
 */
public class EllerMazeGenerator extends AbstractMazeGenerator {

  private int randomJoint;

  public EllerMazeGenerator(int rowsColumns) {
    super(rowsColumns);
  }

  public EllerMazeGenerator(int rows, int columns) {
    super(rows, columns);
    int j = columns / 4;
    this.randomJoint = (j < 3) ? 3 : j;
  }

  public static final EllerMazeGenerator createGenerator(int rowsColumns) {
    return createGenerator(rowsColumns, rowsColumns);
  }

  public static final EllerMazeGenerator createGenerator(int rows, int columns) {
    int r = ((rows % 2) == 0) ? (rows + 1) : rows;
    int c = ((columns % 2) == 0) ? (columns + 1) : columns;
    return new EllerMazeGenerator(r, c);
  }

  @Override
  public void createMaze() throws Exception {
    grid.border(BLOCK);
    int s = 2;
    for (int r = 1; r < grid.getRows() - 1; r++) {
      // odd: create random area
      if ((r & 1) == 1) {
        for (int c = grid.firstColumn() + 1; c < grid.lastColumn(); c += Utils
          .closedRandom(1, grid.getColumns()/2) * s) {
          grid.updateVal(r, c - 1, BLOCK);
        }
      }
      // even: fill with h-walls
      else {
        // LastRowWall
        int lastWallCol, newWallCol = 0;
        for (int c = grid.firstColumn() + 1; c <= grid.lastColumn(); c++) {
          if (grid.isBlocked(r - 1, c)) {
            grid.updateVal(r, c, H_BLOCK);

            lastWallCol = newWallCol;
            newWallCol = c;
            int passageFrom = lastWallCol + 1, passageTo = newWallCol - 1, passageCandidateCnt =
              (passageTo - passageFrom) / s;
            List<Integer> candidates = Lists.newArrayListWithCapacity(passageCandidateCnt);
            for (int i = passageFrom; i <= passageTo; i += 2) {
              candidates.add(i);
            }
            List<Integer> sub;
            if (candidates.size() > 3) {
              Collections.shuffle(candidates);
              sub = candidates.subList(0, 2);
              Collections.sort(sub);
            } else {
              sub = Lists.newArrayList(Utils.selectOne(candidates));
            }
            int f = passageFrom, t;
            for (Integer p : sub) {
              t = p;
              grid.updateRowSegment(r, f, t, H_BLOCK);
              f = t + 1;
            }
            grid.updateRowSegment(r, f, c, H_BLOCK);
            //            System.out.println(
            //              lastWallCol + " - " + newWallCol + " - " + candidates + " - selected:
            //              " + sub);
          }
        }
      }
      grid.updateRowSegment(grid.lastRow() - 1, grid.firstColumn() + 1, grid.lastColumn(),
                            ACCESSIBLE);
    }
    grid.defaultEntrance();
    grid.defaultExit();
  }

}
