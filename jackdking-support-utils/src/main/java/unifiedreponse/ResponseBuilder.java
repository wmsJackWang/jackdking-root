package unifiedreponse;

public class ResponseBuilder {
    /**
     * 带实体的统一返回
     *
     * @param data 实体
     * @param <T>  实体类型
     * @return
     */
    public static <T> ResponseResult buildSuccess(T data) {
        return new ResponseResult<T>(ResultEnums.SUCCESS, data);
    }

    public static ResponseResult buildSuccess() {
        return new ResponseResult(ResultEnums.SUCCESS);
    }

    public static ResponseResult buildSuccess(String msg) {
        return new ResponseResult(ResultEnums.SUCCESS.getCode(), msg);
    }

    public static ResponseResult buildSuccess(String code, String msg) {
        return new ResponseResult(code, msg);
    }

    public static <T> ResponseResult buildSuccess(String code, String msg, T data) {
        return new ResponseResult<T>(code, msg, data);
    }

    public static ResponseResult buildSuccess(ResultEnums resultEnums) {
        return new ResponseResult(resultEnums);
    }

    public static <T> ResponseResult buildError(T data) {
        return new ResponseResult<T>(ResultEnums.ERROR, data);
    }

    public static ResponseResult buildError() {
        return new ResponseResult(ResultEnums.ERROR);
    }

    public static ResponseResult buildError(String msg) {
        return new ResponseResult(ResultEnums.ERROR.getCode(), msg);
    }

    public static ResponseResult buildError(String code, String msg) {
        return new ResponseResult(code, msg);
    }

    public static <T> ResponseResult buildError(String code, String msg, T data) {
        return new ResponseResult<T>(code, msg, data);
    }

    public static ResponseResult buildError(ResultEnums resultEnums) {
        return new ResponseResult(resultEnums);
    }
}