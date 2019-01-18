package cn.heckman.console.controller;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.modulecommon.utils.UUIDGenerator;
import cn.heckman.moduleredis.service.RedisService;
import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.SmsGatewayAccount;
import cn.heckman.moduleservice.service.SmsGatewayAccountService;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
@RestController
@RequestMapping("/api/gateway_account")
public class SmsGatewayAccountController extends AbstractPortalController {

    @Autowired
    private SmsGatewayAccountService smsGatewayAccountService;

    @Autowired
    private RedisService redisService;

    private Logger logger = LoggerFactory.getLogger(SmsGatewayAccountController.class);

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public RestResponse page(@RequestParam(defaultValue = "1") Integer pageNo,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             String account,
                             String userId,
                             String name) {
        RestResponse restResponse = new RestResponse();
        try {
            Map<String, String> map = new HashMap<>();
            if (StringUtils.isNotBlank(account)) {
                map.put("account", account);
            }
            if (StringUtils.isNotBlank(userId)) {
                map.put("userId", userId);
            }
            if (StringUtils.isNotBlank(name)) {
                map.put("name", name);
            }
            Page page = smsGatewayAccountService.queryPage(pageNo, pageSize, map);
            restResponse.setData(page);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            restResponse = RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResponse save(@RequestBody JSONObject obj) {
        RestResponse restResponse = new RestResponse();
        try {
            SmsGatewayAccount smsTemp = buildPOInJsonObject(obj, smsGatewayAccountService);
            if (StringUtils.isNotBlank(smsTemp.getId())) {
                //修改
                smsTemp.setLastTime(new Date());
            } else {
                if (!redisService.exists("sms.gateway.account")) {
                    redisService.set("sms.gateway.account", 50000);
                }
                long a = redisService.incr("sms.gateway.account", 1l);
                smsTemp.setUserid(a + "");
            }
            smsGatewayAccountService.save(smsTemp);
            redisService.set("gateway_user_" + smsTemp.getUserid(), JSON.toJSONString(smsTemp));
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
            smsGatewayAccountService.updateValuesByArrays(new String[]{"deleted", "lastTime"}, new Object[]{1, nowStr}, "id", idArray);
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
            restResponse.setData(smsGatewayAccountService.findById(id));
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/get_password", method = RequestMethod.GET)
    public RestResponse getPassword() {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(UUIDGenerator.uuid());
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/get_userId", method = RequestMethod.GET)
    public RestResponse getUserId() {
        RestResponse restResponse = new RestResponse();
        try {
            if (!redisService.exists("sms.gateway.account")) {
                redisService.set("sms.gateway.account", 50000);
            }
            long a = redisService.incr("sms.gateway.account", 1l);
            restResponse.setSuccess(true);
            restResponse.setData(a);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse = RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

}
