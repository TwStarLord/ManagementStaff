package com.tw.service;

import java.util.List;

import com.tw.pojo.Collects;

public interface CollectsService {

	List<Collects> selByJobId(Integer jobid);
	
	Integer deleteCollects(Collects collects);
	
	Integer insertCollects(Collects collects);
}
