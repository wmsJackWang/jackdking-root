package org.jackdking.limiter.enums;

public enum LimiterType {

    COUNT(1, "count limiter"),
    SLIDING_WINDOW(2, "sliding window limiter"),
    TOKEN_BUCKET(3, "token bucket limiter"),
    LEAK_BUCKET(4, "Leak Bucket limiter");

    int type;
    String desc;



    LimiterType(int type, String desc){
        this.type = type;
        this.desc = desc;

    }
}
