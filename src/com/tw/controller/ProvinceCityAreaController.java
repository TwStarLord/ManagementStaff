package com.tw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tw.pojo.Areas;
import com.tw.pojo.Cities;
import com.tw.pojo.Provinces;
import com.tw.service.ProvinceCityAreaService;

@Controller
public class ProvinceCityAreaController {

	@Resource
	private ProvinceCityAreaService provinceCityAreaService;
	
	@RequestMapping("findAllProvinces")
	@ResponseBody
	public List<Provinces> findAllProvinces(){
		return provinceCityAreaService.selAllProvinces();
	}
	
	@RequestMapping("findCityByProId")
	@ResponseBody
	public List<Cities> findCityByProviceId(String provinceId){
		return provinceCityAreaService.selCityByProvincesId(provinceId);
	}
	
	@RequestMapping("findAreaByCityId")
	@ResponseBody
	public List<Areas> findAreaByCityId(String cityId){
		return provinceCityAreaService.selAreaByCityId(cityId);
	}
}
