package com.tw.pojo;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable{

	private Integer id;
	private String title;
	private String date;
	private String content;
	private String author;
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
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Notice [id=" + id + ", title=" + title + ", date=" + date + ", content=" + content + ", author="
				+ author + "]";
	}
}
