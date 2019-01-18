package cn.heckman.console.controller;

import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.VisitLog;
import cn.heckman.moduleservice.service.VisitLogService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/visit_log")
@SuppressWarnings("all")
public class VisitLogServiceController {

    private Logger logger = LoggerFactory.getLogger(VisitLogServiceController.class);

    @Autowired
    private VisitLogService visitLogService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RestResponse list() {
        RestResponse restResponse = new RestResponse();
        try {
            List<VisitLog> list = visitLogService.getList("from VisitLog obj ");
            restResponse.setData(list);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public RestResponse page(@RequestParam(defaultValue = "1") Integer pageNo,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             HttpServletRequest request,
                             String username, String startTime, String endTime) {
        RestResponse restResponse = new RestResponse();
        try {
            Map<String, Object> map = Maps.newHashMap();
            if (StringUtils.isNotBlank(username)) {
                map.put("username", username);
            }
            if (StringUtils.isNotBlank(startTime)) {
                map.put("startTime", startTime);
            }
            if (StringUtils.isNotBlank(endTime)) {
                map.put("endTime", endTime);
            }
            Page<VisitLog> page = visitLogService.page(map, pageNo, pageSize);
            restResponse.setData(page);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

}
