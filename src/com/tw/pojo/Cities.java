package com.tw.pojo;

import java.io.Serializable;

public class Cities implements Serializable{
	private String cityId;
	private String cityName;
	private String provinceId;
	
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	@Override
	public String toString() {
		return "Cities [cityId=" + cityId + ", cityName=" + cityName + ", provinceId=" + provinceId + "]";
	}
	
}
