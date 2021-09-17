package ace.attributor;

import ace.annoation.Attributor;
import ace.annoation.Ruler;
import ace.constant.Constants;
import ace.core.AceAttributorCheckManager;
import ace.core.AceContext;
import ace.core.AceResult;
import ace.core.api.CommonAttributorMatcher;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName OrderTypeAttributor
 * @Description TODO
 * @Author jackdking
 * @Date 17/09/2021 3:50 下午
 * @Version 2.0
 **/
@Attributor(name = "orderTypeAttributor" , fieldName = "orderType")
@Slf4j
@Data
public class OrderTypeAttributor implements IAttributor {

    @Ruler(name = "refundRuler")
    public AceResult refundChecker(AceContext<Map<String,Object>, String[],Object> aceContext) {

        log.debug("begin to execute ruler {}","refundRuler");

        boolean result = AceAttributorCheckManager.check(aceContext, new CommonAttributorMatcher<Map<String,Object>, String[],Object>() {

            @Override
            public boolean isMatch(Map<String, Object> contextDataParam, String[] rulerParam) {

                Object orderType = contextDataParam.get(Constants.orderType);
                if(Arrays.stream(rulerParam).anyMatch(tag -> tag.equals(orderType))) {
                    return true;
                }
                return false;
            }
        }, Constants.orderType);

        AceResult checkTopicTagResult = new AceResult(result,null);
        log.debug("checkTopicTag ruler result :{}", JSON.toJSONString(checkTopicTagResult));
        return checkTopicTagResult;
    }
}
