package com.jackdking.rw.separation.datasource;

/**
 * @author mingshengwang
 * @date 2019年10月29日 下午4:41:03
 * @todo TODO
 * @descript null
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<String>();


    public static void setType(String dataSourceType){
        if(dataSourceType == null){
            throw new NullPointerException();
        }
        dataSourceHolder.set(dataSourceType);
    }

    public static String getType(){
        return dataSourceHolder.get();
    }

    public static void clearType(){
        dataSourceHolder.remove();
    }

}
