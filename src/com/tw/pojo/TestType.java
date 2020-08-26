package com.tw.pojo;


import java.io.Serializable;

public class TestType implements Serializable{

    private Integer type;
    private Integer checked;

    public TestType(Integer type, Integer checked) {
        this.type = type;
        this.checked = checked;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }
}
