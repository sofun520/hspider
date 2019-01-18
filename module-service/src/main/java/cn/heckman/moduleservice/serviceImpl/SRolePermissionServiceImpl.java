package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.dao.SRolePermissionDao;
import cn.heckman.moduleservice.entity.SRolePermission;
import cn.heckman.moduleservice.service.SRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by heckman on 2018/7/8.
 */
@Service
public class SRolePermissionServiceImpl extends AbstractService<SRolePermission> implements SRolePermissionService {

    @Autowired
    private SRolePermissionDao sRolePermissionDao;

    @Override
    public BaseDaoInterface<SRolePermission, Serializable> getDao() {
        return sRolePermissionDao;
    }
}
