package com.qyer.dora.maze.dungeon;

import com.qyer.dora.maze.Constants;
import com.qyer.dora.maze.TileBasedMap;
import com.qyer.dora.maze.Utils;

/**
 * User: Z J Wu Date: 2019-02-26 Time: 18:31 Package: com.qyer.dora.maze.dungeon
 */
public class CellularAutomatonCave {

  private TileBasedMap map;

  public TileBasedMap getMap() {
    return map;
  }

  public CellularAutomatonCave(int row, int column) {
    this.map = new TileBasedMap(row, column);
    this.map.border(Constants.BLOCK);
    randomFill();
  }

  private void randomFill() {
    for (int i = 1; i < map.lastRow(); i++) {
      for (int j = 1; j < map.lastColumn(); j++) {
        if (Utils.generateBooleanProbability(40)) {
          map.updateVal(i, j, Constants.BLOCK);
        }
      }
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
          } else {
            map.updateVal(i, j, Constants.ACCESSIBLE);
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    CellularAutomatonCave cave = new CellularAutomatonCave(30,100);
    cave.foreachOnce();
    Utils.dump(cave.map);
  }
}
