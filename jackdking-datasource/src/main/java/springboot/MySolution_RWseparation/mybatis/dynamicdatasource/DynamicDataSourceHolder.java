package springboot.MySolution_RWseparation.mybatis.dynamicdatasource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mingshengwang
 * @date 2019年10月29日 下午4:41:03
 * @todo TODO
 * @descript null
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<DataSourceType> dataSourceHolder = new ThreadLocal<>();

    private static final Set<DataSourceType> dataSourceTypes = new HashSet<>();
    static {
        dataSourceTypes.add(DataSourceType.MASTER);
        dataSourceTypes.add(DataSourceType.SLAVE);
    }

    public static void setType(DataSourceType dataSourceType){
        if(dataSourceType == null){
            throw new NullPointerException();
        }
        dataSourceHolder.set(dataSourceType);
    }

    public static DataSourceType getType(){
        return dataSourceHolder.get();
    }

    public static void clearType(){
        dataSourceHolder.remove();
    }

    public static boolean containsType(DataSourceType dataSourceType){
        return dataSourceTypes.contains(dataSourceType);
    }
    
    public static void main(String[] args) {
		System.out.println(DataSourceType.MASTER.toString());
	}
}