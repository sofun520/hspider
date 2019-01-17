package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {

    Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;

    List getPermissionList(String roleId) throws Exception;

    List getPermissionByRoleIdType(String roleId, int resourceType);

    List querySetting(String roleId) throws Exception;

    Role getById(String id) throws Exception;
}
