package bittech;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName BitTechBlog
 * @Description TODO
 * @Author jackdking
 * @Date 16/01/2024 5:41 下午
 * @Version 2.0
 **/
public class BitTechBlog implements BitTechBlogI{

  static MethodInterceptor interceptor = new MethodInterceptor() {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      System.out.println("advice:"+methodInvocation.getArguments()[0]);
      return methodInvocation.proceed();
    }
  };

  public static void main(String[] args) {
    ProxyFactory pf = new ProxyFactory(BitTechBlogI.class, interceptor);
    pf.setTarget(new BitTechBlog());
    BitTechBlogI bt = (BitTechBlogI) pf.getProxy();//代理的是接口，代理对象只能识别到接口类型，无法识别接口的实现类。因为返回的对象类型是BitTechBlogI新的实现类
    bt.print("jack");

    Object proxy = pf.getProxy();
    Class cla = proxy.getClass();
    Class[] supperClass = cla.getInterfaces();
  }

  @Override
  public void print(String name) {

    System.out.println("print name:"+name);
  }


}
