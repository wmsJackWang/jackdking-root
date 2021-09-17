package ace.classifier;

import ace.core.AceContext;
import ace.core.AceResult;
import ace.annoation.Classifier;
import ace.annoation.Ruler;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Classifier(name="orderTagClassifier" ,
            matcher = { @Ruler(name = "checkTopicTag",value = {"order_finsh","order_ready"}),
                        @Ruler(name = "checkTopicTagLegal",value = {"order_finsh","order_cancel"})},
            filter  = { @Ruler(name="checkTopicTag",value={"order_cancel"})})
@Slf4j
public class TopicTagClassifier implements IClassifier{

    //根据属性条件，返回要生成的IExecutor集合，分类器可能需要多个执行单元
    @Override
    public AceResult classify(AceContext aceContext) {

        log.debug("execute orderTagClassifier ");
        List<String> executorList = ImmutableList.of("TopicTagExecutor");
        AceResult aceResult = AceResult.success();
        aceResult.setResult(executorList);
        //返回要生成的IExecutor集合
        return aceResult;
    }
}
