package com.qyer.dora.maze.generator;

import com.qyer.dora.maze.Constants;
import com.qyer.dora.maze.Utils;

/**
 * User: Z J Wu Date: 2019-02-25 Time: 17:06 Package: com.qyer.dora.maze.generator
 */
public class EllerMazeGenerator extends AbstractMazeGenerator {

  public EllerMazeGenerator(int brushSize, int rowsColumns) {
    super(brushSize, rowsColumns);
  }

  public EllerMazeGenerator(int brushSize, int rows, int columns) {
    super(brushSize, rows, columns);
  }

  @Override
  public void createMaze() throws Exception {
    maze.border(Constants.BLOCK);
    int s = 2;
    for (int r = 1; r < maze.getRows() - 1; r++) {
      for (int c = 1  ; c < maze.getColumns() - 1; c += Utils.closedRandom(1, 3) * 2) {
        maze.updateVal(r, c - 1, Constants.BLOCK);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    EllerMazeGenerator emg = new EllerMazeGenerator(1, 10, 27);
    emg.createMaze();
    emg.dump();
  }
}
