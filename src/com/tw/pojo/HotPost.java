package com.tw.pojo;

import java.io.Serializable;

public class HotPost implements Serializable {

    private Long id;
    private String title;
    private String href;
    private String date;
    private String author;
    private Long collectCount;

    public HotPost() {
    }

    public HotPost(String title, String href, String date, String author, Long collectCount) {
        this.title = title;
        this.href = href;
        this.date = date;
        this.author = author;
        this.collectCount = collectCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Long collectCount) {
        this.collectCount = collectCount;
    }

    @Override
    public String toString() {
        return "HotPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", collectCount=" + collectCount +
                '}';
    }
}
