package cn.heckman.moduleservice.entity;

/**
 * Created by heckman on 2018/7/1.
 */
public class SecurityUserVo {

    private String userId;
    private String username;
    private String nickname;
    private String roleId;
    private String employeeIdNumber;
    private String telephone;
    private String email;
    private String employeeId;

    private String tenantId;

    public SecurityUserVo() {
    }

    public SecurityUserVo(String userId, String username, String nickname, String roleId, String employeeIdNumber, String telephone, String email, String employeeId) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.roleId = roleId;
        this.employeeIdNumber = employeeIdNumber;
        this.telephone = telephone;
        this.email = email;
        this.employeeId = employeeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getEmployeeIdNumber() {
        return employeeIdNumber;
    }

    public void setEmployeeIdNumber(String employeeIdNumber) {
        this.employeeIdNumber = employeeIdNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
