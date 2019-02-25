package com.qyer.dora.maze.web;

import static com.qyer.dora.maze.Constants.BRUSH_MIDDLE;
import static com.qyer.dora.maze.Constants.BRUSH_THICK;
import static com.qyer.dora.maze.Constants.BRUSH_THIN;
import static com.qyer.dora.maze.Constants.DEFAULT_MAZE_HEIGHT;
import static com.qyer.dora.maze.Constants.DEFAULT_MAZE_WIDTH;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.qyer.dora.maze.Utils;
import com.qyer.dora.maze.generator.RecursiveDivisionMazeGenerator;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:44 Package: com.qyer.dora.maze.web
 */
@Singleton
public class RecursiveDivisionMazeServlet extends HttpServlet {

  @Inject
  public RecursiveDivisionMazeServlet() {
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
    IOException {
    resp.setContentType("image/png");
    String widthStr = req.getParameter("w"), heightStr = req.getParameter("h"), brushStr = req
      .getParameter("b");
    int width = StringUtils.isBlank(widthStr) ? DEFAULT_MAZE_WIDTH : Integer.parseInt(widthStr);
    int height = StringUtils.isBlank(heightStr) ? DEFAULT_MAZE_HEIGHT : Integer.parseInt(heightStr);
    int brush = StringUtils.isBlank(brushStr) ? BRUSH_MIDDLE : Integer.parseInt(brushStr);
    if (width > 500) {
      width = 500;
    }
    if (height > 500) {
      height = 500;
    }
    if (brush < BRUSH_THIN) {
      brush = BRUSH_THIN;
    } else if (brush > BRUSH_THICK) {
      brush = BRUSH_THICK;
    }
    RecursiveDivisionMazeGenerator g = new RecursiveDivisionMazeGenerator(brush, height, width);
    g.createMaze();
    BufferedImage image = g.makeImage(g.getMaze());
    Utils.writeImage(image, "png", resp.getOutputStream());
  }

}
