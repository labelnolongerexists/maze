package com.qyer.dora.maze.dungeon;

import com.qyer.dora.maze.Constants;
import com.qyer.dora.maze.TileBasedGrid;
import com.qyer.dora.maze.Utils;
import com.qyer.dora.shape.RCPoint;

/**
 * User: Z J Wu Date: 2019-02-26 Time: 18:31 Package: com.qyer.dora.maze.dungeon
 */
public class CellularAutomatonCave {

  private TileBasedGrid map;

  private int initBlockPercent;
  private int foreachTimes;

  public TileBasedGrid getMap() {
    return map;
  }

  public CellularAutomatonCave(int row, int column, int initBlockPercent, int foreachTimes) {
    this.map = new TileBasedGrid(row, column);
    this.map.border(Constants.BLOCK);
    this.initBlockPercent = initBlockPercent;
    this.foreachTimes = foreachTimes;
    randomFill();
  }

  private void randomFill() {
    for (int i = 1; i < map.lastRow(); i++) {
      for (int j = 1; j < map.lastColumn(); j++) {
        if (Utils.generateBooleanProbability(initBlockPercent)) {
          map.updateVal(i, j, Constants.BLOCK);
        }
      }
    }
  }

  public void foreach() {
    for (int i = 0; i < foreachTimes; i++) {
      foreachOnce();
    }
  }

  public void foreachOnce() {
    for (int i = 1; i < map.lastRow(); i++) {
      for (int j = 1; j < map.lastColumn(); j++) {
        int c = map.surroundedBlockCnt(i, j);
        if (map.isBlocked(i, j)) {
          if (c >= 4) {
            map.updateVal(i, j, Constants.BLOCK);
          } else {
            map.updateVal(i, j, Constants.ACCESSIBLE);
          }
        } else {
          if (c >= 5) {
            map.updateVal(i, j, Constants.BLOCK);
            map.updateVal(RCPoint.top(i, j), Constants.BLOCK);
            map.updateVal(RCPoint.bottom(i, j), Constants.BLOCK);
            map.updateVal(RCPoint.left(i, j), Constants.BLOCK);
            map.updateVal(RCPoint.right(i, j), Constants.BLOCK);
          } else {
            map.updateVal(i, j, Constants.ACCESSIBLE);
          }
        }
      }
    }
  }

}
