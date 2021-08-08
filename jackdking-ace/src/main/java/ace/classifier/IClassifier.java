package ace.classifier;

import ace.AceContext;
import ace.AceResult;

public interface IClassifier{
    public AceResult classify(AceContext aceContext);
}
