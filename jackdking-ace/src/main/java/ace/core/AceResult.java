package ace.core;

import lombok.Data;

import java.util.function.Consumer;

@Data
public class AceResult<T>{
    private Boolean isEffect;
    private T result;

    private static final AceResult EMPTY = new AceResult(null, null);

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

    public static AceResult empty() {
        @SuppressWarnings("unchecked")
        AceResult t = (AceResult) EMPTY;
        return t;
    }

    public boolean isPresent() {
        return isEffect != null;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (isEffect != null)
            consumer.accept(result);
    }
}