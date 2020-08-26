package com.tw.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 员工信息表
 * @author tw
 *
 */
public class Staff implements Serializable{
/*
 * `jobid` bigint(20) NOT NULL,
	`name` varchar(255) NOT NULL,
	image varchar(255)
	`account` varchar(255) NOT NULL,
	`password` varchar(255) NOT NULL,
	`departid` int(10) DEFAULT NULL,
	`departname` varchar(255) DEFAULT NULL,
	`sex`  varchar(255)   DEFAULT NULL,            
	`birthday` varchar(255) DEFAULT NULL,
	`eduback` varchar(255) DEFAULT NULL,
	`mobile` varchar(255) DEFAULT NULL,
	`mail` varchar(255) DEFAULT NULL,
	`address` varchar(255) DEFAULT NULL,
	`status` varchar(255) DEFAULT NULL,
	`timeforjob` varchar(255) DEFAULT NULL,
	`isadmin` int(10) DEFAULT NULL,
	`descripetion` text(100)  DEFAULT NULL,
	activecode varchar(255)
 * 
 */



//	前端框架存在漏洞，无法使用table 的 tpl 语法 获取对应数据，只能后台处理
	private Integer departid;

	public Integer getDepartid() {
		return departid;
	}

	public void setDepartid(Integer departid) {
		this.departid = departid;
	}

	private Integer jobid;
	
	private String name;
	
	private String image;
	
	private String account;
	
	private String password;

	private String salt;

	/*private  int  departid;

	private String departname;*/

	private Department department;

	public Staff() {
	}

	public Staff(int jobid, String name, String image, String account, String password, String salt, Department department, String sex, String birthday, String eduback, String mobile, String mail, String address, String status, String timeforjob, Integer role_id, String descripetion, String activecode, List<Permission> permissionUrlList, List<Role> roleList) {
		this.jobid = jobid;
		this.name = name;
		this.image = image;
		this.account = account;
		this.password = password;
		this.salt = salt;
		this.department = department;
		this.sex = sex;
		this.birthday = birthday;
		this.eduback = eduback;
		this.mobile = mobile;
		this.mail = mail;
		this.address = address;
		this.status = status;
		this.timeforjob = timeforjob;
		this.role_id = role_id;
		this.descripetion = descripetion;
		this.activecode = activecode;
		this.permissionUrlList = permissionUrlList;
		this.roleList = roleList;
	}

	private String sex;

	private String birthday;

	private String eduback;

	private String mobile;

	private String mail;

	private String address;

	private String status;

	private String timeforjob;

	private Integer role_id;

	private String descripetion;

	private String activecode;

	private boolean isrecord;

	private String role_name;

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public boolean isIsrecord() {
		return isrecord;
	}

	public void setIsrecord(boolean isrecord) {
		this.isrecord = isrecord;
	}

	//	登录成功时直接从数据库中根据用户id查出角色id，再根据角色id查出所有的权限id以及对应的url地址，
//	最后将url地址保存在staff信息中，当存在敏感操作时，直接判断url权限，如果存在，直接放行，如果不存在，弹框提示
//	所以说这里不能放在role中，而是应该放在staff信息中
	public List<Permission> permissionUrlList;

	public List<Role> roleList;

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public List<Permission> getPermissionUrlList() {
		return permissionUrlList;
	}

	public void setPermissionUrlList(List<Permission> permissionUrlList) {
		this.permissionUrlList = permissionUrlList;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEduback() {
		return eduback;
	}

	public void setEduback(String eduback) {
		this.eduback = eduback;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimeforjob() {
		return timeforjob;
	}

	public void setTimeforjob(String timeforjob) {
		this.timeforjob = timeforjob;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDescripetion() {
		return descripetion;
	}

	public void setDescripetion(String descripetion) {
		this.descripetion = descripetion;
	}

	public String getActivecode() {
		return activecode;
	}

	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "Staff{" +
				"departid=" + departid +
				", jobid=" + jobid +
				", name='" + name + '\'' +
				", image='" + image + '\'' +
				", account='" + account + '\'' +
				", password='" + password + '\'' +
				", salt='" + salt + '\'' +
				", department=" + department +
				", sex='" + sex + '\'' +
				", birthday='" + birthday + '\'' +
				", eduback='" + eduback + '\'' +
				", mobile='" + mobile + '\'' +
				", mail='" + mail + '\'' +
				", address='" + address + '\'' +
				", status='" + status + '\'' +
				", timeforjob='" + timeforjob + '\'' +
				", role_id=" + role_id +
				", descripetion='" + descripetion + '\'' +
				", activecode='" + activecode + '\'' +
				", isrecord=" + isrecord +
				", role_name='" + role_name + '\'' +
				", permissionUrlList=" + permissionUrlList +
				", roleList=" + roleList +
				'}';
	}
}
	
	
	
	
	
	
	
	

