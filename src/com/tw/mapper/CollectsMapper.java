package com.tw.mapper;

import java.util.List;

import com.tw.pojo.Collects;

public interface CollectsMapper {

	List<Collects> selByJobId(Integer jobId);
	
	Integer insertCollects(Collects collects);
	
	Integer deleteCollects(Collects collects);

}
