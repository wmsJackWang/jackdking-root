package ace;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class AceContext<T> {

    private String aceSence;
    private T dataParam;
    private Map<String,AceResult<T>> attributorResultMap;

    private AceContext(T dataParam , String aceSence){
        AceContext.this.aceSence = aceSence;
        AceContext.this.dataParam = dataParam;
    }
    public static AceContext of(Object dataParam, String aceSence){
        return new AceContext(dataParam,aceSence);
    }

    public String getAceSence() {
        return aceSence;
    }

    public Map<String, AceResult<T>> getAttributorResultMap() {
        return attributorResultMap;
    }
    public void addAttributorResultMap(String key , AceResult<T> attributorResult){
        if(attributorResultMap==null) {
            attributorResultMap = Maps.newHashMap();
        }
        attributorResultMap.put(key, attributorResult);
    }
    public T getDataParam() {
        return dataParam;
    }
}
