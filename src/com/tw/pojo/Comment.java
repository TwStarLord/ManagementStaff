package com.tw.pojo;

import java.io.Serializable;

public class Comment implements Serializable{

	private Integer id;
	private Integer staffid;
	private Integer noticeid;
	private String comment;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStaffid() {
		return staffid;
	}
	public void setStaffid(Integer staffid) {
		this.staffid = staffid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getNoticeid() {
		return noticeid;
	}
	public void setNoticeid(Integer noticeid) {
		this.noticeid = noticeid;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", staffid=" + staffid + ", noticeid=" + noticeid + ", comment=" + comment + "]";
	}
	
}
