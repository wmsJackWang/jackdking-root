package ace.executor;

import ace.AceContext;
import ace.AceResult;
/*
 * 执行单元
 */
public interface IExecutor {
    public AceResult execute(AceContext aceContext);
}
