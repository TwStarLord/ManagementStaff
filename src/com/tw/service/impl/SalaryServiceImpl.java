package com.tw.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tw.mapper.SalaryMapper;
import com.tw.pojo.PageInfo;
import com.tw.pojo.Salary;
import com.tw.service.SalaryService;

@Service
public class SalaryServiceImpl implements SalaryService{

	@Resource
	private SalaryMapper salaryMapper;

	@Override
	public PageInfo selAllByPage(int page, int limit, Salary salary) {
		PageInfo pageInfo = new PageInfo();
		//设置pageinfo信息
		pageInfo.setPagecurrent(page);
		pageInfo.setPagesize(limit);
		long count = salaryMapper.selCount(salary);
		pageInfo.setPagerecord(count);
		pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误
		String name = null;
		//salary条件
		Integer departid = null;
		Integer jiesuanyuefen = null;
		Integer minjibengongzi = null;
		Integer maxjibengongzi = null;
		Integer jiangli = null;
		Integer chengfa = null;
		Integer jiabangongzi = null;
		Integer kuanggonggongzi = null;
		Integer qingjiagongzi = null;
		Integer chuchaigongzi = null;
		Integer minshijijiesuan = null;
		Integer maxshijijiesuan = null;
		if(salary!=null) {//有参数进行条件查询
			name = salary.getName();
			departid = salary.getDepartid();
			jiesuanyuefen = salary.getJiesuanyuefen();
			minjibengongzi = salary.getMinjibengongzi();
			maxjibengongzi = salary.getMaxjibengongzi();
			jiangli = salary.getJiangli();
			chengfa = salary.getChengfa();
			jiabangongzi = salary.getJiabangongzi();
			kuanggonggongzi = salary.getKuanggonggongzi();
			qingjiagongzi = salary.getQingjiagongzi();
			chuchaigongzi = salary.getChuchaigongzi();
			minshijijiesuan = salary.getMinshijijiesuan();
			maxshijijiesuan = salary.getMaxshijijiesuan();
		}
		pageInfo.setList(salaryMapper.selByPage(((page-1)*limit), limit,name,departid,jiesuanyuefen,minjibengongzi,maxjibengongzi,jiangli,
				chengfa,jiabangongzi,kuanggonggongzi,qingjiagongzi,chuchaigongzi,minshijijiesuan,maxshijijiesuan));
		
		return pageInfo;
	}

	@Override
	public Salary selByJobId(Integer jobid) {
		return salaryMapper.selByJobId(jobid);
	}

	@Override
	public Salary selByJobid(Integer jobid) {
		return salaryMapper.selByJobId(jobid);
	}

	@Override
	public Integer insertSalaryInfo(Salary salary) {
		return salaryMapper.insertSalaryInfo(salary);
	}

	@Override
	public Integer updateSalaryInfo(Salary salary) {
		return salaryMapper.updateSalaryInfo(salary);
	}

	@Override
	public Integer deleteBatchSalaryInfoById(Long id) {
		return null;
	}

	@Override
	public Integer deleteSalaryInfoById(Long id) {
		return null;
	}

}
