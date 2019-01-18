package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.modulecommon.utils.UUIDGenerator;
import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.dao.RoleDao;
import cn.heckman.moduleservice.entity.PermissionTreeVo;
import cn.heckman.moduleservice.entity.Role;
import cn.heckman.moduleservice.entity.SResource;
import cn.heckman.moduleservice.entity.SRolePermissionVo;
import cn.heckman.moduleservice.service.RoleService;
import cn.heckman.moduleservice.utils.HqlUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public List getPermissionList(String roleId) throws Exception {
        List baseList = getPermissionByRoleIdType(roleId, SResource.LEVEL_ONE);
        List list = getPermissionByRoleIdType(roleId, SResource.LEVEL_TWO);
        SRolePermissionVo baseVo = null;
        SRolePermissionVo childVo = null;
        for (int i = 0; i < baseList.size(); i++) {
            baseVo = (SRolePermissionVo) baseList.get(i);
            List childNowList = new ArrayList();
            for (int j = 0; j < list.size(); j++) {
                childVo = (SRolePermissionVo) list.get(j);
                if (baseVo.getId().equals(childVo.getParentId())) {
                    childNowList.add(childVo);
                }
            }
            baseVo.setChildren(childNowList);
        }
        System.out.println(JSONObject.toJSONString(baseList));
        return baseList;
    }

    public List getPermissionByRoleIdType(String roleId, int resourceType) {
        String sql = " SELECT " +
                " tr.id, " +
                " tr.title, " +
                " tr.path, " +
                " tr.icon, " +
                " tr.component, " +
                " tr.`name`, " +
                " tr.parent_id AS parentId " +
                "FROM " +
                " `tb_role_permission` trp, " +
                " tb_resource tr " +
                "WHERE " +
                " trp.resource_id = tr.id " +
                " AND trp.role_id = '" + roleId + "' " +
                " AND tr.resource_type =" + resourceType +
                " AND tr.deleted = 0 " +
                " AND trp.deleted = 0 " +
                " ORDER BY " +
                " tr.sortno ";
        List list = executNativeQuery(sql, SRolePermissionVo.class);
        return list;
    }

    @Override
    public List querySetting(String roleId) throws Exception {
        String sql = " SELECT  " +
                " t.id,  " +
                " t.title AS `name`,  " +
                " t.`parent_id` AS parentId,  " +
                " CASE  " +
                "WHEN tr.id IS NULL THEN  " +
                " '0'  " +
                "ELSE  " +
                " '1'  " +
                "END AS checkedFlag  " +
                "FROM  " +
                " tb_resource t  " +
                "LEFT OUTER JOIN (  " +
                " SELECT  " +
                "  tr.id  " +
                " FROM  " +
                "  tb_role_permission trp,  " +
                "  tb_resource tr  " +
                " WHERE  " +
                "  trp.resource_id = tr.id  " +
                " AND trp.role_id = '" + roleId + "'  " +
                " AND tr.resource_type = ?  " +
                ") tr ON t.id = tr.id  " +
                "WHERE  " +
                " t.resource_type = ? ";
        List parentList = executNativeQuery(sql.replace("?", "1"), PermissionTreeVo.class);
        List childList = executNativeQuery(sql.replace("?", "2"), PermissionTreeVo.class);
        return getList(parentList, childList);
    }

    public List<PermissionTreeVo> getList(List<PermissionTreeVo> parentList, List<PermissionTreeVo> childList)
            throws Exception {
        for (PermissionTreeVo pl : parentList) {
            for (PermissionTreeVo cl : childList) {
                if (pl.getId().equals(cl.getParentId())) {
                    pl.getChildren().add(cl);
                }
            }
        }
        return parentList;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RestResponse saveRolePermission(net.sf.json.JSONObject object) throws Exception {
        if (null != object.get("roleId") && StringUtils.isNotBlank(object.getString("roleId"))) {
            String roleId = object.getString("roleId");
            String sql = " DELETE FROM tb_role_permission WHERE role_id = '" + roleId + "' ";
            executeSql(sql);

            if (null != object.get("permissionId") && StringUtils.isNotBlank(object.getString("permissionId"))) {
                String permissionId = object.getString("permissionId");
                String[] permissionIds = permissionId.split(",");
                StringBuffer saveSql = new StringBuffer("");
                saveSql.append(" INSERT INTO `tb_role_permission` ( " +
                        "  `id`, " +
                        "  `role_id`, " +
                        "  `resource_id`, " +
                        "  `create_dt`, " +
                        "  `last_modi_dt`, " +
                        "  `sortno`, " +
                        "  `deleted`, " +
                        "  `version` " +
                        " )  " +
                        " VALUES ");
                for (int i = 0; i < permissionIds.length; i++) {
                    if (i == permissionIds.length - 1) {
                        saveSql.append(" ('" + UUIDGenerator.uuid() + "','" + roleId + "','" + permissionIds[i] + "',now(),now(),0,0,0) ");
                    } else {
                        saveSql.append(" ('" + UUIDGenerator.uuid() + "','" + roleId + "','" + permissionIds[i] + "',now(),now(),0,0,0), ");
                    }
                }
                System.out.println(saveSql.toString());
                executeSql(saveSql.toString());
            }
        }
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(true);
        return restResponse;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void saveObj(Role role) throws Exception {
        save(role);
    }


    @Transactional(rollbackOn = Exception.class)
    @Override
    public RestResponse deleteRole(String[] idArray) {
        RestResponse restResponse = new RestResponse();
        try {
            String nowStr = DateUtils.formatDate(new Date());
            updateValuesByArrays(new String[]{"deleted", "lastTime"}, new Object[]{1, nowStr}, "id", idArray);

//            sRolePermissionService.updateValuesByArrays(new String[]{"deleted", "lastTime"}, new Object[]{1, nowStr}, "role.id", idArray);

            restResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            restResponse.setSuccess(false);
        }
        return restResponse;
    }

    @Override
    public Role getById(String id) throws Exception {
        String hql = "from Role obj ";
        hql = HqlUtil.addCondition(hql, "id", id);
        return findUnique(hql);
    }
}
