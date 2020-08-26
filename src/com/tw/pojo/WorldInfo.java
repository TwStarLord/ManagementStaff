package com.tw.pojo;

import java.io.Serializable;

public class WorldInfo implements Serializable {

    private Integer code;
    private String provinceName;
    private String time;
    private Integer currentConfirmedCount;
    private Integer confirmedCount;
    private Integer curedCount;
    private Integer deadCount;

    public WorldInfo() {
    }

    public WorldInfo(Integer code, String provinceName, String time, Integer currentConfirmedCount, Integer confirmedCount, Integer curedCount, Integer deadCount) {
        this.code = code;
        this.provinceName = provinceName;
        this.time = time;
        this.currentConfirmedCount = currentConfirmedCount;
        this.confirmedCount = confirmedCount;
        this.curedCount = curedCount;
        this.deadCount = deadCount;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCurrentConfirmedCount() {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(Integer currentConfirmedCount) {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public Integer getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(Integer confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public Integer getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(Integer curedCount) {
        this.curedCount = curedCount;
    }

    public Integer getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(Integer deadCount) {
        this.deadCount = deadCount;
    }

    @Override
    public String toString() {
        return "WorldInfo{" +
                "code=" + code +
                ", provinceName='" + provinceName + '\'' +
                ", time='" + time + '\'' +
                ", currentConfirmedCount=" + currentConfirmedCount +
                ", confirmedCount=" + confirmedCount +
                ", curedCount=" + curedCount +
                ", deadCount=" + deadCount +
                '}';
    }
}
