package jackdking.dt.web.model.param;

import lombok.Data;

@Data
public class DynamicConfigRefreshParam {

    String dynamicThreadPoolId;

    int coreSize;

    int maxSize;

}
