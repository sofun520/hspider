package cn.heckman.moduleservice.base;

import com.auth0.jwt.interfaces.Claim;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author liqiang
 * @date 2017/11/17
 */
public abstract class AbstractPortalController {

    private Logger logger = LoggerFactory.getLogger(AbstractPortalController.class);

    public Map<String, Claim> getTokenMap(HttpServletRequest request) {
        return (Map<String, Claim>) request.getAttribute("map");
    }

    /**
     * 通过表单提交的数据转换为持久对象便于修改对象保存
     *
     * @param jsonObject
     * @param service
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    protected <T> T buildPOInJsonObject(JSONObject jsonObject, BaseService<T> service) throws IllegalAccessException, InstantiationException {
        T po;
        if (jsonObject.containsKey("id") && StringUtils.isNotEmpty(jsonObject.getString("id"))) {
            if (logger.isDebugEnabled()) {
                logger.debug("buildPOInJsonObject : {},service type is :{}", jsonObject, service.getRealClass());
            }
            po = (T) service.findById(jsonObject.getString("id"));
            if (logger.isDebugEnabled()) {
                logger.debug("found po is :{}:{}", po.getClass(), po.toString());
            }
        } else {
            po = service.getRealClass().newInstance();
            if (logger.isDebugEnabled()) {
                logger.debug("buildPO in new instance :{}:{}", service.getRealClass(), po);
            }
        }
        if (po != null) {
            copyDataToBean(jsonObject, po);
        }
        return po;
    }

    /**
     * 复制map值到指定对象
     *
     * @param parameterMap
     * @param po
     * @param
     */
    private void copyDataToBean(Map<String, String[]> parameterMap, Object po) {
        for (Object obj : parameterMap.keySet()) {
            try {
                BeanUtils.setProperty(po, obj.toString(), parameterMap.get(obj));
            } catch (Exception e) {
//                e.printStackTrace();
                logger.warn("copy data to bean error:" + e.getMessage());
            }
        }
    }


   /* protected SecurityUserVo currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (SecurityUserVo) session.getAttribute(SessionConstant.USER_SESSION);
    }*/

    /*protected SecurityUser currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (SecurityUser) session.getAttribute(SessionConstant.USER_SESSION);
    }

    @Autowired
    private UserService userService;

    protected User getUser(HttpServletRequest request) {
        SecurityUser securityUser = currentUser(request);
        return userService.find(securityUser.getUid());
    }

    protected String getTsaTenantId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(SessionConstant.TSA_TENANT_ID);
    }*/

}
