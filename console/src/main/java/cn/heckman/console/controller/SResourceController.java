package cn.heckman.console.controller;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.PermissionTreeVo;
import cn.heckman.moduleservice.entity.SResource;
import cn.heckman.moduleservice.service.SResourceService;
import cn.heckman.moduleservice.utils.HqlUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heckman on 2018/6/30.
 */
@RequestMapping("/api/resource")
@RestController
@SuppressWarnings("all")
public class SResourceController extends AbstractPortalController {

    private Logger logger = LoggerFactory.getLogger(SResourceController.class);

    @Autowired
    private SResourceService resourceService;


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public RestResponse list(String code,
                             @RequestParam(defaultValue = "1") Integer pageNo,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        RestResponse restResponse = new RestResponse();
        Map<String, Object> param = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(code)) {
                param.put("code", code);
            }
            Page page = resourceService.queryPage(param, pageNo, pageSize);
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
            SResource resource = buildPOInJsonObject(obj, resourceService);
            if (StringUtils.isNotBlank(resource.getId())) {
                resource.setLastTime(new Date());
            }
            /*if (null != obj.get("parentId") && StringUtil.isNotEmpty(obj.getString("parentId"))) {
                //SResource pa = resourceService.findById(obj.getString("parentId"));
                resource.setParentId(obj.getString("parentId"));
            }*/
            resourceService.save(resource);
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
//            SResource resource = resourceService.findById(id);

            String hql = "from SResource obj";
            hql = HqlUtil.addCondition(hql, "id", id);
            List<SResource> list = resourceService.getList(hql);
            if (list.size() > 0) {
                restResponse.setData(list.get(0));
            } else {
                restResponse.setData(null);
            }
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
            resourceService.updateValuesByArrays(new String[]{"deleted", "lastTime"}, new Object[]{1, nowStr}, "id", idArray);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/permission_tree", method = RequestMethod.GET)
    public RestResponse permissionTree() {
        RestResponse restResponse = new RestResponse();
        try {
            List<PermissionTreeVo> list = resourceService.queryPermissionTree();
            restResponse.setData(list);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

}
