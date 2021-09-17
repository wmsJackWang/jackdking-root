package ace.core.api;

import ace.core.AceContext;
import com.google.common.base.Preconditions;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName IAttributorMatcher
 * @Description TODO
 * @Author jackdking
 * @Date 17/09/2021 5:17 下午
 * @Version 2.0
 **/
public interface IAttributorMatcher<T, S, R> {

    default public boolean defaultCheck(AceContext aceContext) {

        Preconditions.checkNotNull(aceContext,"aceContext cannot be null");
        Preconditions.checkNotNull(aceContext.getDataParam(),"dataParam of aceContext cannot be null");

        return true;
    }

    public boolean isMatch(T contextDataParam, S rulerParam);
}
