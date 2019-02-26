package com.qyer.dora.maze.di;

import com.google.inject.servlet.ServletModule;
import com.qyer.dora.maze.web.EllerMazeServlet;
import com.qyer.dora.maze.web.PrimMazeServlet;
import com.qyer.dora.maze.web.RecursiveDivisionMazeServlet;

/**
 * User: Z J Wu Date: 2018/10/16 Time: 19:09 Package: com.qyer.munich.tags.di
 */
public class ServletMappingModule extends ServletModule {

  @Override
  protected void configureServlets() {
    serve("/maze/rd").with(RecursiveDivisionMazeServlet.class);
    serve("/maze/prim").with(PrimMazeServlet.class);
    serve("/maze/eller").with(EllerMazeServlet.class);
  }
}
