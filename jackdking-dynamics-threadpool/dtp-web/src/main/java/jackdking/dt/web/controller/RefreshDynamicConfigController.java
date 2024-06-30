package jackdking.dt.web.controller;

import jackdking.dt.web.model.param.DynamicConfigRefreshParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/dynamic/threadPool")
public class RefreshDynamicConfigController {


    @PostMapping("/config/refresh")
    public Object refreshDynamicThreadPoolConfig(@RequestBody DynamicConfigRefreshParam param) {

        return null;
    }

}
