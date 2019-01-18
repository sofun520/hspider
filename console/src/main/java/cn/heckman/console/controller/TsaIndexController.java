package cn.heckman.console.controller;

import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.TsaIndex;
import cn.heckman.moduleservice.service.TsaIndexService;
import com.auth0.jwt.interfaces.Claim;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/tsa_index")
public class TsaIndexController extends AbstractPortalController {

    private Logger logger = LoggerFactory.getLogger(TsaIndexController.class);

    @Autowired
    private TsaIndexService tsaIndexService;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public RestResponse show(HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
            Map<String, Claim> map = getTokenMap(request);
            String tenantCode = map.get("tenantCode").asString();

            restResponse = tsaIndexService.show(tenantCode);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse = RestResponse.failed(ErrorConstant.INNER_ERROR_CODE,
                    ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResponse save(@RequestBody JSONObject object) {
        RestResponse restResponse = new RestResponse();
        try {
            TsaIndex tsaIndex = buildPOInJsonObject(object, tsaIndexService);
            if (null != tsaIndex.getId()) {
                tsaIndex.setLastTime(new Date());
            }
            tsaIndexService.save(tsaIndex);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse = RestResponse.failed(ErrorConstant.INNER_ERROR_CODE,
                    ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RestResponse delete(@RequestParam String id) {
        RestResponse restResponse = new RestResponse();
        try {
            tsaIndexService.delete(id);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse = RestResponse.failed(ErrorConstant.INNER_ERROR_CODE,
                    ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }
}
