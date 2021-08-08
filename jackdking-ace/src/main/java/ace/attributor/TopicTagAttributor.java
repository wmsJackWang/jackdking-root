package ace.attributor;


import ace.AceContext;
import ace.AceResult;
import ace.Constants;
import ace.annoation.Attributor;
import ace.annoation.Ruler;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/*
 * 消息tag校验器
 * 该校验器，检测消息的tag。
 */
@Attributor(name = "messageTagAttributor" , fieldName = "tag")
@Slf4j
public class TopicTagAttributor implements IAttributor {

    @Ruler(name = "checkTopicTag")
    public AceResult attribute(AceContext<Map<String,Object>>  aceContext) {
        Preconditions.checkNotNull(aceContext,"TopicTagAttributor cannt be null");
        Preconditions.checkNotNull(aceContext.getDataParam(),"data of aceContext cannt be null");
        Map<String , Object> contextDataParam = Optional.ofNullable(aceContext.getDataParam()).orElse(Collections.emptyMap());
        Object aceContextTag = contextDataParam.get(Constants.ACE_CONTEXT_PARAM);
        Object tagValue = contextDataParam.get(Constants.TAG);

        Preconditions.checkArgument(!StringUtils.isEmpty(aceContextTag),"classifer defined aceContextTag cannt be empty");
        Preconditions.checkArgument(!StringUtils.isEmpty(tagValue)," tagValue cannt be empty");

        boolean result =  false;
        if(SPLITTER.splitToList(String.valueOf(aceContextTag)).contains(tagValue)) {
            result = true;
        }

        AceResult checkTopicTagResult = new AceResult(result,null);

        return checkTopicTagResult;
    }

}