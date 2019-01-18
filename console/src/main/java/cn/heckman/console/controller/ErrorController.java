package cn.heckman.console.controller;


import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {

    private Logger logger = LoggerFactory.getLogger(ErrorController.class);


    @RequestMapping(value = "/token_null", method = RequestMethod.GET)
    public RestResponse auth() {
        RestResponse restResponse = new RestResponse();
        restResponse.setErrorCode(ErrorConstant.TOKEN_NULL_CODE);
        restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.TOKEN_NULL_CODE));
        return restResponse;
    }

    @RequestMapping(value = "/token_error", method = RequestMethod.GET)
    public RestResponse tokenError() {
        RestResponse restResponse = new RestResponse();
        restResponse.setErrorCode(ErrorConstant.TOKEN_ERROR_CODE);
        restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.TOKEN_ERROR_CODE));
        return restResponse;
    }


}
