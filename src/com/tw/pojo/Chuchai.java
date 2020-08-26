package com.tw.pojo;

import java.io.Serializable;

public class Chuchai implements Serializable{

	private Integer id;
	private int jobid;
	private String name;
	private String account;
	private Integer departid;
	private Department department;
	private String starttime;
	private String endtime;
	private String destination;
	private String status;
	private String cause;
	private String shenpi;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getJobid() {
		return jobid;
	}
	public void setJobid(int jobid) {
		this.jobid = jobid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDepartid() {
		return departid;
	}
	public void setDepartid(Integer departid) {
		this.departid = departid;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getShenpi() {
		return shenpi;
	}
	public void setShenpi(String shenpi) {
		this.shenpi = shenpi;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Chuchai{" +
				"id=" + id +
				", jobid=" + jobid +
				", name='" + name + '\'' +
				", account='" + account + '\'' +
				", departid=" + departid +
				", department=" + department +
				", starttime='" + starttime + '\'' +
				", endtime='" + endtime + '\'' +
				", destination='" + destination + '\'' +
				", status='" + status + '\'' +
				", cause='" + cause + '\'' +
				", shenpi='" + shenpi + '\'' +
				'}';
	}
}
