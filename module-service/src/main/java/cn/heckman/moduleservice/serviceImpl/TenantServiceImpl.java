package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.TenantDao;
import cn.heckman.moduleservice.entity.Tenant;
import cn.heckman.moduleservice.service.TenantService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class TenantServiceImpl extends AbstractService<Tenant> implements TenantService {

    @Autowired
    private TenantDao tenantDao;

    @Override
    public BaseDaoInterface<Tenant, Serializable> getDao() {
        return tenantDao;
    }

    @Override
    public Tenant getByTenantCode(String tenantCode) throws Exception {
        String hql = "from Tenant obj ";
        hql = HqlUtil.addCondition(hql, "tenantCode", tenantCode);
        List<Tenant> list = getList(hql);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Page queryPage(Integer pageNo, Integer pageSize, Map<String, Object> params) throws Exception {
        String hql = "from Tenant obj ";
        if (null != params.get("tenantName")) {
            hql = HqlUtil.addCondition(hql, "tenantName", params.get("tenantName").toString().trim(), HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING_LIKE, "");
        }
        return pageList(hql, pageNo, pageSize);
    }
}
