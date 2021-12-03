package ObjectPool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName PersonFactory
 * @Description TODO
 * @Author jackdking
 * @Date 15/11/2021 1:51 下午
 * @Version 2.0
 **/
public class ObjectResourceFactory extends BasePooledObjectFactory<ObjectResource> {
    @Override
    public ObjectResource create() throws Exception {
        return new ObjectResource();
    }

    @Override
    public PooledObject wrap(ObjectResource o) {
        return null;
    }

    @Override
    public boolean validateObject(PooledObject<ObjectResource> p) {
        return super.validateObject(p);
    }

    @Override
    public PooledObject<ObjectResource> makeObject() throws Exception {
        return new DefaultPooledObject<ObjectResource>(new ObjectResource());
    }
}
