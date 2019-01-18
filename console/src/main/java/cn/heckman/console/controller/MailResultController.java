package cn.heckman.console.controller;

import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.service.MailResultService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mail_result")
public class MailResultController {

    private Logger logger = LoggerFactory.getLogger(MailResultController.class);

    @Autowired
    private MailResultService mailResultService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public RestResponse page(@RequestParam(defaultValue = "1") Integer pageNo,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             String address, String content) {
        RestResponse restResponse = new RestResponse();
        Map<String, Object> param = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(address)) {
                param.put("address", address);
            }
            if (StringUtils.isNotBlank(content)) {
                param.put("content", content);
            }
            Page page = mailResultService.queryPage(param, pageNo, pageSize);
            restResponse.setSuccess(true);
            restResponse.setData(page);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

}
