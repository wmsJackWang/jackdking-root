package ace.attributor;

import ace.core.AceAttributorCheckManager;
import ace.core.AceContext;
import ace.core.AceResult;
import ace.constant.Constants;
import ace.annoation.Attributor;
import ace.annoation.Ruler;
import ace.core.api.AttributorMatcher;
import ace.core.api.CommonAttributorMatcher;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;

/*
 * 消息tag校验器
 * 该校验器，检测消息的tag。
 */
@Attributor(name = "messageTagAttributor" , fieldName = "tag")
@Slf4j
@Data
public class TopicTagAttributor implements IAttributor {

    @Ruler(name = "checkTopicTagLegal")
    public AceResult checkTopicTagLegal(AceContext<Map<String,Object>, String[],Object> aceContext) {
        log.debug("begin to execute ruler {}","checkTopicTagLegal");

        boolean result = AceAttributorCheckManager.check(aceContext, new AttributorMatcher<Map<String,Object>, String[],Object>() {
            @Override
            public boolean bizCheck(AceContext<Map<String,Object>, String[],Object> aceContext) {

                Map<String , Object> contextDataParam = aceContext.getDataParam();
                Object tagValue = contextDataParam.get(Constants.TAG);
                String[] rulerParam = aceContext.getRulerParam();

                Preconditions.checkArgument(!StringUtils.isEmpty(rulerParam),"classifer defined rulerParam cannt be empty");
                Preconditions.checkArgument(!StringUtils.isEmpty(tagValue)," tagValue cannt be empty");
                return true;
            }

            @Override
            public boolean isMatch(Map<String, Object> contextDataParam, String[] rulerParam) {

                Object tagValue = contextDataParam.get(Constants.TAG);
                if(Arrays.stream(rulerParam).anyMatch(tag -> tag.equals(tagValue))) {
                    return false;
                }
                return true;
            }
        });

        AceResult checkTopicTagResult = new AceResult(result,null);
        log.debug("checkTopicTag ruler result :{}",JSON.toJSONString(checkTopicTagResult));
        return checkTopicTagResult;
    }

    @Ruler(name = "checkTopicTag")
    public AceResult checkTopicTag(AceContext<Map<String,Object>,String[],Object>  aceContext) {
        log.debug("begin to execute ruler {}","checkTopicTag");

        boolean result = AceAttributorCheckManager.check(aceContext, new CommonAttributorMatcher<Map<String,Object>, String[],Object>() {

            @Override
            public boolean isMatch(Map<String, Object> contextDataParam, String[] rulerParam) {

                Object tagValue = contextDataParam.get(Constants.TAG);
                if(Arrays.stream(rulerParam).anyMatch(tag -> tag.equals(tagValue))) {
                    return true;
                }
                return false;
            }
        }, Constants.TAG);

        AceResult checkTopicTagResult = new AceResult(result,null);
        log.debug("checkTopicTag ruler result :{}",JSON.toJSONString(checkTopicTagResult));
        return checkTopicTagResult;
    }
}