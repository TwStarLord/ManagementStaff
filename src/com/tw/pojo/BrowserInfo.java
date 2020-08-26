package com.tw.pojo;

import java.io.Serializable;

public class BrowserInfo implements Serializable {

    private Long value;
    private String name;

    public BrowserInfo() {
    }

    public BrowserInfo(Long value, String name) {
        this.value = value;
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BrowserInfo{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
