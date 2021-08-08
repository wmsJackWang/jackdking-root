package ace.classifier;

import ace.AceContext;
import ace.AceResult;
import ace.annoation.Classifier;

@Classifier(name="orderTagClassifier" , matcher = "checkTopicTag",filter = "")
public class TopicTagClassifier implements IClassifier{

    //根据属性条件，返回要生成的IExecutor集合，分类器可能需要多个执行单元
    @Override
    public AceResult classify(AceContext aceContext) {
        return null;
    }
}
