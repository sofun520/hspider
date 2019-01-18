package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "wsa", name = "tb_mail_result")
public class MailResult extends IdEntity {

    /**
     * `address` varchar(20) DEFAULT '',
     * `user_id` varchar(32) DEFAULT NULL,
     * `content` text DEFAULT NULL,
     */

    private String address;
    private String userId;
    private String content;

    public MailResult() {
    }

    public MailResult(String address, String userId, String content) {
        this.address = address;
        this.userId = userId;
        this.content = content;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
