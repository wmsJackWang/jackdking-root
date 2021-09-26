package ace.classifier;

import ace.annoation.Classifier;
import ace.annoation.Ruler;
import ace.core.AceContext;
import ace.core.AceResult;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName RefundOrderClassifier
 * @Description TODO
 * @Author jackdking
 * @Date 24/09/2021 5:05 下午
 * @Version 2.0
 **/

@Slf4j
@Classifier(name="refundOrderClassifier" ,
        matcher = { @Ruler(name = "refundRuler",value = {"2"})},
        filter  = {})
public class RefundOrderClassifier  implements IClassifier{
    @Override
    public AceResult classify(AceContext aceContext) {
        log.debug("execute refundOrderClassifier ");
        List<String> executorList = ImmutableList.of("RefundOrderExecutor");
        AceResult aceResult = AceResult.success();
        aceResult.setResult(executorList);
        //返回要生成的IExecutor集合
        return aceResult;
    }
}
