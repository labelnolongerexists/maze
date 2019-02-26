package com.qyer.dora.maze.web;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.qyer.dora.maze.generator.EllerMazeGenerator;
import com.qyer.dora.maze.generator.Maze;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:44 Package: com.qyer.dora.maze.web
 */
@Singleton
public class EllerMazeServlet extends TileBasedMazeServlet {

  @Inject
  public EllerMazeServlet() {
  }

  @Override
  protected Maze generateMaze(int width, int height, int b) throws Exception {
    EllerMazeGenerator g = EllerMazeGenerator.createGenerator(b, height, width);
    g.createMaze();
    return g.getMaze();
  }

}
