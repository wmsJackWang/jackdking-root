package ace.classifier;

import ace.annoation.Classifier;
import ace.annoation.Ruler;
import ace.core.AceContext;
import ace.core.AceResult;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Classifier(name = "healthVoucherClassifier", matcher = {@Ruler(name = "checkTopicTag", value = {"health_topic"})}, filter = {})
@Slf4j
public class HealthVoucherClassifier implements IClassifier{
    @Override
    public AceResult classify(AceContext aceContext) {

        //从数据库中拉取 健康业务的 凭证类型list

        //代码写死，返回 健康业务的 凭证类型list


        log.debug("execute healthVoucherClassifier ");
//        refundOrderClassifier.testComponent();
        List<String> executorList = ImmutableList.of("ConsumerRefundOrderVoucherExecutor", "ConsumerOrderVoucherExecutor");
        AceResult aceResult = AceResult.success();
        aceResult.setResult(executorList);
        //返回要生成的IExecutor集合
        return aceResult;
    }
}
