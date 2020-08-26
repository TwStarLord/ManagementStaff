package com.tw.pojo;

import java.io.Serializable;

public class Qingjia implements Serializable{

	private Long id;
	private Integer jobid;
	private String name;
	private String account;
	private Integer departid;
	private String departname;
	private Department department;
	//mysql自动处理日期格式
	private String starttime;
	private String endtime;
	private Long leavedays;
	private String status;
	private String cause;
	private String descripetion;
	private String subdate;
	private String shenpi;

	public Qingjia() {
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getJobid() {
		return jobid;
	}

	public void setJobid(Integer jobid) {
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

	public Long getLeavedays() {
		return leavedays;
	}

	public void setLeavedays(Long leavedays) {
		this.leavedays = leavedays;
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

	public String getDescripetion() {
		return descripetion;
	}

	public void setDescripetion(String descripetion) {
		this.descripetion = descripetion;
	}

	public String getSubdate() {
		return subdate;
	}

	public void setSubdate(String subdate) {
		this.subdate = subdate;
	}

	public String getShenpi() {
		return shenpi;
	}

	public void setShenpi(String shenpi) {
		this.shenpi = shenpi;
	}

	@Override
	public String toString() {
		return "Qingjia{" +
				"id=" + id +
				", jobid=" + jobid +
				", name='" + name + '\'' +
				", account='" + account + '\'' +
				", departid=" + departid +
				", departname='" + departname + '\'' +
				", department=" + department +
				", starttime='" + starttime + '\'' +
				", endtime='" + endtime + '\'' +
				", leavedays=" + leavedays +
				", status='" + status + '\'' +
				", cause='" + cause + '\'' +
				", descripetion='" + descripetion + '\'' +
				", subdate='" + subdate + '\'' +
				", shenpi='" + shenpi + '\'' +
				'}';
	}
}
