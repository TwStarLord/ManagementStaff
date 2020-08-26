package com.tw.pojo;

import java.io.Serializable;

public class SexInfo implements Serializable {

    private Long value;
    private String name;

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
}
