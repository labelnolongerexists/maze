package com.qyer.dora.maze.web;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.qyer.dora.maze.TileBasedMap;
import com.qyer.dora.maze.m.PrimMazeGenerator;

import java.io.IOException;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:44 Package: com.qyer.dora.maze.web
 */
@Singleton
public class PrimMazeServlet extends TileBasedMazeServlet {

  @Inject
  public PrimMazeServlet() {
  }

  @Override
  protected TileBasedMap generateMaze(int width, int height, int b) throws IOException {
    PrimMazeGenerator g = PrimMazeGenerator.createGenerator(b, height, width);
    g.createMaze();
    return g.getTileBasedMap();
  }

}
