package ace.attributor;


import ace.core.AceContext;
import ace.core.AceResult;
import ace.constant.Constants;
import ace.annoation.Attributor;
import ace.annoation.Ruler;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/*
 * 消息tag校验器
 * 该校验器，检测消息的tag。
 */
@Attributor(name = "messageTagAttributor" , fieldName = "tag")
@Slf4j
@Data
public class TopicTagAttributor implements IAttributor {

    @Ruler(name = "checkTopicTag")
    public AceResult attribute(AceContext<Map<String,Object>,String[],Object>  aceContext) {
        log.debug("begin to execute ruler {}","checkTopicTag");
        Preconditions.checkNotNull(aceContext,"TopicTagAttributor cannt be null");
        Preconditions.checkNotNull(aceContext.getDataParam(),"data of aceContext cannt be null");
        Map<String , Object> contextDataParam = Optional.ofNullable(aceContext.getDataParam()).orElse(Collections.emptyMap());
        String[] rulerParam = aceContext.getRulerParam();
        Object tagValue = contextDataParam.get(Constants.TAG);

        Preconditions.checkArgument(!StringUtils.isEmpty(rulerParam),"classifer defined rulerParam cannt be empty");
        Preconditions.checkArgument(!StringUtils.isEmpty(tagValue)," tagValue cannt be empty");

        log.debug("rulerParam is :{}", JSON.toJSONString(rulerParam));
        log.debug("dataParam is :{}", JSON.toJSONString(tagValue));


        boolean result =  false;
        if(Arrays.stream(rulerParam).anyMatch(tag -> tag.equals(tagValue))) {
            result = true;
        }

        AceResult checkTopicTagResult = new AceResult(result,null);
        log.debug("checkTopicTag ruler result :{}",JSON.toJSONString(checkTopicTagResult));
        return checkTopicTagResult;
    }

}