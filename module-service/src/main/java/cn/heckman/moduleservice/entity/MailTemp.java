package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "wsa", name = "tb_mail_temp")
public class MailTemp extends IdEntity {

    /**
     * `keyword` varchar(20) DEFAULT NULL,
     * `temp` text DEFAULT NULL,
     */

    private String keyword;
    private String temp;

    @Column(name = "keyword")
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    @Column(name = "temp")
    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
