package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.modulecommon.utils.JWTUtils;
import cn.heckman.modulecommon.utils.MD5Util;
import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.dao.UserDao;
import cn.heckman.moduleservice.entity.User;
import cn.heckman.moduleservice.service.UserService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public BaseDaoInterface<User, Serializable> getDao() {
        return userDao;
    }

    @Override
    public RestResponse login(String username, String password) throws Exception {
        RestResponse restResponse = new RestResponse();
        String hql = "from User obj ";
        hql = HqlUtil.addCondition(hql, "username", username);
        //hql = HqlUtil.addCondition(hql, "tenant.tenantCode", tenantCode);
        List<User> list = getList(hql);
        if (list.size() > 0) {
            hql = HqlUtil.addCondition(hql, "password", MD5Util.md5(password));
            list = getList(hql);
            if (list.size() > 0) {
                restResponse.setSuccess(true);
                User user = list.get(0);
                String tenantCode = null == user.getTenant() ? "NULL" : user.getTenant().getTenantCode();

                long timestamp = System.currentTimeMillis();

                Map<String, String> params = new HashMap();
                params.put("timestamp", String.valueOf(timestamp));
                params.put("userId", user.getId());
                params.put("roleId", null == user.getRole() ? "" : user.getRole().getId());
                params.put("username", user.getUsername());
                params.put("tenantId", null == user.getTenant() ? "" : user.getTenant().getId());
                params.put("tenantCode", null == user.getTenant() ? "" : user.getTenant().getTenantCode());

                String token = JWTUtils.createToken(params);

                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("username", user.getUsername());
                userInfo.put("telephone", user.getTelephone());
                userInfo.put("email", user.getEmail());
                userInfo.put("nickname", user.getNickname());

                Map<String, Object> ret = new HashMap<>();
                ret.put("token", token);
                ret.put("timestamp", String.valueOf(timestamp));
                ret.put("userInfo", userInfo);
                restResponse.setData(ret);
            } else {
                restResponse.setErrorCode(ErrorConstant.USERNAME_PASSWORD_NOT_CORRECT_CODE);
                restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.USERNAME_PASSWORD_NOT_CORRECT_CODE));
            }
        } else {
            restResponse.setErrorCode(ErrorConstant.USER_NOT_EXIST_CODE);
            restResponse.setErrorMsg(ErrorConstant.getErrMsg(ErrorConstant.USER_NOT_EXIST_CODE));
        }
        return restResponse;
    }
}
