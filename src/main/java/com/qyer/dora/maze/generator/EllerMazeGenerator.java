package com.qyer.dora.maze.generator;

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

  public EllerMazeGenerator(int brushSize, int rowsColumns) {
    super(brushSize, rowsColumns);
  }

  public EllerMazeGenerator(int brushSize, int rows, int columns) {
    super(brushSize, rows, columns);
    int j = columns / 4;
    this.randomJoint = (j < 3) ? 3 : j;
  }

  public static final EllerMazeGenerator createGenerator(int brushSize, int rowsColumns) {
    return createGenerator(brushSize, rowsColumns, rowsColumns);
  }

  public static final EllerMazeGenerator createGenerator(int brushSize, int rows, int columns) {
    int r = ((rows % 2) == 0) ? (rows + 1) : rows;
    int c = ((columns % 2) == 0) ? (columns + 1) : columns;
    return new EllerMazeGenerator(brushSize, r, c);
  }

  @Override
  public void createMaze() throws Exception {
    maze.border(BLOCK);
    int s = 2;
    for (int r = 1; r < maze.getRows() - 1; r++) {
      // odd: create random area
      if ((r & 1) == 1) {
        for (int c = maze.firstColumn() + 1; c < maze.lastColumn(); c += Utils
          .closedRandom(2, randomJoint) * s) {
          maze.updateVal(r, c - 1, BLOCK);
        }
      }
      // even: fill with h-walls
      else {
        System.out.println("---------------------------------");
        // LastRowWall
        int lastWallCol, newWallCol = 0;
        for (int c = maze.firstColumn() + 1; c <= maze.lastColumn(); c++) {
          if (maze.isBlocked(r - 1, c)) {
            maze.updateVal(r, c, H_BLOCK);

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
              System.out.println(f + "/" + t);
              maze.updateRowSegment(r, f, t, H_BLOCK);
              f = t + 1;
            }
            maze.updateRowSegment(r, f, c, H_BLOCK);
//            System.out.println(
//              lastWallCol + " - " + newWallCol + " - " + candidates + " - selected: " + sub);
          }
        }
      }
      maze.updateRowSegment(maze.lastRow() - 1, maze.firstColumn() + 1, maze.lastColumn(),
                            ACCESSIBLE);
    }
    maze.defaultEntrance();
    maze.defaultExit();
  }

  public static void main(String[] args) throws Exception {
    EllerMazeGenerator emg = new EllerMazeGenerator(1, 5, 41);
    emg.createMaze();
    emg.dump();
  }
}
