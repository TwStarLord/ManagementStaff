package com.tw.mapper;

import java.util.List;

import com.tw.pojo.Areas;
import com.tw.pojo.Cities;
import com.tw.pojo.Provinces;

public interface ProvinceCityAreaMapper {

	Provinces selProByProName(String name);
	
	Provinces selProByProId(String id);

	List<Provinces> selAllProvinces();
	
	List<Cities> selCitiesByProvinceId(String provinceId);
	
	Cities selCityByCityName(String name, String provinceId);
	
	Cities selCityById(String id);
	
	List<Areas> selAreasByCityId(String cityId);
	
	Areas selAreaByAreaName(String name, String cityId);
	
	Areas selAreaById(String id);
}
