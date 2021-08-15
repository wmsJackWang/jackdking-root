package ace.utils;

import ace.constant.Constants;
import ace.core.AceResult;

import java.util.List;

public class AceUtil {

    public static String getClassifierRulerName (String classifierName, String rulerName, String matcher) {
        return classifierName+ Constants.SPLITER+rulerName+matcher;
    }

    public static AceResult aggregateResult (List<AceResult> aceResults) {
        AceResult retResult = new AceResult(true,null);
        for(AceResult aceResult:aceResults) {
            retResult = retResult.and(aceResult);
        }
        return retResult;
    }

    public static AceResult aggregateResult (AceResult aceresult , AceResult... aceResults) {
        AceResult retResult = new AceResult(true,null);
        for(AceResult aceResult:aceResults) {
            retResult = retResult.and(aceResult);
        }
        return retResult;
    }

    public static AceResult aggregateResult (List<AceResult> aceResults , String aggregateRuler) {
        return null;
    }

    public static List<AceResult> negativeAceResultList(List<AceResult> aceResults){

        aceResults.stream().forEach(aceResult -> aceResult.negative());
        return aceResults;
    }
}
