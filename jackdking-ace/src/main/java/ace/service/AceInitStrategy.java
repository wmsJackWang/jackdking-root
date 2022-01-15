package ace.service;

import ace.annoation.Ruler;
import ace.enums.ErrorMessageCode;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public interface AceInitStrategy {

    public void parseAnnoation();
    public void parseAttributor();
    public void parseClassifier();
    public void parseExecutor();

    default  void validateLegalParam(String classifierName, Ruler[] matchers, Ruler[] filters, String priority) {

        boolean isAtomicClassifier = !ObjectUtils.isEmpty(matchers) || !ObjectUtils.isEmpty(filters);
        boolean isNestClassifier = !StringUtils.isEmpty(priority);
        Assert.isTrue(isAtomicClassifier^isNestClassifier, ErrorMessageCode.CLASSIFIER_ILLEGAL.retCheckMessage(classifierName));
    }
}
