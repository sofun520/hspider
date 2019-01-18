package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "demo", name = "sms_gateway_account")
public class SmsGatewayAccount extends IdEntity {

    /**
     * name
     * `account` varchar(50) DEFAULT NULL,
     * `user_id` varchar(50) DEFAULT NULL,
     * `password` varchar(50) DEFAULT NULL,
     * `report_url` varchar(300) DEFAULT '',
     * `deliver_url` varchar(300) DEFAULT NULL,
     */

    private String name;
    private String account;
    private String userid;
    private String password;
    private String reportUrl;
    private String deliverUrl;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    @Column(name = "user_id")
    public String getUserid() {
        return userid;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Column(name = "report_url")
    public String getReportUrl() {
        return reportUrl;
    }

    @Column(name = "deliver_url")
    public String getDeliverUrl() {
        return deliverUrl;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public void setDeliverUrl(String deliverUrl) {
        this.deliverUrl = deliverUrl;
    }
}
