package ace.enums;


public enum ErrorMessageCode {


    CLASSIFIER_MATCHERS_NULL("classifier_matchers_empty",001,"classifier matchers is empty"),
    CLASSIFIER_CHECK_NULL("classifier_check_null",002,"classifier is not exist"),
    CLASSIFIER_MATCH_BEYOND_ONE("classifier_match_beyond_one",003,"scene's classifier match count is beyond 1"),
    CLASSIFIER_MATCH_NOT_EXIST("classifier_match_not_exist",004,"scene's classifier match not exist");


    public String errorName;
    public Integer errorCode;
    public String message;

    ErrorMessageCode(String errorName,Integer errorCode,String message){

        this.errorName = errorName;
        this.errorCode = errorCode;
        this.message = message;
    }

    public String retCheckMessage(String name){

        return new StringBuilder().append("【")
                    .append(name)
                    .append("】")
                    .append(message).toString();
    }
}
