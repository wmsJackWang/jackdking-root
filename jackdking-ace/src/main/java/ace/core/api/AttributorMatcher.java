package ace.core.api;

import ace.constant.Constants;
import ace.core.AceContext;
import com.google.common.base.Preconditions;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName AttributorMatcher
 * @Description TODO
 * @Author jackdking
 * @Date 17/09/2021 4:14 下午
 * @Version 2.0
 **/
public abstract class AttributorMatcher<T, S, R> implements IAttributorMatcher<T, S, R> {


    public abstract boolean bizCheck(AceContext<T, S, R> aceContext);

}
