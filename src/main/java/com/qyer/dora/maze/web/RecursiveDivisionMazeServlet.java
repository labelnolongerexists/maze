package com.qyer.dora.maze.web;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.qyer.dora.maze.generator.Maze;
import com.qyer.dora.maze.generator.RecursiveDivision4MazeGenerator;

import java.io.IOException;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:44 Package: com.qyer.dora.maze.web
 */
@Singleton
public class RecursiveDivisionMazeServlet extends TileBasedMazeServlet {

  @Inject
  public RecursiveDivisionMazeServlet() {
  }

  @Override
  protected Maze generateMaze(int width, int height, int b) throws IOException {
    RecursiveDivision4MazeGenerator g = new RecursiveDivision4MazeGenerator(b, height, width);
    g.createMaze();
    return g.getMaze();
  }
}
