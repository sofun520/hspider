package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "wsa", name = "tb_role")
public class Role extends IdEntity {

    /**
     * `role_name` varchar(50) DEFAULT NULL,
     * `role_code` varchar(50) DEFAULT NULL,
     */

    private String roleName;
    private String roleCode;

    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    @Column(name = "role_code")
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
