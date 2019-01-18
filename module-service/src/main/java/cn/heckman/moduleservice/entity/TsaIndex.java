package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "wsa", name = "tb_tsa_index")
public class TsaIndex extends IdEntity {

    /**
     * `title` varchar(50) DEFAULT NULL,
     * `describe` varchar(50) DEFAULT NULL,
     * `image` varchar(150) DEFAULT '',
     * `location` int(1) DEFAULT NULL COMMENT '类型（1：顶部图片；2：中部4X2菜单；3：中部广告图片；4：底部2X3菜单）',
     * `display_type` int(1) DEFAULT NULL COMMENT '展示类型（1：列表展示；2：单张图片展示；3：轮播图展示）',
     * `tenant_id` varchar(32) DEFAULT NULL,
     */

    private String title;
    private String describe;
    private String image;
    private int location;
    private int displayType;
    private Tenant tenant;

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "describe")
    public String getDescribe() {
        return describe;
    }

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    @Column(name = "location")
    public int getLocation() {
        return location;
    }

    @Column(name = "display_type")
    public int getDisplayType() {
        return displayType;
    }

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public Tenant getTenant() {
        return tenant;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
