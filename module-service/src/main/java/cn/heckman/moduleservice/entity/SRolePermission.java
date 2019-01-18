package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by heckman on 2018/6/30.
 */
@Entity
@Where(clause = "deleted=0")
@Table(schema = "demo", name = "tb_role_permission")
public class SRolePermission extends IdEntity {

    /**
     * `role_id` varchar(32) DEFAULT NULL,
     * `resource_id` varchar(32) DEFAULT NULL,
     */

    private Role role;
    private SResource resource;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public Role getRole() {
        return role;
    }

    @ManyToOne
    @JoinColumn(name = "resource_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public SResource getResource() {
        return resource;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setResource(SResource resource) {
        this.resource = resource;
    }
}
