package cn.heckman.console.controller;

import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.Role;
import cn.heckman.moduleservice.service.RoleService;
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
 * Created by heckman on 2018/6/30.
 */
@RestController
@RequestMapping("/api/role")
//@Api("role")
@SuppressWarnings("all")
public class RoleController extends AbstractPortalController {

    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

//    @ApiOperation(value = "查询角色信息", notes = "查询角色分页信息")
//    @ApiImplicitParams({@ApiImplicitParam(name = "pageNo", value = "页码", paramType = "query", required = true, dataType = "integer"),
//            @ApiImplicitParam(name = "pageSize", value = "每页展示条数", paramType = "query", required = true, dataType = "integer"),
//            @ApiImplicitParam(name = "roleName", value = "角色名称", paramType = "query", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "roleCode", value = "角色code", paramType = "query", required = false, dataType = "String")
//    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public RestResponse page(@RequestParam(defaultValue = "1") Integer pageNo,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             String roleName, String roleCode) {
        RestResponse restResponse = new RestResponse();
        Map<String, Object> param = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(roleName)) {
                param.put("roleName", roleName);
            }
            if (StringUtils.isNotBlank(roleCode)) {
                param.put("roleCode", roleCode);
            }
            Page page = roleService.queryPage(param, pageNo, pageSize);
            restResponse.setSuccess(true);
            restResponse.setData(page);
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

            Role role = buildPOInJsonObject(obj, roleService);

            if (StringUtils.isNotBlank(role.getId())) {
                role.setLastTime(new Date());
            }
            roleService.saveObj(role);
            //roleService.save(role);
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
            Role role = roleService.getById(id);
            restResponse.setData(role);
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
            //String nowStr = DateUtils.formatDate(new Date());
            //roleService.updateValuesByArrays(new String[]{"deleted", "lastTime"}, new Object[]{1, nowStr}, "id", idArray);

            restResponse = roleService.deleteRole(idArray);


            //restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }


    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public RestResponse getPermission(@RequestParam(defaultValue = "") String roleId, HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
            Map<String, Claim> map = getTokenMap(request);
            roleId = map.get("roleId").asString();

            List list = roleService.getPermissionList(roleId);
            restResponse.setData(list);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/query_permission_tree", method = RequestMethod.GET)
    public RestResponse querySetting(@RequestParam(defaultValue = "") String roleId, HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
//            Map<String, Claim> map = getTokenMap(request);
//            roleId = map.get("roleId").asString();

            List list = roleService.querySetting(roleId);
            restResponse.setData(list);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping(value = "/save_role_permission", method = RequestMethod.POST)
    public RestResponse saveRolePermission(@RequestBody JSONObject object) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = roleService.saveRolePermission(object);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setErrorCode(ErrorConstant.INNER_ERROR_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }


}
