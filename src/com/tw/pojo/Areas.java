package com.tw.pojo;

import java.io.Serializable;

public class Areas implements Serializable{
	private String areaId;
	private String areaName;
	private String cityId;
	
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	@Override
	public String toString() {
		return "Areas [areaId=" + areaId + ", areaName=" + areaName + ", cityId=" + cityId + "]";
	}
	
}
