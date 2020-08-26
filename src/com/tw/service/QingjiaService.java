package com.tw.service;

import com.tw.pojo.PageInfo;
import com.tw.pojo.Qingjia;

public interface  QingjiaService {

	PageInfo selAllByPage(int page, int limit, Qingjia qingjia);
	
	int deleteQingjiaInfo(int jobid);

	Integer insertQingjiaInfo(Qingjia qingjia);

	int updateQingjiaInfo(Qingjia qingjia);

	Qingjia selByJobId(Integer jobid);

	Qingjia selById(Integer id);

	Integer updateQingjiaApplyStatus(Qingjia qingjia,Long newsid);

	PageInfo selSelfQingjiaInfo(int page, int limit,Integer jobid);

    Integer insEndHolidayApply(Long id);

    Integer updateEndQingjiaApply(Long id, Long newsid, String reply);

    Integer deleteBatchQingjiaInfoById(Long id);

	Integer deleteQingjiaInfoById(Long id);
}
