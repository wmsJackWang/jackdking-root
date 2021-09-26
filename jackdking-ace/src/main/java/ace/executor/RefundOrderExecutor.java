package ace.executor;

import ace.annoation.Executor;
import ace.core.AceContext;
import ace.core.AceResult;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName RefundOrderExecutor
 * @Description TODO
 * @Author jackdking
 * @Date 24/09/2021 5:10 下午
 * @Version 2.0
 **/
@Executor(name = "RefundOrderExecutor")
public class RefundOrderExecutor implements IExecutor{
    @Override
    public AceResult execute(AceContext aceContext) {
        return new AceResult(true,"execute RefundOrderExecutor");
    }
}
