package com.qyer.dora.maze.web;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.qyer.dora.maze.m.EllerMazeGenerator;
import com.qyer.dora.maze.TileBasedMap;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:44 Package: com.qyer.dora.maze.web
 */
@Singleton
public class EllerMazeServlet extends TileBasedMazeServlet {

  @Inject
  public EllerMazeServlet() {
  }

  @Override
  protected TileBasedMap generateMaze(int width, int height, int b) throws Exception {
    EllerMazeGenerator g = EllerMazeGenerator.createGenerator(b, height, width);
    g.createMaze();
    return g.getTileBasedMap();
  }

}
