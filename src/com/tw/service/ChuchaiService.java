package com.tw.service;

import java.util.List;

import com.tw.exception.StaffException;
import com.tw.pojo.Chuchai;
import com.tw.pojo.PageInfo;

public interface ChuchaiService {

	/**
	 * 页面加载时直接查询全部信息进行显示
	 * @return
	 */
	List<Chuchai> selAll();

	PageInfo selAllByPage(int page, int limit, Chuchai chuchai);

	Chuchai selByJobId(Integer jobid);

	int updateChuchaiInfo(Chuchai chuchai);
	
	int deleteChuchaiInfo(int jobid);

	int insertChuchaiInfo(Chuchai chuchai) throws StaffException;

	Chuchai selById(Integer id);

	Integer updateChuchaiManagementStatus(Chuchai chuchai,Long newsid);

	PageInfo selSelfChuchaiInfo(int page, int limit, Integer jobid);

    Integer insCountChuchaiFeeApply(Long id);

    Integer updateCountChuchaiApply(Long id, Long newsid, String reply);

	Integer deleteBatchChuchaiInfoById(Long id);

	Integer deleteChuchaiInfoById(Long id);
}
