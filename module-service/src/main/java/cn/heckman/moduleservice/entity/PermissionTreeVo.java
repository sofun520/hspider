package cn.heckman.moduleservice.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heckman on 2018/7/3.
 */
public class PermissionTreeVo {

    private String id;
    private String checkedFlag;
    private boolean checked;
    private String parentId;
    private String name;

    private String path;
    private String icon;
    private String component;
    private Integer resourceType;
    private List<PermissionTreeVo> children = new ArrayList<>();

    public List<PermissionTreeVo> getChildren() {
        return children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckedFlag() {
        return checkedFlag;
    }

    public void setCheckedFlag(String checkedFlag) {
        this.checkedFlag = checkedFlag;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public void setChildren(List<PermissionTreeVo> children) {
        this.children = children;
    }
}
