package com.qyer.dora.maze.m;

import com.qyer.dora.maze.TileBasedMap;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 13:45 Package: com.qyer.dora.maze.generator
 */
public abstract class AbstractMazeGenerator {

  protected int brushSize;
  protected TileBasedMap tileBasedMap;

  public AbstractMazeGenerator(int brushSize, int rowsColumns) {
    this(brushSize, rowsColumns, rowsColumns);
  }

  public AbstractMazeGenerator(int brushSize, int rows, int columns) {
    this.brushSize = brushSize;
    this.tileBasedMap = new TileBasedMap(rows, columns);
  }

  public int getBrushSize() {
    return brushSize;
  }

  public void setBrushSize(int brushSize) {
    this.brushSize = brushSize;
  }

  public TileBasedMap getTileBasedMap() {
    return tileBasedMap;
  }

  public void setTileBasedMap(TileBasedMap tileBasedMap) {
    this.tileBasedMap = tileBasedMap;
  }

  public abstract void createMaze() throws Exception;

}
