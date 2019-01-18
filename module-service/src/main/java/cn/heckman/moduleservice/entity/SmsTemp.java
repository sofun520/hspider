package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "wsa", name = "tb_sms_temp")
public class SmsTemp extends IdEntity {

    /**
     * `temp_id` varchar(30) DEFAULT '' COMMENT '模板ID',
     * `temp_content` varchar(200) DEFAULT '' COMMENT '模板内容',
     * variables
     */

    private String tempId;
    private String tempContenmt;
    private String variables;

    @Column(name = "temp_id")
    public String getTempId() {
        return tempId;
    }

    @Column(name = "temp_content")
    public String getTempContenmt() {
        return tempContenmt;
    }

    @Column(name = "variables")
    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public void setTempContenmt(String tempContenmt) {
        this.tempContenmt = tempContenmt;
    }
}
