package com.qyer.dora.maze.m;

import com.qyer.dora.maze.TileBasedGrid;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 13:45 Package: com.qyer.dora.maze.generator
 */
public abstract class AbstractMazeGenerator {

  protected TileBasedGrid grid;

  public AbstractMazeGenerator(int rowsColumns) {
    this(rowsColumns, rowsColumns);
  }

  public AbstractMazeGenerator(int rows, int columns) {
    this.grid = new TileBasedGrid(rows, columns);
  }

  public TileBasedGrid getGrid() {
    return grid;
  }

  public void setGrid(TileBasedGrid grid) {
    this.grid = grid;
  }

  public abstract void createMaze() throws Exception;

}
