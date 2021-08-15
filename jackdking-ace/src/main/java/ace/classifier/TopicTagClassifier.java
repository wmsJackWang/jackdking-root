package ace.classifier;

import ace.core.AceContext;
import ace.core.AceResult;
import ace.annoation.Classifier;
import ace.annoation.Ruler;

@Classifier(name="orderTagClassifier" ,
        matcher = {@Ruler(name = "checkTopicTag",value = {"order_finsh"})},
        filter = {@Ruler(name="checkTopicTag",value={"order_cancel"})})
public class TopicTagClassifier implements IClassifier{

    //根据属性条件，返回要生成的IExecutor集合，分类器可能需要多个执行单元
    @Override
    public AceResult classify(AceContext aceContext) {


        //返回要生成的IExecutor集合
        return null;
    }
}
