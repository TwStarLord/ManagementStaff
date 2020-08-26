package com.tw.pojo;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.security.PrivateKey;
import java.util.List;

public class TestDataOfPermission implements Serializable {
    private Integer id;
    private String title;
    private Integer parentId;
    private List<TestType> checkArr;
    private List<TestDataOfPermission> children;

    public TestDataOfPermission(Integer id, String title, Integer parentId, List<TestType> checkArr, List<TestDataOfPermission> children) {
        this.id = id;
        this.title = title;
        this.parentId = parentId;
        this.checkArr = checkArr;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<TestType> getCheckArr() {
        return checkArr;
    }

    public void setCheckArr(List<TestType> checkArr) {
        this.checkArr = checkArr;
    }

    public List<TestDataOfPermission> getChildren() {
        return children;
    }

    public void setChildren(List<TestDataOfPermission> children) {
        this.children = children;
    }
}
