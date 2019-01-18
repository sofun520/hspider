package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "wsa", name = "tb_user")
public class User extends IdEntity {

    /**
     * `username` varchar(50) DEFAULT NULL,
     * `password` varchar(50) DEFAULT NULL,
     * `telephone` varchar(20) DEFAULT NULL,
     * `email` varchar(50) DEFAULT NULL,
     * `role_id` varchar(32) DEFAULT NULL,
     * `tenant_id` varchar(32) DEFAULT NULL COMMENT '所属租户id',
     */

    private String username;
    private String password;
    private String telephone;
    private String email;
    private Role role;
    private Tenant tenant;
    private String nickname;

    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public Role getRole() {
        return role;
    }

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public Tenant getTenant() {
        return tenant;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
