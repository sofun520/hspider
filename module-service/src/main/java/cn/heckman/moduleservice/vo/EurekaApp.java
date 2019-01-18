package cn.heckman.moduleservice.vo;

public class EurekaApp {

    private String name;
    private String ipAddr;
    private String port;
    private String status;
    private String healthCheckUrl;


    public EurekaApp() {
    }

    public EurekaApp(String name, String ipAddr, String port, String status, String healthCheckUrl) {
        this.name = name;
        this.ipAddr = ipAddr;
        this.port = port;
        this.status = status;
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
