package com.tw.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tw.mapper.CollectsMapper;
import com.tw.pojo.Collects;
import com.tw.service.CollectsService;

@Service
public class CollectsServiceImpl implements CollectsService{

	@Resource
	private CollectsMapper collectsMapper;
	
	@Override
	public List<Collects> selByJobId(Integer jobid) {
		return collectsMapper.selByJobId(jobid);
	}

	@Override
	public Integer deleteCollects(Collects collects) {
		return collectsMapper.deleteCollects(collects);
	}

	@Override
	public Integer insertCollects(Collects collects) {
		return collectsMapper.insertCollects(collects);
	}

}
