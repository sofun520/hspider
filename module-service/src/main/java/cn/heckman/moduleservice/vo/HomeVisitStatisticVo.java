package cn.heckman.moduleservice.vo;

import java.math.BigInteger;

/**
 * Created by heckman on 2018/10/1.
 */
public class HomeVisitStatisticVo {

    private String tenantId;
    private String tenantName;
    private BigInteger total;
    private String Date;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
