package com.qyer.dora.maze.m;

import static com.qyer.dora.maze.Constants.BLOCK;

import com.google.common.collect.Lists;
import com.qyer.dora.maze.Constants;
import com.qyer.dora.maze.Utils;
import com.qyer.dora.shape.RCPoint;
import com.qyer.dora.shape.RCSegment;

import java.util.Collections;
import java.util.List;

/**
 * User: Z J Wu Date: 2019-02-28 Time: 10:34 Package: com.qyer.dora.maze.m
 */
public class RecursiveBacktrackMazeGenerator extends AbstractMazeGenerator {

  // left, right, top, bottom
  private final List<Byte> DIRECTION = Lists.newArrayList((byte) 0, (byte) 1, (byte) 2, (byte) 3);

  private RecursiveBacktrackMazeGenerator(int rowsColumns) {
    this(rowsColumns, rowsColumns);
  }

  private RecursiveBacktrackMazeGenerator(int rows, int columns) {
    super(rows, columns);
  }

  public static final RecursiveBacktrackMazeGenerator createGenerator(int rows, int columns) {
    int r = ((rows % 2) == 0) ? (rows + 1) : rows;
    int c = ((columns % 2) == 0) ? (columns + 1) : columns;
    return new RecursiveBacktrackMazeGenerator(r, c);
  }

  private boolean stopDig(RCPoint point) {
    return grid.isBorder(point.getRow(), point.getColumn()) || grid
      .isAccessible(point.getRow(), point.getColumn());
  }

  @Override
  public void createMaze() throws Exception {
    grid
      .fill(BLOCK, grid.firstRow() + 1, grid.lastRow(), grid.firstColumn() + 1, grid.lastColumn());
    RCPoint initPoint = new RCPoint(1, 1);
    makePassage(new RCSegment(initPoint, initPoint));

    grid.border(BLOCK);
    grid.defaultEntrance();
    grid.defaultExit();
  }

  private void makePassage(RCSegment s) {
    if (stopDig(s.getTo())) {
      //      System.out.println("cant dig - " + s.getTo());
      return;
    }
    int rF = s.getFromRow(), cF = s.getFromColumn();
    int rT = s.getToRow(), cT = s.getToColumn();
    grid.updateVal(rF, cF, Constants.ACCESSIBLE);
    grid.updateVal(rT, cT, Constants.ACCESSIBLE);
    //    System.out.println("---------------------------------");
    //    Utils.dump(grid);
    List<Byte> currentRoundDirections = Lists.newArrayList(DIRECTION);
    Collections.shuffle(currentRoundDirections);
    //    System.out.println(currentRoundDirections);
    //    RCPoint to = s.getTo();
    for (int d : currentRoundDirections) {
      switch (d) {
        case 0:
          //          System.out.println(to + " - Dig left");
          makePassage(new RCSegment(grid.left(rT, cT, 1), grid.left(rT, cT, 2)));
          break;
        case 1:
          //          System.out.println(to + " - Dig right");
          makePassage(new RCSegment(grid.right(rT, cT, 1), grid.right(rT, cT, 2)));
          break;
        case 2:
          //          System.out.println(to + " - Dig up");
          makePassage(new RCSegment(grid.top(rT, cT, 1), grid.top(rT, cT, 2)));
          break;
        case 3:
          //          System.out.println(to + " - Dig down");
          makePassage(new RCSegment(grid.bottom(rT, cT, 1), grid.bottom(rT, cT, 2)));
          break;
      }
    }
  }

  public static void main(String[] args) throws Exception {
    RecursiveBacktrackMazeGenerator g = RecursiveBacktrackMazeGenerator.createGenerator(10, 20);
    g.createMaze();
    System.out.println("---------------------------------");
    Utils.dump(g.grid);
  }
}
