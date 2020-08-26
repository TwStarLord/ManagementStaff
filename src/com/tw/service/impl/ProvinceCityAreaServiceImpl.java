package com.tw.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tw.mapper.ProvinceCityAreaMapper;
import com.tw.pojo.Areas;
import com.tw.pojo.Cities;
import com.tw.pojo.Provinces;
import com.tw.service.ProvinceCityAreaService;

@Service
public class ProvinceCityAreaServiceImpl implements ProvinceCityAreaService{

	@Resource
	private ProvinceCityAreaMapper provinceCityAreaMapper;

	@Override
	public List<Provinces> selAllProvinces() {
		return provinceCityAreaMapper.selAllProvinces();
	}

	@Override
	public List<Cities> selCityByProvincesId(String provinceId) {
		return provinceCityAreaMapper.selCitiesByProvinceId(provinceId);
	}

	@Override
	public List<Areas> selAreaByCityId(String cityId) {
		return provinceCityAreaMapper.selAreasByCityId(cityId);
	}

	@Override
	public Provinces selProvinceById(String id) {
		return provinceCityAreaMapper.selProByProId(id);
	}

	@Override
	public Cities selCityById(String id) {
		return selCityById(id);
	}

	@Override
	public Areas selAreaById(String id) {
		return provinceCityAreaMapper.selAreaById(id);
	}

	@Override
	public Provinces selProvinceByName(String name) {
		return provinceCityAreaMapper.selProByProName(name);
	}

	@Override
	public Cities selCityByName(String name, String provinceId) {
		return provinceCityAreaMapper.selCityByCityName(name, provinceId);
	}

	@Override
	public Areas selAreaByName(String name, String cityId) {
		return provinceCityAreaMapper.selAreaByAreaName(name, cityId);
	}

	
	

}
