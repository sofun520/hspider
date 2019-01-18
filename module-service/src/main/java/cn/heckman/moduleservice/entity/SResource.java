package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by heckman on 2018/6/30.
 */
@Entity
@Where(clause = "deleted=0")
@Table(schema = "demo", name = "tb_resource")
public class SResource extends IdEntity {

    /**
     * `title` varchar(50) NOT NULL,
     * `path` varchar(100) DEFAULT NULL,
     * `icon` varchar(50) DEFAULT NULL,
     * `component` varchar(50) DEFAULT NULL,
     * `parent_id` varchar(32) DEFAULT NULL,
     * `resource_type` int(1) NOT NULL COMMENT '资源名称  1：一级菜单；2：二级菜单；3：权限资源',
     */

    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    public static final int LEVEL_THREE = 3;

    private String name;
    private String title;
    private String path;
    private String icon;
    private String component;
    private String parentId;
    private Integer resourceType;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "path")
    public String getPath() {
        return path;
    }

    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    @Column(name = "component")
    public String getComponent() {
        return component;
    }

    @Column(name = "parent_id")
    public String getParentId() {
        return parentId;
    }

    @Column(name = "resource_type")
    public Integer getResourceType() {
        return resourceType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }
}
