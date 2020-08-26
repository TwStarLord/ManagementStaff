package com.tw.service;

import com.tw.pojo.PageInfo;
import com.tw.pojo.Salary;

public interface SalaryService {

	PageInfo selAllByPage(int page, int limit, Salary salary);

	Salary selByJobId(Integer jobid);

    Salary selByJobid(Integer jobid);

    Integer insertSalaryInfo(Salary salary);

    Integer updateSalaryInfo(Salary salary);

    Integer deleteBatchSalaryInfoById(Long id);

    Integer deleteSalaryInfoById(Long id);

//	int deleteSalaryInfo(int jobid);
//
//	int insertSalaryInfo(Salary salary);
//
//	int updateSalaryInfo(Salary salary);
}
