package com.tw.pojo;

import java.io.Serializable;

public class Provinces implements Serializable{
	private String provinceId;
	private String provinceName;
	
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	@Override
	public String toString() {
		return "Provinces [provinceId=" + provinceId + ", provinceName=" + provinceName + "]";
	}
	
	
}
