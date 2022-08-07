import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.concurrent.ExecutorService;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName Test
 * @Description TODO
 * @Author jackdking
 * @Date 25/07/2022 9:47 下午
 * @Version 2.0
 **/
public class Test {
    private static Random random = new Random();
    public static void main(String[] args) throws MalformedObjectNameException, InterruptedException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, IOException {
        ExecutorService es1 = ThreadPoolMonitor.newCachedThreadPool("test-pool-1");
        ThreadPoolParam threadPoolParam1 = new ThreadPoolParam(es1);

        ExecutorService es2 = ThreadPoolMonitor.newCachedThreadPool("test-pool-2");
        ThreadPoolParam threadPoolParam2 = new ThreadPoolParam(es2);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        String domainName = "MyMBean";
        mbs.registerMBean(threadPoolParam1, new ObjectName("test-pool-1:type=threadPoolParam"));
        mbs.registerMBean(threadPoolParam2, new ObjectName("test-pool-2:type=threadPoolParam"));


        ObjectName adapterName = new ObjectName(domainName+":name=htmladapter,port=8082");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        adapter.start();
        mbs.registerMBean(adapter,adapterName);

        int rmiPort = 1099;
        Registry registry = LocateRegistry.createRegistry(rmiPort);

        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:"+rmiPort+"/"+domainName);
        JMXConnectorServer jmxConnector = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        jmxConnector.start();
        //http连接的方式查看监控任务

        executeTask(es1);
        executeTask(es2);
        Thread.sleep(1000 * 60 * 60);
    }

    private static void executeTask(ExecutorService es) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                int temp = i;
                es.submit(() -> {
                    //随机睡眠时间
                    try {
                        Thread.sleep(random.nextInt(60) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(temp);
                });
            }
        }).start();
    }
}