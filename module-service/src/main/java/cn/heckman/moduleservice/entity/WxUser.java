package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "wsa", name = "tb_wx_user")
public class WxUser extends IdEntity {

    /**
     * `nickname` varchar(50) NOT NULL DEFAULT '' COMMENT '用户昵称',
     * `avatar_url` text DEFAULT NULL COMMENT '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表132*132正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。',
     * `gender` varchar(10) DEFAULT NULL COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
     * `city` varchar(50) DEFAULT '' COMMENT '用户所在城市',
     * `province` varchar(50) DEFAULT '' COMMENT '用户所在省份',
     * `country` varchar(50) DEFAULT '' COMMENT '用户所在国家',
     * `language` varchar(50) DEFAULT NULL COMMENT '用户的语言，简体中文为zh_CN',
     * `open_id` varchar(50) DEFAULT NULL,
     * `union_id` varchar(50) DEFAULT NULL,
     * `tenant_id` varchar(32) DEFAULT '' COMMENT '用户id',
     */

    private String nockname;
    private String avatarUrl;
    private String gender;
    private String city;
    private String province;
    private String country;
    private String language;
    private String openId;
    private String unionId;
    private Tenant tenant;

    public WxUser() {
    }

    public WxUser(String nockname, String avatarUrl, String gender, String city, String province, String country, String language, String openId, String unionId, Tenant tenant) {
        this.nockname = nockname;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.city = city;
        this.province = province;
        this.country = country;
        this.language = language;
        this.openId = openId;
        this.unionId = unionId;
        this.tenant = tenant;
    }

    @Column(name = "nickname")
    public String getNockname() {
        return nockname;
    }

    @Column(name = "avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    @Column(name = "language")
    public String getLanguage() {
        return language;
    }

    @Column(name = "open_id")
    public String getOpenId() {
        return openId;
    }

    @Column(name = "union_id")
    public String getUnionId() {
        return unionId;
    }

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public Tenant getTenant() {
        return tenant;
    }

    public void setNockname(String nockname) {
        this.nockname = nockname;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
