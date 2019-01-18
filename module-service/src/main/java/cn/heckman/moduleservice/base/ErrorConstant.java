package cn.heckman.moduleservice.base;

/**
 * @author liqiang
 * @date 2017/11/17
 */
public enum ErrorConstant {

    INNER_ERROR("1001001", "内部程序错误"),
    USER_NOT_EXIST("1001002", "不存在当前用户名"),
    REQUEST_TIMEOUT("1001003", "请求超出有效期"),
    REQUEST_PARAM_ERROR("1001004", "用户登录异常"),
    NOT_LOGIN_EXPT("1001005", "用户未登录或登录超时"),
    USERNAME_PASSWORD_NOT_CORRECT("1001006", "用户名或密码错误"),
    USERNAME_OR_PASSWORD_NULL("1001007", "用户名或密码为空"),

    TOKEN_NULL("1004001", "token不能为空"),
    TOKEN_ERROR("1004002", "token校验不正确，请重新登录"),
    TENANTCODE_OR_OPENID_NULL("1004003", "tenantCode或者openid为空");

    public static String INNER_ERROR_CODE = "1001001";
    public static String USER_NOT_EXIST_CODE = "1001002";
    public static String REQUEST_TIMEOUT_CODE = "1001003";
    public static String REQUEST_PARAM_ERROR_CODE = "1001004";
    public static String NOT_LOGIN_EXPT_CODE = "1001005";
    public static String USERNAME_PASSWORD_NOT_CORRECT_CODE = "1001006";
    public static String USERNAME_OR_PASSWORD_NULL_CODE = "1001007";
    public static String TOKEN_NULL_CODE = "1004001";
    public static String TOKEN_ERROR_CODE = "1004002";
    public static String TENANTCODE_OR_OPENID_NULL_CODE = "1004003";


    private String errCode;
    private String errMsg;


    ErrorConstant(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public static String getErrMsg(String errCode) {
        for (ErrorConstant errorConstant : ErrorConstant.values()) {
            if (errCode.equals(errorConstant.getErrCode())) {
                return errorConstant.getErrMsg();
            }
        }
        return INNER_ERROR.getErrMsg();
    }

}
