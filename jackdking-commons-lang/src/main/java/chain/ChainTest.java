package chain;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;
import org.apache.commons.chain.impl.ContextBase;
import org.junit.jupiter.api.Test;

import java.net.URL;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ChainTest
 * @Description TODO
 * @Author jackdking
 * @Date 08/12/2021 1:25 下午
 * @Version 2.0
 **/
public class ChainTest {

    @Test
    public void testChain() throws Exception {
        //ConfigRuleSet中定义了"规则"
        ConfigParser configParser = new ConfigParser();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//位于classpath位置
        URL url = classLoader.getResource("chain/catalog.xml");
        configParser.parse(url);
//获得默认的catalog
//其中CatalogFactoryBase是单例模式,在parse是会触发CatalogFactory的实例化
        Catalog catalog = CatalogFactoryBase.getInstance().getCatalog();
        Command chain = catalog.getCommand("printChain");
        Context context = new ContextBase();//A session map,can be useed by all commands
//当前chain中,所有的command依次执行,直到结束,或者其中一个command返回true,或者抛出异常
        chain.execute(context);
    }
}
