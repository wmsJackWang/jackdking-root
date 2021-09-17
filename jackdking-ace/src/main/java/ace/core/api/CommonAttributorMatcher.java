package ace.core.api;

import ace.core.AceContext;
import com.google.common.base.Preconditions;
import org.checkerframework.checker.units.qual.A;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName CommonAttributorMatcher
 * @Description TODO
 * @Author jackdking
 * @Date 17/09/2021 5:09 下午
 * @Version 2.0
 **/
public abstract class CommonAttributorMatcher<T, S, R> extends AttributorMatcher<T, S, R>  {

    @Override
    public boolean bizCheck(AceContext aceContext) {
        return true;
    }
}
