package com.tw.pojo;

import java.io.Serializable;

public class Collects implements Serializable{

	private Integer id;
	private Integer staffid;
	private Integer noticeid;
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
	public Integer getNoticeid() {
		return noticeid;
	}
	public void setNoticeid(Integer noticeid) {
		this.noticeid = noticeid;
	}
	@Override
	public String toString() {
		return "Collects [id=" + id + ", staffid=" + staffid + ", noticeid=" + noticeid + "]";
	}
}
