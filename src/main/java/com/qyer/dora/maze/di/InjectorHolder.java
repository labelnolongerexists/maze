package com.qyer.dora.maze.di;

import static com.google.inject.Stage.PRODUCTION;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * User: Z J Wu Date: 2018/05/11 Time: 13:50 Package: com.qyer.mp.razor.main.utils
 */
public class InjectorHolder {

  private static InjectorHolder INSTANCE = new InjectorHolder();
  private final Injector injector;

  private InjectorHolder() {
    this.injector = Guice
      .createInjector(PRODUCTION, new UtilBindingModule(), new ServletMappingModule());
  }

  public static InjectorHolder getInstance() {
    return INSTANCE;
  }

  public Injector getInjector() {
    return injector;
  }
}
