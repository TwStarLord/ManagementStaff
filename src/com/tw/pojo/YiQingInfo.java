package com.tw.pojo;

import java.io.Serializable;

public class YiQingInfo implements Serializable {

    //code列
    private Integer code;
    //time列
    private String time;
    //provinceName列
    private String provinceName;
    //areaName列
    private String areaName;
    //confirmCount列
    private Integer confirmCount;
    //curedCount列
    private Integer curedCount;
    //deadCount
    private Integer deadCount;


    private Integer confirmCountOfCountry;
    private Integer curedCountOfCountry;
    private Integer deadCountOfCountry;

    public YiQingInfo() {
    }

    public YiQingInfo(Integer code, String time, String provinceName, String areaName, Integer confirmCount, Integer curedCount, Integer deadCount, Integer confirmCountOfCountry, Integer curedCountOfCountry, Integer deadCountOfCountry) {
        this.code = code;
        this.time = time;
        this.provinceName = provinceName;
        this.areaName = areaName;
        this.confirmCount = confirmCount;
        this.curedCount = curedCount;
        this.deadCount = deadCount;
        this.confirmCountOfCountry = confirmCountOfCountry;
        this.curedCountOfCountry = curedCountOfCountry;
        this.deadCountOfCountry = deadCountOfCountry;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getConfirmCount() {
        return confirmCount;
    }

    public void setConfirmCount(Integer confirmCount) {
        this.confirmCount = confirmCount;
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

    public Integer getConfirmCountOfCountry() {
        return confirmCountOfCountry;
    }

    public void setConfirmCountOfCountry(Integer confirmCountOfCountry) {
        this.confirmCountOfCountry = confirmCountOfCountry;
    }

    public Integer getCuredCountOfCountry() {
        return curedCountOfCountry;
    }

    public void setCuredCountOfCountry(Integer curedCountOfCountry) {
        this.curedCountOfCountry = curedCountOfCountry;
    }

    public Integer getDeadCountOfCountry() {
        return deadCountOfCountry;
    }

    public void setDeadCountOfCountry(Integer deadCountOfCountry) {
        this.deadCountOfCountry = deadCountOfCountry;
    }

    @Override
    public String toString() {
        return "YiQingInfo{" +
                "code=" + code +
                ", time='" + time + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", confirmCount=" + confirmCount +
                ", curedCount=" + curedCount +
                ", deadCount=" + deadCount +
                ", confirmCountOfCountry=" + confirmCountOfCountry +
                ", curedCountOfCountry=" + curedCountOfCountry +
                ", deadCountOfCountry=" + deadCountOfCountry +
                '}';
    }
}
