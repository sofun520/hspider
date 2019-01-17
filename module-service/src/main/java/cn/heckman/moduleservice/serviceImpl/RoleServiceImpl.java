package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.RoleDao;
import cn.heckman.moduleservice.entity.Role;
import cn.heckman.moduleservice.service.RoleService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

//    @Autowired
//    private SRolePermissionService sRolePermissionService;

    @Override
    public BaseDaoInterface<Role, Serializable> getDao() {
        return roleDao;
    }

    @Override
    public Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        String hql = "from Role obj ";
        if (null != param.get("roleName")) {
            hql = HqlUtil.addCondition(hql, "roleName", param.get("roleName"));
        }
        if (null != param.get("roleCode")) {
            hql = HqlUtil.addCondition(hql, "roleCode", param.get("roleCode"));
        }
        return pageList(hql, pageNo, pageSize);
    }


    @Transactional(rollbackOn = Exception.class)
    public void saveObj(Role role) throws Exception {
        save(role);
    }


    @Override
    public Role getById(String id) throws Exception {
        String hql = "from Role obj ";
        hql = HqlUtil.addCondition(hql, "id", id);
        return findUnique(hql);
    }

    @Override
    public List getPermissionList(String roleId) throws Exception {
        return null;
    }

    @Override
    public List getPermissionByRoleIdType(String roleId, int resourceType) {
        return null;
    }

    @Override
    public List querySetting(String roleId) throws Exception {
        return null;
    }


}
