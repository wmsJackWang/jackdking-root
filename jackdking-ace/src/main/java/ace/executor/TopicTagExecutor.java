package ace.executor;

import ace.AceContext;
import ace.AceResult;
import ace.annoation.Executor;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName TopicTagExecutor
 * @Description TODO
 * @Author jackdking
 * @Date 11/08/2021 12:14 下午
 * @Version 2.0
 **/
@Executor(name = "TopicTagExecutor")
public class TopicTagExecutor implements IExecutor{
    @Override
    public AceResult execute(AceContext aceContext) {
        return new AceResult(true,"execute TopicTagExecutor");
    }
}
