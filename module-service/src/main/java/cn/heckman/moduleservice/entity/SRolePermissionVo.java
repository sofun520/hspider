package cn.heckman.moduleservice.entity;

import java.util.List;

/**
 * Created by heckman on 2018/7/1.
 */
public class SRolePermissionVo {

    private String id;
    private String title;
    private String path;
    private String icon;
    private String component;
    private String name;
    private List<SRolePermissionVo> children;
    private List<SRolePermissionVo> permission;
    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SRolePermissionVo> getChildren() {
        return children;
    }

    public void setChildren(List<SRolePermissionVo> children) {
        this.children = children;
    }

    public List<SRolePermissionVo> getPermission() {
        return permission;
    }

    public void setPermission(List<SRolePermissionVo> permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
