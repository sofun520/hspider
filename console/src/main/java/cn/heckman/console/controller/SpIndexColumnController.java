package cn.heckman.console.controller;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.SpIndexColumn;
import cn.heckman.moduleservice.entity.Tenant;
import cn.heckman.moduleservice.service.SpIndexColumnService;
import cn.heckman.moduleservice.service.TenantService;
import cn.heckman.moduleservice.utils.HqlUtil;
import com.auth0.jwt.interfaces.Claim;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heckman on 2018/8/4.
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/api/index_column")
public class SpIndexColumnController extends AbstractPortalController {

    private Logger logger = LoggerFactory.getLogger(SpIndexColumnController.class);

    @Autowired
    private SpIndexColumnService spIndexColumnService;

    @Autowired
    private TenantService tenantService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public RestResponse pageList(@RequestParam(defaultValue = "1") Integer pageNo,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 String type,
                                 HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        Map<String, Object> params = new HashMap<>();
        try {
            Map<String, Claim> map = getTokenMap(request);
            String tenantCode = map.get("tenantCode").asString();
            String tenantId = map.get("tenantId").asString();

            params.put("tenantId", tenantId);
            params.put("type", type);
            Page page = spIndexColumnService.queryPage(pageNo, pageSize, params);
            restResponse.setData(page);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/wx/list", method = RequestMethod.GET)
    public RestResponse list(@RequestParam String type,
                             HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
            Map<String, Claim> map = getTokenMap(request);
            String tenantCode = map.get("tenantCode").asString();

            String hql = "from SpIndexColumn obj ";
            hql = HqlUtil.addCondition(hql, "tenant.tenantCode", tenantCode);
            hql = HqlUtil.addCondition(hql, "columnType", type);
            List list = spIndexColumnService.getList(hql);
            restResponse.setData(list);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            //logger.error("inner error", ex);
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
            spIndexColumnService.updateValuesByArrays(new String[]{"deleted", "lastTime"}, new Object[]{1, nowStr}, "id", idArray);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResponse save(@RequestBody JSONObject obj,
                             HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
            Map<String, Claim> map = getTokenMap(request);
            String tenantCode = map.get("tenantCode").asString();
            String tenantId = map.get("tenantId").asString();

            SpIndexColumn indexColumn = buildPOInJsonObject(obj, spIndexColumnService);
            if (StringUtils.isNotBlank(indexColumn.getId())) {
                indexColumn.setLastTime(new Date());
            }

            Tenant tenant = tenantService.findById(tenantId);
            indexColumn.setTenant(tenant);

            spIndexColumnService.save(indexColumn);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

}
