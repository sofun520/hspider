package cn.heckman.console.controller;

import cn.heckman.moduleredis.service.RedisService;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.RestResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eureka")
public class EurekaAppController {

    private Logger logger = LoggerFactory.getLogger(EurekaAppController.class);

    @Autowired
    private RedisService redisService;

    @RequestMapping("/app")
    public RestResponse list() {
        RestResponse restResponse = new RestResponse();
        try {
            String result = (String) redisService.get("eureka_app");
            JSONArray obj = JSONObject.parseArray(result);
            restResponse.setSuccess(true);
            restResponse.setData(obj);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE,
                    ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }
}
