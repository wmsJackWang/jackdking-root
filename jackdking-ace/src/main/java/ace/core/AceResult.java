package ace.core;

import lombok.Data;

@Data
public class AceResult<T>{
    private Boolean isEffect;
    private T result;
    public AceResult(Boolean isEffect , T result){
        AceResult.this.isEffect = isEffect;
        AceResult.this.result = result;
    }

    public AceResult() {
        this(false,null);
    }
    
    public AceResult and(AceResult aceResult) {
        return new AceResult(Boolean.logicalAnd(this.isEffect,aceResult.isEffect),null);
    }

    public AceResult negative(){
        this.isEffect = !this.isEffect;
        return this;
    }

    public AceResult or(AceResult aceResult) {
        return new AceResult(Boolean.logicalOr(this.isEffect,aceResult.isEffect),null);
    }

    public static AceResult success(){
        return new AceResult(true,null);
    }

    public static AceResult fail(){
        return new AceResult(false,null);
    }
}
