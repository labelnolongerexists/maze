package com.qyer.dora.maze.di;

import static com.google.inject.name.Names.named;
import static com.qyer.dora.maze.di.InjectKeys.IK_ES_ASYNC_SERVLET;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.AbstractModule;

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
                                                 .setNameFormat("AsyncServletWorker(%d)").build();
    ExecutorService es = Executors.newCachedThreadPool(tf);
    // 绑定一下用于web容器析构时销毁
    bind(ExecutorService.class).annotatedWith(named(IK_ES_ASYNC_SERVLET)).toInstance(es);
  }

}
