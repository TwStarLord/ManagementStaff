package com.tw.pojo;

import java.io.Serializable;

public class LogInfo implements Serializable {

    private Long id;
    private String account;
    private String ip;
    private String recordtime;
    private String operation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public LogInfo() {
    }

    public LogInfo(String account, String ip, String recordtime, String operation) {
        this.account = account;
        this.ip = ip;
        this.recordtime = recordtime;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "LogInfo{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", ip='" + ip + '\'' +
                ", recordtime='" + recordtime + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }
}
