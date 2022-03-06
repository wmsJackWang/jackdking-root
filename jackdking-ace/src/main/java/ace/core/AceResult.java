package ace.core;

import lombok.Data;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@Data
public class AceResult<T>{
    private Boolean isEffect;
    private T result;

    private static final AceResult EMPTY = new AceResult(null, null);
    private static final AceResult SUCCESS = new AceResult(true, null);
    private static final AceResult FAIL = new AceResult(false, null);


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

    public AceResult and(AceResult aceResult, BiFunction biFunction) {
        return new AceResult(Boolean.logicalAnd(this.isEffect,aceResult.isEffect),biFunction.apply(this, aceResult));
    }

    public AceResult negative(){
        this.isEffect = !this.isEffect;
        return this;
    }

    public AceResult or(AceResult aceResult) {
        return new AceResult(Boolean.logicalOr(this.isEffect,aceResult.isEffect),null);
    }

    public AceResult or(AceResult aceResult, BiFunction biFunction) {
        return new AceResult(Boolean.logicalOr(this.isEffect,aceResult.isEffect),biFunction.apply(this, aceResult));
    }


    public static AceResult success(){
        return SUCCESS;
    }

    public static AceResult fail(){
        return FAIL;
    }

    public static AceResult empty() {
        return EMPTY;
    }

    public boolean isPresent() {
        return isEffect != null;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (isEffect != null)
            consumer.accept(result);
    }
}
