package com.tw.pojo;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable{

	private Integer id;
	private String name;
	private String available;
	
	//一个角色包含多个权限
	private List<Permission> permissionlist;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Permission> getPermissionlist() {
		return permissionlist;
	}
	public void setPermissionlist(List<Permission> permissionlist) {
		this.permissionlist = permissionlist;
	}
	
	
}
