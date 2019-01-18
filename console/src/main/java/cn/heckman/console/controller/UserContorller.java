package cn.heckman.console.controller;

import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.base.SessionConstant;
import cn.heckman.moduleservice.entity.User;
import cn.heckman.moduleservice.service.UserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@SuppressWarnings("all")
@RestController
public class UserContorller {

    private Logger logger = LoggerFactory.getLogger(UserContorller.class);

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResponse login(@RequestBody JSONObject params) {
        RestResponse restResponse = new RestResponse();
        try {
            if (null != params.get("username") && null != params.get("password")) {
                restResponse = userService.login(params.getString("username"),
                        params.getString("password"));
            } else {
                restResponse.setErrorCode(ErrorConstant.USERNAME_OR_PASSWORD_NULL_CODE);
                restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.USERNAME_OR_PASSWORD_NULL_CODE));
            }
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/api/user/list", method = RequestMethod.GET)
    public RestResponse list() {
        RestResponse restResponse = new RestResponse();
        try {
            List<User> list = userService.getList("from User obj ");
            restResponse.setSuccess(true);
            restResponse.setData(list);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public RestResponse logout(HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConstant.USER_SESSION, null);
            session.invalidate();
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

}
