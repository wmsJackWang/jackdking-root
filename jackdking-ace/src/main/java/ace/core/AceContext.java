package ace.core;

import ace.enums.AceScene;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class AceContext<T,S,R> {

    private AceScene aceScene;
    private T dataParam;
    private S rulerParam;
    private Map<String, AceResult<R>> attributeResultMap;

    private AceContext(T dataParam , AceScene aceScene,S rulerParam){
        AceContext.this.aceScene = aceScene;
        AceContext.this.dataParam = dataParam;
        AceContext.this.rulerParam = rulerParam;
    }
    public static AceContext of(Object dataParam, AceScene aceSence){
        return new AceContext(dataParam,aceSence,null);
    }

    public AceScene getAceScene() {
        return aceScene;
    }

    public Map<String, AceResult<R>> getAttributeResultMap() {
        return attributeResultMap;
    }
    public void addAttributeResultMap(String key , AceResult<R> attributorResult){
        if(attributeResultMap ==null) {
            attributeResultMap = Maps.newHashMap();
        }
        attributeResultMap.put(key, attributorResult);
    }
}
