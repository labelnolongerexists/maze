package com.qyer.dora.maze.web;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.qyer.dora.maze.TileBasedMap;
import com.qyer.dora.maze.dungeon.CellularAutomatonCave;

import java.io.IOException;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:44 Package: com.qyer.dora.maze.web
 */
@Singleton
public class CACaveServlet extends TileBasedMazeServlet {

  @Inject
  public CACaveServlet() {
  }

  @Override
  protected TileBasedMap generateMaze(int width, int height, int b) throws IOException {
    CellularAutomatonCave cave = new CellularAutomatonCave(height, width);
    cave.foreachOnce();
    return cave.getMap();
  }
}
