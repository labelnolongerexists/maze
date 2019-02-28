package com.qyer.dora.maze.web;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.qyer.dora.maze.TileBasedGrid;
import com.qyer.dora.maze.m.RecursiveBacktrackMazeGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:44 Package: com.qyer.dora.maze.web
 */
@Singleton
public class RecursiveBacktrackMazeServlet extends TileBasedMazeServlet {

  @Inject
  public RecursiveBacktrackMazeServlet() {
  }

  @Override
  protected TileBasedGrid generateMaze(HttpServletRequest req, HttpServletResponse resp, int width,
                                       int height) throws Exception {
    RecursiveBacktrackMazeGenerator g = RecursiveBacktrackMazeGenerator
      .createGenerator(height, width);
    g.createMaze();
    return g.getGrid();
  }
}
