package com.qyer.dora.maze.web;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.qyer.dora.maze.Utils;
import com.qyer.dora.maze.di.InjectorHolder;

import javax.servlet.ServletContextEvent;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:39 Package: com.qyer.dora.maze.web
 */
public class ContextListener extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return InjectorHolder.getInstance().getInjector();
  }

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    Injector injector = getInjector();
    Utils.printFileContentInClassPath("./hello");
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    Utils.printFileContentInClassPath("./bye");
  }
}
