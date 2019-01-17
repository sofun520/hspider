package cn.heckman.moduleservice.exception;

/**
 * Created by heckman on 2018/2/9.
 */
public class FrameworkRuntimeException extends Exception{
    private String errorCode;
    private String errorMsg;
    private Object[] ctxParams;

//    public FrameworkRuntimeException(AllErrors error){
//        super(error.toString());
//        this.errorCode = error.getErrorCode();
//        this.errorMsg = error.getErrorMsg();
//
//    }

    public void params(Object ... params){
        this.ctxParams = params;
    }

}
