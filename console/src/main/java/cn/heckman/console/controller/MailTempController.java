package cn.heckman.console.controller;

import cn.heckman.modulecommon.utils.ConstUtils;
import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.MailTemp;
import cn.heckman.moduleservice.service.MailTempService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mail_temp")
@SuppressWarnings("all")
public class MailTempController extends AbstractPortalController {

    private Logger logger = LoggerFactory.getLogger(SmsTempController.class);

    @Autowired
    private MailTempService mailTempService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public RestResponse page(@RequestParam(defaultValue = "1") Integer pageNo,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             String keyword, String temp) {
        RestResponse restResponse = new RestResponse();
        Map<String, Object> param = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(keyword)) {
                param.put("keyword", keyword);
            }
            if (StringUtils.isNotBlank(temp)) {
                param.put("temp", temp);
            }
            Page page = mailTempService.queryPage(param, pageNo, pageSize);
            restResponse.setSuccess(true);
            restResponse.setData(page);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResponse save(@RequestBody JSONObject obj) {
        RestResponse restResponse = new RestResponse();
        try {
            MailTemp mailTemp = buildPOInJsonObject(obj, mailTempService);
            if (StringUtils.isNotBlank(mailTemp.getId())) {
                //修改
                mailTemp.setLastTime(new Date());
            }
            mailTempService.save(mailTemp);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RestResponse delete(@RequestParam String ids) {
        RestResponse restResponse = new RestResponse();
        try {
            String[] idArray = null;
            if (ids.contains(",")) {
                idArray = ids.split(",");
            } else {
                idArray = new String[]{ids};
            }
            String nowStr = DateUtils.formatDate(new Date());
            mailTempService.updateValuesByArrays(new String[]{"deleted", "lastTime"}, new Object[]{1, nowStr}, "id", idArray);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public RestResponse find(@RequestParam String id) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(mailTempService.findById(id));
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public RestResponse send(@RequestParam String id, @RequestParam String address,
                             HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
            String from = ConstUtils.getStr("spring.mail.username");
//            getTokenMap(request).get("").asString();
            mailTempService.sendTempSms(id, address, from);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }


}
