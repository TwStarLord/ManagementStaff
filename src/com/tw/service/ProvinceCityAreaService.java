package com.tw.service;

import java.util.List;

import com.tw.pojo.Areas;
import com.tw.pojo.Cities;
import com.tw.pojo.Provinces;

public interface ProvinceCityAreaService {

	List<Provinces> selAllProvinces();

	List<Cities> selCityByProvincesId(String provinceId);

	List<Areas> selAreaByCityId(String cityId);
	
	Provinces selProvinceById(String id);
	
	Cities selCityById(String id);
	
	Areas selAreaById(String id);
	
	Provinces selProvinceByName(String name);
	
	Cities selCityByName(String name, String provinceId);
	
	Areas selAreaByName(String name, String cityId);
}
