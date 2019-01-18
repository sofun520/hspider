package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "demo", name = "sms_gateway_test")
public class SmsGatewayTest extends IdEntity {

    /**
     * `task_id` varchar(32) DEFAULT NULL,
     * `phone` varchar(20) DEFAULT NULL,
     */

    public static int PADING_SEND = 1;//未发送
    public static int SENDED = 2;//已发送

    private String taskId;
    private String phone;
    private int status;
    private String account;

    public SmsGatewayTest() {
    }

    public SmsGatewayTest(String taskId, String phone, int status,String account) {
        this.taskId = taskId;
        this.phone = phone;
        this.status = status;
        this.account = account;
    }

    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "task_id")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
