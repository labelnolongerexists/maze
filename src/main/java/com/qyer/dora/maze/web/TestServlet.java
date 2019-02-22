package com.qyer.dora.maze.web;

import static com.qyer.dora.maze.di.InjectKeys.IK_DEFAULT_SERVLET_CONFIG;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.qyer.commons.param.BasicHttpParameter;
import com.qyer.commons.web.BasicAsyncServlet;
import com.qyer.commons.web.ServletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:44 Package: com.qyer.dora.maze.web
 */
@Singleton
public class TestServlet extends BasicAsyncServlet<BasicHttpParameter> {

  @Inject
  public TestServlet(@Named(IK_DEFAULT_SERVLET_CONFIG) ServletConfig config) {
    super(config);
  }

  @Override
  protected Object serve(BasicHttpParameter p, HttpServletRequest httpServletRequest,
                         HttpServletResponse resp) throws Exception {
    return null;
  }

  @Override
  protected BasicHttpParameter extractParameters(HttpServletRequest req) throws Exception {
    return new BasicHttpParameter(req);
  }
}
