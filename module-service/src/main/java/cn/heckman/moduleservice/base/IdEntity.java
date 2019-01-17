package cn.heckman.moduleservice.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liqiang
 * @date 2017/11/17
 */
@MappedSuperclass
public abstract class IdEntity implements Serializable {
    private String id;
    private Date createTime = new Date();
    private Date lastTime = new Date();
    private Long sortno = 0L;
    private boolean deleted;
    private Integer version = 0;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    /*@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")*/
    public String getId() {
        return id;
    }

    @Column(name = "create_dt")
    public Date getCreateTime() {
        return createTime;
    }

    @Column(name = "last_modi_dt")
    public Date getLastTime() {
        return lastTime;
    }

    @Column(name = "sortno")
    public Long getSortno() {
        return sortno;
    }

    @Column(name = "deleted")
    @JsonIgnore
    public boolean getDeleted() {
        return deleted;
    }

    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public void setSortno(Long sortno) {
        this.sortno = sortno;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
