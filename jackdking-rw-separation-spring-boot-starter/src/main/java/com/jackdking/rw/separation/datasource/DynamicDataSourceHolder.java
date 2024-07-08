package com.jackdking.rw.separation.datasource;

import com.jackdking.rw.separation.enums.DatabaseMSPrefixType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mingshengwang
 * @date 2019年10月29日 下午4:41:03
 * @todo TODO
 * @descript null
 */
public class DynamicDataSourceHolder {

    private static final InheritableThreadLocal<String> dataSourceHolder = new InheritableThreadLocal<String>();

    public static final InheritableThreadLocal<String> monotonicReadArgsHolder = new InheritableThreadLocal<String>();

    @Getter
    private static final Map<String, MasterWithManySlaverWrapper> dsContext = new HashMap<>();

    public static void addMasterSlave(String key, MasterWithManySlaverWrapper wrapper) {
        dsContext.put(key, wrapper);
    }

    public static String getMasterDsKey(String dsName) {
        return String.format("%s:%s", DatabaseMSPrefixType.MASTER.getPrefix(), dsName);
    }

    public static String getSlaveDsKey(String dsName) {
        return String.format("%s:%s", DatabaseMSPrefixType.SLAVE.getPrefix(), dsName);
    }

    public static boolean isDataSourceExists(String dsName) {

        String dataSourceKey = getMasterDsKey(dsName);
        return dsContext.keySet().contains(dataSourceKey);
    }

    public static void setType(String dataSourceType) {
        if (dataSourceType == null) {
            throw new NullPointerException();
        }
        dataSourceHolder.set(dataSourceType);
    }

    public static String getType() {
        return dataSourceHolder.get();
    }

    public static void clearType() {
        dataSourceHolder.remove();
    }

}
