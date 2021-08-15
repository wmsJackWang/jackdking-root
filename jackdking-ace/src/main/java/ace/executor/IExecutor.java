package ace.executor;

import ace.core.AceContext;
import ace.core.AceResult;
/*
 * 执行单元
 */
public interface IExecutor {
    public AceResult execute(AceContext aceContext);
}
