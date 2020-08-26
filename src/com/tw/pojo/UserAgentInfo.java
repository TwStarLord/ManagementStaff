package com.tw.pojo;

import java.io.Serializable;

public class UserAgentInfo implements Serializable {

    private Long id;
    private String name;
    private String recordtime;
    private String browser;
    private String os;
    private String ip;

    public UserAgentInfo() {
    }

    public UserAgentInfo(String name, String recordtime, String browser, String os, String ip) {
        this.name = name;
        this.recordtime = recordtime;
        this.browser = browser;
        this.os = os;
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "UserAgentInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recordtime='" + recordtime + '\'' +
                ", browser='" + browser + '\'' +
                ", os='" + os + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
