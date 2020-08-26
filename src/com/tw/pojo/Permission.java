package com.tw.pojo;

import java.io.Serializable;
import java.util.List;

public class Permission implements Serializable{

	private Integer id;
	private Integer role_id;
	private String name;
	private String type;
	private String url;
	private String percode;
	private Integer parentid;
	private Integer parentids;
	private String available;
	//该全向会含有下级权限
	private List<Permission> childperlist;
	//一个权限可以被多个角色拥有
	private List<Role> rolelist;

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPercode() {
		return percode;
	}
	public void setPercode(String percode) {
		this.percode = percode;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public Integer getParentids() {
		return parentids;
	}
	public void setParentids(Integer parentids) {
		this.parentids = parentids;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public List<Role> getRolelist() {
		return rolelist;
	}
	public void setRolelist(List<Role> rolelist) {
		this.rolelist = rolelist;
	}
	public List<Permission> getChildperlist() {
		return childperlist;
	}
	public void setChildperlist(List<Permission> childperlist) {
		this.childperlist = childperlist;
	}

	@Override
	public String toString() {
		return "Permission{" +
				"id=" + id +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				", url='" + url + '\'' +
				", percode='" + percode + '\'' +
				", parentid=" + parentid +
				", parentids=" + parentids +
				", available='" + available + '\'' +
				", childperlist=" + childperlist +
				", rolelist=" + rolelist +
				'}';
	}
}
