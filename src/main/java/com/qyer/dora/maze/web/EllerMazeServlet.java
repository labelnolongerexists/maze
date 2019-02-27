package com.qyer.dora.maze.web;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.qyer.dora.maze.TileBasedMap;
import com.qyer.dora.maze.m.EllerMazeGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:44 Package: com.qyer.dora.maze.web
 */
@Singleton
public class EllerMazeServlet extends TileBasedMazeServlet {

  @Inject
  public EllerMazeServlet() {
  }

  @Override
  protected TileBasedMap generateMaze(HttpServletRequest req, HttpServletResponse resp, int width,
                                      int height) throws Exception {
    EllerMazeGenerator g = EllerMazeGenerator.createGenerator(height, width);
    g.createMaze();
    return g.getTileBasedMap();
  }

}
