package com.jackdking.sharding.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JDKingDynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataType = DynamicDataSourceHolder.getType();
        return dataType;
    }

    public JDKingDynamicDataSource() {
        // TODO Auto-generated constructor stub
        mutilDs = new ArrayList<String>();
    }

    private static List<String> mutilDs;

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        // TODO Auto-generated method stub

        if (targetDataSources.size() <= 0) {
            throw new RuntimeException("多数据源不能配置为空");
        }

        for (Object key : targetDataSources.keySet()) {
            mutilDs.add(String.valueOf(key));
        }

        super.setTargetDataSources(targetDataSources);
    }

    public static boolean isReady() {
        return mutilDs != null && mutilDs.size() > 0;
    }

    public static boolean contains(String dsName) {
        if (mutilDs != null && mutilDs.size() > 0)
            return mutilDs.contains(dsName);
        return false;
    }
}
