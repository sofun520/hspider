package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "demo", name = "tb_sms_result")
public class SmsResult extends IdEntity {

    /**
     * `phone` varchar(20) DEFAULT NULL,
     * `message` varchar(255) DEFAULT NULL,
     * `task_id` varchar(20) DEFAULT '' COMMENT '运营商返回sid',
     * `result_code` varchar(10) DEFAULT '' COMMENT '提交状态',
     * `result_msg` varchar(100) DEFAULT '' COMMENT '提交结果',
     * `deliver_code` varchar(10) DEFAULT '' COMMENT '发送状态',
     * `deliver_msg` varchar(100) DEFAULT '' COMMENT '发送结果',
     */

    private String phone;
    private String message;
    private String taskId;
    private String resultCode;
    private String resultMsg;
    private String deliverCode;
    private String deliverMsg;

    public SmsResult() {
    }

    public SmsResult(String phone, String message, String taskId, String resultCode, String resultMsg) {
        this.phone = phone;
        this.message = message;
        this.taskId = taskId;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getDeliverCode() {
        return deliverCode;
    }

    public void setDeliverCode(String deliverCode) {
        this.deliverCode = deliverCode;
    }

    public String getDeliverMsg() {
        return deliverMsg;
    }

    public void setDeliverMsg(String deliverMsg) {
        this.deliverMsg = deliverMsg;
    }
}
