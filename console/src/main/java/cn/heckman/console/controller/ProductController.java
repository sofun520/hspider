package cn.heckman.console.controller;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.Product;
import cn.heckman.moduleservice.entity.Tenant;
import cn.heckman.moduleservice.entity.User;
import cn.heckman.moduleservice.service.ProductService;
import cn.heckman.moduleservice.service.TenantService;
import cn.heckman.moduleservice.service.UserService;
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
import java.util.Map;

/**
 * Created by heckman on 2018/7/28.
 */
@RestController
@RequestMapping("/api/product")
@SuppressWarnings("all")
public class ProductController extends AbstractPortalController {

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResponse save(@RequestBody JSONObject obj,
                             HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
//            String tenantCode = (String) request.getAttribute("tenantCode");
//            String userId = (String) request.getAttribute("userId");

            Map<String, Claim> map = getTokenMap(request);
            String tenantCode = map.get("tenantCode").asString();
            String userId = map.get("userId").asString();
            User user = userService.findById(userId);

            Product pro = buildPOInJsonObject(obj, productService);
            if (StringUtils.isNotBlank(pro.getId())) {
                pro.setLastTime(new Date());
            }

            Tenant tenant = user.getTenant();
            pro.setTenant(tenant);

            productService.save(pro);
            restResponse.setSuccess(true);

//            ElasticSearchUtils esu = new ElasticSearchUtils();
//            esu.put("product", "product", pro.getId(), com.alibaba.fastjson.JSONObject.toJSONString(pro), Maps.newHashMap());
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public RestResponse pageList(@RequestParam(defaultValue = "1") Integer pageNo,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 String productName,
                                 HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        Map<String, Object> params = new HashMap<>();
        try {
            Map<String, Claim> map = getTokenMap(request);
            String tenantCode = map.get("tenantCode").asString();

//            String tenantCode = (String) request.getAttribute("tenantCode");
            params.put("tenantCode", tenantCode);

            params.put("productName", productName);
            Page page = productService.queryPage(pageNo, pageSize, params);
            restResponse.setData(page);
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
            productService.updateValuesByArrays(new String[]{"deleted", "lastTime"}, new Object[]{1, nowStr}, "id", idArray);
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
            restResponse.setData(productService.findById(id));
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

}
