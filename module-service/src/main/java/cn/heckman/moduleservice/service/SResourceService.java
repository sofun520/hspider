package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.PermissionTreeVo;
import cn.heckman.moduleservice.entity.SResource;

import java.util.List;
import java.util.Map;

public interface SResourceService extends BaseService<SResource> {

    List<PermissionTreeVo> queryPermissionTree() throws Exception;

    Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;

    List<PermissionTreeVo> getList(List<PermissionTreeVo> parentList, List<PermissionTreeVo> childList)
            throws Exception;

}
