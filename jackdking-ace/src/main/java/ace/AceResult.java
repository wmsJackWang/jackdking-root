package com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace;

import lombok.Data;

@Data
public class AceResult<T>{
    private Boolean isEffect;
    private T result;
    public AceResult(Boolean isEffect , T result){
        AceResult.this.isEffect = isEffect;
        AceResult.this.result = result;
    }
}
