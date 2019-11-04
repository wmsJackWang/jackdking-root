package springboot.MySolution_RWseparation.mybatis.dynamicdatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class JDKingDynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
    	String dataType = DynamicDataSourceHolder.getType().toString();
    	System.out.println(dataType);
        return dataType;
    }
}
