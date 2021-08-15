package ace.classifier;

import ace.core.AceContext;
import ace.core.AceResult;

public interface IClassifier{
    public AceResult classify(AceContext aceContext);
}
