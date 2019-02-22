package com.qyer.dora.maze.web;

import static com.qyer.dora.maze.Constants.BRUSH_MIDDLE;
import static com.qyer.dora.maze.Constants.BRUSH_THICK;
import static com.qyer.dora.maze.Constants.BRUSH_THIN;
import static com.qyer.dora.maze.di.InjectKeys.IK_DEFAULT_SERVLET_CONFIG;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.qyer.commons.param.BasicHttpParameter;
import com.qyer.commons.web.BasicAsyncServlet;
import com.qyer.commons.web.ServletConfig;
import com.qyer.dora.maze.generator.RecursiveDivisionMazeGenerator;
import com.qyer.dora.maze.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:44 Package: com.qyer.dora.maze.web
 */
@Singleton
public class RecursiveDivisionMazeServlet extends BasicAsyncServlet<BasicHttpParameter> {

  @Inject
  public RecursiveDivisionMazeServlet(@Named(IK_DEFAULT_SERVLET_CONFIG) ServletConfig config) {
    super(config);
  }

  @Override
  protected String responseContentType(String contentType) {
    return super.responseContentType(contentType);
  }

  @Override
  protected String responseContentType() {
    return "image/png";
  }

  @Override
  protected Object serve(BasicHttpParameter p, HttpServletRequest httpServletRequest,
                         HttpServletResponse resp) throws Exception {
    int width = p.getAsInt("w", 100), height = p.getAsInt("h", 100), brush = p
      .getAsInt("b", BRUSH_MIDDLE);
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
    RecursiveDivisionMazeGenerator maze = new RecursiveDivisionMazeGenerator(brush, height, width);
    maze.createMaze();
    BufferedImage image = maze.makeImage();
    Utils.writeImage(image, "png", resp.getOutputStream());
    return null;
  }

  @Override
  protected BasicHttpParameter extractParameters(HttpServletRequest req) throws Exception {
    return new BasicHttpParameter(req);
  }
}
