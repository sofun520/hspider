package cn.heckman.console.controller;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.DataItem;
import cn.heckman.moduleservice.service.DataItemService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heckman on 2018/6/30.
 */
@SuppressWarnings("all")
@RequestMapping("/api/data_item")
@RestController
public class DataItemController extends AbstractPortalController {

    @Autowired
    private DataItemService dataItemService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public RestResponse list(@RequestParam(defaultValue = "1") Integer pageNo,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             String dictionaryId) {
        RestResponse restResponse = new RestResponse();
        Map<String, Object> param = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(dictionaryId)) {
                param.put("dictionaryId", dictionaryId);
            }
            Page page = dataItemService.queryPage(param, pageNo, pageSize);
            restResponse.setData(page);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RestResponse querySelect(@RequestParam String dictionaryId) {
        RestResponse restResponse = new RestResponse();
        Map<String, Object> param = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(dictionaryId)) {
                param.put("dictionaryId", dictionaryId);
            }
            List list = dataItemService.queryList(param);
            restResponse.setData(list);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResponse save(@RequestBody JSONObject obj) {
        RestResponse restResponse = new RestResponse();
        try {
            DataItem dataItem = buildPOInJsonObject(obj, dataItemService);
            if (StringUtils.isNotBlank(dataItem.getId())) {
                dataItem.setLastTime(new Date());
            }
            dataItemService.save(dataItem);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
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
            dataItemService.updateValuesByArrays(new String[]{"deleted", "lastTime"}, new Object[]{1, nowStr}, "id", idArray);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

}
