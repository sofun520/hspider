package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "wsa", name = "tb_tenant")
public class Tenant extends IdEntity {

    /**
     * `tenant_name` varchar(50) DEFAULT NULL,
     * `tenant_code` varchar(50) DEFAULT NULL,
     * `tenant_introduce` text DEFAULT NULL,
     * `app_id` varchar(50) DEFAULT NULL,
     * `app_key` varchar(50) DEFAULT NULL,
     * `tenant_person` varchar(50) DEFAULT '' COMMENT '租户负责人',
     * `tenant_phone` varchar(20) DEFAULT '' COMMENT '租户手机号码',
     * `tenant_email` varchar(50) DEFAULT '' COMMENT '租户email',
     * `status` int(1) DEFAULT 1 COMMENT '租户状态（1：启用；0：禁用）',
     */

    private String tenantName;
    private String tenantCode;
    private String tenantIntroduce;
    private String appId;
    private String appKey;
    private String tenantPerson;
    private String tenantPhone;
    private String tenantEmail;
    private Integer status;

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "tenant_person")
    public String getTenantPerson() {
        return tenantPerson;
    }

    @Column(name = "tenant_phone")
    public String getTenantPhone() {
        return tenantPhone;
    }

    @Column(name = "tenant_email")
    public String getTenantEmail() {
        return tenantEmail;
    }

    public void setTenantPerson(String tenantPerson) {
        this.tenantPerson = tenantPerson;
    }

    public void setTenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    @Column(name = "tenant_name")
    public String getTenantName() {
        return tenantName;
    }

    @Column(name = "tenant_code")
    public String getTenantCode() {
        return tenantCode;
    }

    @Column(name = "tenant_introduce")
    public String getTenantIntroduce() {
        return tenantIntroduce;
    }

    @Column(name = "app_id")
    public String getAppId() {
        return appId;
    }

    @Column(name = "app_key")
    public String getAppKey() {
        return appKey;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public void setTenantIntroduce(String tenantIntroduce) {
        this.tenantIntroduce = tenantIntroduce;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
