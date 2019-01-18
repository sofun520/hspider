package cn.heckman.console.controller;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.DataDictionary;
import cn.heckman.moduleservice.service.DataDictionaryService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by heckman on 2018/6/30.
 */
@RestController
@RequestMapping("/api/data_dictionary")
@SuppressWarnings("all")
public class DataDictionaryController extends AbstractPortalController {

    private Logger logger = LoggerFactory.getLogger(DataDictionaryController.class);

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public RestResponse list(String code,
                             @RequestParam(defaultValue = "1") Integer pageNo,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        RestResponse restResponse = new RestResponse();
        Map<String, Object> param = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(code)) {
                param.put("code", code);
            }
            Page page = dataDictionaryService.queryPage(param, pageNo, pageSize);
            restResponse.setData(page);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResponse save(@RequestBody JSONObject obj) {
        RestResponse restResponse = new RestResponse();
        try {
            DataDictionary dataDictionary = buildPOInJsonObject(obj, dataDictionaryService);
            if (StringUtils.isNotBlank(dataDictionary.getId())) {
                dataDictionary.setLastTime(new Date());
            }
            dataDictionaryService.save(dataDictionary);
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
            DataDictionary dataDictionary = dataDictionaryService.findById(id);
            restResponse.setData(dataDictionary);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
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
            dataDictionaryService.updateValuesByArrays(new String[]{"deleted", "lastTime"}, new Object[]{1, nowStr}, "id", idArray);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

}
