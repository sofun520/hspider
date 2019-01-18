package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.SResourceDao;
import cn.heckman.moduleservice.entity.PermissionTreeVo;
import cn.heckman.moduleservice.entity.SResource;
import cn.heckman.moduleservice.service.SResourceService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class SResourceServiceImpl extends AbstractService<SResource> implements SResourceService {

    @Autowired
    private SResourceDao sResourceDao;

    @Override
    public BaseDaoInterface<SResource, Serializable> getDao() {
        return sResourceDao;
    }

    @Override
    public Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        String hql = "from SResource obj ";
        return pageList(hql, pageNo, pageSize);
    }

    @Override
    public List<PermissionTreeVo> queryPermissionTree() throws Exception {
        String sql = " SELECT  " +
                "  id, " +
                "  title as name, " +
                "  path, " +
                "  icon, " +
                "  component, " +
                "  resource_type AS resourceType, " +
                "  parent_id AS parentId  " +
                "FROM " +
                "  tb_resource  " +
                "WHERE resource_type = ? and deleted=0 ";
        List parentList = executNativeQuery(sql.replace("?", "1"), PermissionTreeVo.class);
        List childList = executNativeQuery(sql.replace("?", "2"), PermissionTreeVo.class);

        System.out.println(JSONObject.toJSONString(parentList));
        System.out.println(JSONObject.toJSONString(childList));

        return getList(parentList, childList);
    }

    public List<PermissionTreeVo> getList(List<PermissionTreeVo> parentList, List<PermissionTreeVo> childList)
            throws Exception {
        for (PermissionTreeVo pl : parentList) {
            for (PermissionTreeVo cl : childList) {
                if (pl.getId().equals(cl.getParentId()) && null != pl.getChildren()) {
                    pl.getChildren().add(cl);
                }
            }
        }
        return parentList;
    }

}
