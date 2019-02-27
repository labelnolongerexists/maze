package com.qyer.dora.maze.web;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.qyer.dora.maze.TileBasedMap;
import com.qyer.dora.maze.dungeon.CellularAutomatonCave;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  protected TileBasedMap generateMaze(HttpServletRequest req, HttpServletResponse resp, int width,
                                      int height) throws IOException {
    String p = StringUtils.trimToEmpty(req.getParameter("p"));
    int initBlockPercent = StringUtils.isBlank(p) ? 40 : Integer.parseInt(p);
    p = StringUtils.trimToEmpty(req.getParameter("f"));
    int foreachTimes = StringUtils.isBlank(p) ? 1 : Integer.parseInt(p);
    CellularAutomatonCave cave = new CellularAutomatonCave(height, width, initBlockPercent,
                                                           foreachTimes);
    cave.foreach();
    return cave.getMap();
  }
}
