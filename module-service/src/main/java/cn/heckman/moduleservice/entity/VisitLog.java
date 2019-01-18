package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "wsa", name = "tb_visit_log")
public class VisitLog extends IdEntity {

    /**
     * `resource` varchar(100) DEFAULT NULL,
     * username
     * tenant_id
     * platform_type
     */

    private String resource;
    private String username;
    private String tenantId;
    private int platformType;

    public VisitLog() {
    }

    public VisitLog(String resource, String username, String tenantId, int platformType) {
        this.resource = resource;
        this.username = username;
        this.tenantId = tenantId;
        this.platformType = platformType;
    }

    @Column(name = "tenant_id")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Column(name = "platform_type")
    public int getPlatformType() {
        return platformType;
    }

    public void setPlatformType(int platformType) {
        this.platformType = platformType;
    }

    @Column(name = "resource")
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
