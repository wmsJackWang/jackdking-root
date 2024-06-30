package jackdking.dt.core.executer.support;

import org.slf4j.MDC;

import static jackdking.dt.common.constant.Constants.EXECUTE_TIMEOUT_TRACE;

public class ExecutorContext {

    public static void putExecuteTimeoutTrace(String executeTimeoutTrace) {
        MDC.put(EXECUTE_TIMEOUT_TRACE, executeTimeoutTrace);
    }

}
