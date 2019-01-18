package cn.heckman.console.controller;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.moduleredis.service.RedisService;
import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.service.HomeStaticService;
import com.auth0.jwt.interfaces.Claim;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by heckman on 2018/10/1.
 */
@RestController
@RequestMapping("/api/home")
public class HomeStatisticController extends AbstractPortalController {

    private Logger logger = LoggerFactory.getLogger(HomeStatisticController.class);

    @Autowired
    private HomeStaticService homeStaticService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/api_visit_statistic", method = RequestMethod.GET)
    public RestResponse apiVisit(HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
//            Map<String, Claim> map = getTokenMap(request);
//            String tenantCode = map.get("tenantCode").asString();
//            String tenantId = map.get("tenantId").asString();
//            String lastMonday = DateUtils.getLastWeekMondayDate(new Date());
//            if (StringUtils.isNotBlank(tenantCode)) {
//                //租户
//                if (redisService.exists(tenantCode + ":" + lastMonday)) {
//                    //存在则从缓存取
//                    restResponse = (RestResponse) redisService.get(tenantCode + ":" + lastMonday);
//                } else {
//                    //不存在则查询数据库
//                    restResponse = homeStaticService.visitStatistic(tenantCode,tenantId);
//                }
//            } else {
//                //管理员
//                if (redisService.exists("admin:" + lastMonday)) {
//                    //存在则从缓存取
//                    restResponse = (RestResponse) redisService.get("admin:" + lastMonday);
//                } else {
//                    //不存在则查询数据库
//                    //不存在则查询数据库
//                    restResponse = homeStaticService.visitStatistic(null,null);
//                }
//            }
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }


}
