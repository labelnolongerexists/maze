package com.qyer.dora.maze.web;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.qyer.dora.maze.TileBasedMap;
import com.qyer.dora.maze.m.RecursiveDivision4MazeGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  protected TileBasedMap generateMaze(HttpServletRequest req, HttpServletResponse resp, int width,
                                      int height) throws IOException {
    RecursiveDivision4MazeGenerator g = new RecursiveDivision4MazeGenerator(height, width);
    g.createMaze();
    return g.getTileBasedMap();
  }
}
