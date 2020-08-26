package com.tw.pojo;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable{


	private Integer departid;
	private String departname;
	private Integer departparentid;
	private List<Staff> stafflist;


	public Integer getDepartid() {
		return departid;
	}
	public void setDepartid(Integer departid) {
		this.departid = departid;
	}
	public String getDepartname() {
		return departname;
	}
	public void setDepartname(String departname) {
		this.departname = departname;
	}
	public Integer getDepartparentid() {
		return departparentid;
	}
	public void setDepartparentid(Integer departparentid) {
		this.departparentid = departparentid;
	}
	public List<Staff> getStafflist() {
		return stafflist;
	}
	public void setStafflist(List<Staff> stafflist) {
		this.stafflist = stafflist;
	}
	@Override
	public String toString() {
		return "Department [departid=" + departid + ", departname=" + departname + ", departparentid=" + departparentid
				+ ", stafflist=" + stafflist + "]";
	}
}
