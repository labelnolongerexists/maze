package com.qyer.dora.maze.web;

import static com.qyer.dora.maze.Constants.BRUSH_MIDDLE;
import static com.qyer.dora.maze.Constants.BRUSH_THICK;
import static com.qyer.dora.maze.Constants.BRUSH_THIN;
import static com.qyer.dora.maze.Constants.DEFAULT_MAZE_WIDTH;

import com.qyer.dora.maze.Utils;
import com.qyer.dora.maze.TileBasedMap;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * User: Z J Wu Date: 2019-02-26 Time: 16:35 Package: com.qyer.dora.maze.web
 */
public abstract class TileBasedMazeServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
    IOException {
    String widthStr = req.getParameter("w"), heightStr = req.getParameter("h"), brushStr = req
      .getParameter("b");
    int width = StringUtils.isBlank(widthStr) ? DEFAULT_MAZE_WIDTH : Integer.parseInt(widthStr);
    int height = StringUtils.isBlank(heightStr) ? (int) (width * 0.7) : Integer.parseInt(heightStr);
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
    TileBasedMap tileBasedMap = null;
    try {
      tileBasedMap = generateMaze(width, height, brush);
    } catch (Exception e) {
      e.printStackTrace();
    }
    BufferedImage image = Utils.makeImage(tileBasedMap, brush);
    resp.setContentType("image/png");
    Utils.writeImage(image, "png", resp.getOutputStream());
  }

  protected abstract TileBasedMap generateMaze(int width, int height, int b) throws Exception;
}
