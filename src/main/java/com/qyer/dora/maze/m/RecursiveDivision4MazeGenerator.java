package com.qyer.dora.maze.m;

import static com.qyer.dora.maze.Constants.ACCESSIBLE;
import static com.qyer.dora.maze.Constants.BLOCK;
import static com.qyer.dora.maze.Utils.closedRandom;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * User: Z J Wu Date: 2019-02-20 Time: 18:26 Package: com.hhrb.maze
 */
public class RecursiveDivision4MazeGenerator extends AbstractMazeGenerator {

  private final int MIN = 2;

  private final int WALL_LEFT = 0;
  private final int WALL_TOP = 1;
  private final int WALL_RIGHT = 2;
  private final int WALL_BOTTOM = 3;
  private List<Integer> WALLS = Lists.newArrayList(WALL_LEFT, WALL_TOP, WALL_RIGHT, WALL_BOTTOM);

  public RecursiveDivision4MazeGenerator(int rowsColumns) {
    this(rowsColumns, rowsColumns);
  }

  public RecursiveDivision4MazeGenerator(int rows, int columns) {
    super(rows, columns);
    grid.border(BLOCK);
    grid.fill(ACCESSIBLE, 1, rows - 1, 1, columns - 1);
  }

  private void fillColumn(int rowFrom, int rowTo, int column, byte entity) {
    int start = grid.isAccessible(rowFrom - 1, column) ? rowFrom + 1 : rowFrom;
    int to = grid.isAccessible(rowTo + 1, column) ? rowTo - 1 : rowTo;
    for (int i = start; i <= to; i++) {
      for (int j = 0; j < grid.getColumns(); j++) {
        grid.updateVal(i, column, entity);
      }
    }
  }

  private void fillRow(int columnFrom, int columnTo, int row, byte entity) {
    int start, to;
    if (grid.isAccessible(row, columnFrom - 1)) {
      start = columnFrom + 1;
    } else {
      start = columnFrom;
    }
    if (grid.isAccessible(row, columnTo + 1)) {
      to = columnTo - 1;
    } else {
      to = columnTo;
    }
    for (int i = start; i <= to; i++) {
      grid.updateVal(row, i, entity);
    }
  }

  private void setDoor(int r, int c) {
    grid.updateVal(r, c, ACCESSIBLE);
  }

  private boolean canSplit(int a, int b) {
    return (b - a) > (MIN * 2);
  }

  private List<Integer> randomWalls() {
    Collections.shuffle(WALLS);
    return WALLS.subList(0, 3);
  }

  private void makeDoors(List<Integer> wallIds, int centerRow, int centerColumn, int cFrom, int cTo,
                         int rFrom, int rTo) {
    for (Integer wallId : wallIds) {
      switch (wallId) {
        case WALL_LEFT:
          setDoor(centerRow, closedRandom(cFrom + 1, centerColumn - 1));
          break;
        case WALL_TOP:
          setDoor(closedRandom(rFrom + 1, centerRow - 1), centerColumn);
          break;
        case WALL_RIGHT:
          setDoor(centerRow, closedRandom(centerColumn + 1, cTo - 1));
          break;
        case WALL_BOTTOM:
          setDoor(closedRandom(centerRow + 1, rTo - 1), centerColumn);
          break;
      }
    }
  }

  public void createMaze() throws IOException {
    createMaze(0, grid.getRows() - 1, 0, grid.getColumns() - 1);
    grid.defaultEntrance();
    grid.defaultExit();
  }

  public void createMaze(int rFrom, int rTo, int cFrom, int cTo) throws IOException {
    if (!canSplit(cFrom, cTo) || !canSplit(rFrom, rTo)) {
      return;
    }
    // Split area into 4 rooms randomly
    // Randomly choose center point
    int vWallFrom = cFrom + MIN, vWallTo = cTo - MIN, centerColumn = closedRandom(vWallFrom,
                                                                                  vWallTo);
    int hWallFrom = rFrom + MIN, hWallTo = rTo - MIN, centerRow = closedRandom(hWallFrom, hWallTo);

    // Build horizontally wall
    fillRow(cFrom + 1, cTo - 1, centerRow, BLOCK);
    // Build vertical wall
    fillColumn(rFrom + 1, rTo - 1, centerColumn, BLOCK);

    makeDoors(randomWalls(), centerRow, centerColumn, cFrom, cTo, rFrom, rTo);

    // LeftTopArea
    createMaze(rFrom, centerRow, cFrom, centerColumn);
    // RightTopArea
    createMaze(rFrom, centerRow, centerColumn, cTo);
    // LeftBottomArea
    createMaze(centerRow, rTo, cFrom, centerColumn);
    // RightBottomArea
    createMaze(centerRow, rTo, centerColumn, cTo);
    //    System.out.println("Center=(R:" + centerRow + ",C:" + centerColumn + ")");
  }

}
