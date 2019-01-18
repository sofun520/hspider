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
@Table(schema = "demo", name = "tb_data_dictionary")
public class DataDictionary extends IdEntity {

    /**
     * `code` varchar(50) DEFAULT NULL,
     * `description` varchar(100) DEFAULT NULL,
     */

    private String code;
    private String description;

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
