package ObjectPool;

import com.google.common.collect.Lists;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName JdkObjectPool
 * @Description TODO
 * @Author jackdking
 * @Date 15/11/2021 1:46 下午
 * @Version 2.0
 **/
public class JdkObjectResourcePool extends GenericObjectPool<ObjectResource> {

    public static GenericObjectPoolConfig getConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMinIdle(5);
        config.setMaxTotal(5);
        config.setMaxWaitMillis(20000);
        return  config;
    }

    JdkObjectResourcePool(PooledObjectFactory factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }

    public JdkObjectResourcePool(PooledObjectFactory factory) {
        super(factory);
    }



    public static void main(String[] args) {

        ObjectResourceFactory objectResourceFactory = new ObjectResourceFactory();
        JdkObjectResourcePool jdkObjectResourcePool = new JdkObjectResourcePool(objectResourceFactory, getConfig());
        System.out.println(jdkObjectResourcePool.getNumActive());

        try {
            ObjectResource objectResource = jdkObjectResourcePool.borrowObject();
            System.out.println(jdkObjectResourcePool.getNumActive());
            System.out.println(objectResource.toString());
            jdkObjectResourcePool.returnObject(objectResource);

            objectResource = jdkObjectResourcePool.borrowObject();
            System.out.println(jdkObjectResourcePool.getNumActive());
            System.out.println(objectResource.toString());
            jdkObjectResourcePool.returnObject(objectResource);

            objectResource = jdkObjectResourcePool.borrowObject();
            System.out.println(jdkObjectResourcePool.getNumActive());
            System.out.println(objectResource.toString());
            jdkObjectResourcePool.returnObject(objectResource);


            List<ObjectResource> list = Lists.newArrayList(jdkObjectResourcePool.borrowObject(),
                    jdkObjectResourcePool.borrowObject(),
                    jdkObjectResourcePool.borrowObject(),
                    jdkObjectResourcePool.borrowObject(),
                    jdkObjectResourcePool.borrowObject());

            System.out.println(jdkObjectResourcePool.getNumActive());
            list.stream()
                    .forEach((obj -> {
                        System.out.println(obj.toString());
                    }));

            //上面list代码已经获取了全部的对象，这个时候再去获取对象，就要阻塞等待一段时间
            objectResource = jdkObjectResourcePool.borrowObject();

            System.out.println(jdkObjectResourcePool.getNumActive());
            System.out.println(objectResource.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
