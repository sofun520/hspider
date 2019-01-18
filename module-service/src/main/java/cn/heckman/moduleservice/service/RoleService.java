package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.PermissionTreeVo;
import cn.heckman.moduleservice.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {

    Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;

    List getPermissionList(String roleId) throws Exception;

    List getPermissionByRoleIdType(String roleId, int resourceType);

    List querySetting(String roleId) throws Exception;

    List<PermissionTreeVo> getList(List<PermissionTreeVo> parentList, List<PermissionTreeVo> childList)
            throws Exception;

    RestResponse saveRolePermission(net.sf.json.JSONObject object) throws Exception;

    void saveObj(Role role) throws Exception;

    RestResponse deleteRole(String[] idArray);

    Role getById(String id) throws Exception;
}
