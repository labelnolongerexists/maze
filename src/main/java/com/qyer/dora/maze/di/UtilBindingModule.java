package com.qyer.dora.maze.di;

import static com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect;
import static com.google.inject.name.Names.named;
import static com.qyer.dora.maze.Constants.ENV;
import static com.qyer.dora.maze.di.InjectKeys.IK_DEFAULT_SERVLET_CONFIG;
import static com.qyer.dora.maze.di.InjectKeys.IK_ES_ASYNC_SERVLET;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.AbstractModule;
import com.qyer.commons.utils.serialization.JsonpFastJsonSerializer2;
import com.qyer.commons.web.ServletConfig;
import com.qyer.log.UncaughtExceptionLogger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * User: Z J Wu Date: 2018/09/05 Time: 13:11 Package: com.qyer.webserver.test
 */
public class UtilBindingModule extends AbstractModule {

  @Override
  protected void configure() {

    // Servlet配置
    // 异步请求处理的线程池
    ThreadFactory tf = new ThreadFactoryBuilder().setDaemon(true)
                                                 .setNameFormat("AsyncServletWorker(%d)")
                                                 .setUncaughtExceptionHandler(
                                                   new UncaughtExceptionLogger()).build();
    ExecutorService es = Executors.newCachedThreadPool(tf);
    // 绑定一下用于web容器析构时销毁
    bind(ExecutorService.class).annotatedWith(named(IK_ES_ASYNC_SERVLET)).toInstance(es);

    // 标准的使用AlibabaFastJson的支持Jsonp处理的序列化器
    JsonpFastJsonSerializer2 s = new JsonpFastJsonSerializer2.Builder()
      .addFeature(DisableCircularReferenceDetect).build();
    ServletConfig servletConfig = new ServletConfig().serializer(s).allSiteAccess()
                                                     .ignoreExecutionResult().passErrorMsg2Result()
                                                     .requestEncoding("utf8")
                                                     .printStackTraceForBusinessErr().env(ENV)
                                                     .async(es);

    bind(ServletConfig.class).annotatedWith(named(IK_DEFAULT_SERVLET_CONFIG))
                             .toInstance(servletConfig);
  }

}
