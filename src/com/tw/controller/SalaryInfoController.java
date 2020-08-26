package com.tw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tw.annotation.Operation;
import com.tw.pojo.Staff;
import com.tw.service.StaffService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tw.pojo.PageInfo;
import com.tw.pojo.Salary;
import com.tw.service.SalaryService;

@Controller
@RequestMapping("SalaryInfo")
public class SalaryInfoController {

	@Resource
	private SalaryService salaryService;

	@RequestMapping("BatchDeleteSalaryInfo")
	@ResponseBody
	@Operation(name = "批量删除薪资信息")
	@RequiresPermissions("salary:batchdelete")
	public String deleteBatchSalaryInfo(String batchid){
//		return salaryService.deleteBatchSalaryInfoById(id)>0 ? "success":"fail";
		return "success";
	}

	@RequestMapping("DeleteSalaryInfo")
	@ResponseBody
	@Operation(name = "删除薪资信息")
	@RequiresPermissions("salary:delete")
	public String deleteSalaryInfo(Long id){
//		return salaryService.deleteSalaryInfoById(id)>0 ? "success":"fail";
		return "success";
	}


	@RequestMapping("ToInsertSalaryInfo")
//	@RequiresPermissions("salary:insert")
	public String toInsertSalaryInfo(){
		return "admin/InsertSalaryInfo";
	}

	@RequestMapping("InsertSalaryInfo")
	@ResponseBody
	public String insertSalaryInfo(Salary salary){
		return salaryService.insertSalaryInfo(salary)>0 ? "success":"fail";
	}

	/**
	 * 员工薪资界面点击编辑按钮，弹窗打开员工薪资信息编辑界面
	 * @return
	 */
	@RequestMapping("ToUpdateSalaryInfo")
	@RequiresPermissions("salary:update")
	public String toUpdateSalaryInfo(Integer jobid, HttpServletRequest request){
		request.setAttribute("editsalaryinfo",salaryService.selByJobid(jobid));
		return "admin/Salary_Edit";
	}

	/**
	 * 编辑员工薪资界面提交按钮进行信息更新
	 * @return
	 */
	@RequestMapping("UpdateSalaryInfo")
	@ResponseBody
	@RequiresPermissions("salary:update")
	public String updateSalaryInfo(Salary salary){


		return salaryService.updateSalaryInfo(salary)>0 ? "success":"fail";
	}

	/**
	 * 主页点击薪资信息查看员工薪资信息
	 * @return
	 */
	@RequestMapping("ToFindAllSalaryInfo")
	public String findAllSalaryInfo(){
		return "admin/AllSalary_Info";
	}


	/**
	 * 导出excel
	 * @param batchjobid
	 * @return
	 */
	@RequestMapping("exportSalaryExcel")
	@ResponseBody
	@RequiresPermissions("salary:excelexport")
	public Map<String, Object> exportSalaryExcel(String batchjobid){
		System.out.println("接收到的id"+batchjobid);
		String[] idlist = batchjobid.split(",");
		List<Salary> salaryList = new ArrayList<Salary>();
		for (String s : idlist) {
			Integer jobid = Integer.valueOf(s);
			salaryList.add(salaryService.selByJobId(jobid));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "");
		result.put("data",salaryList);
		return result;
	}
	
	/**
	 * 主页处点击员工薪资信息可查看所有的薪资信息
	 * 在这里的是固化操作时，出现了400错误，是因为条件查询的参数中含有基本类型，但是将所有的基本数据类型改为integet之后没有报400错误，
	 * 所以可以猜想layui框架从前台传值到后台时，默认传的数据类型如果为基本类型时，会进行自动封装的操作
	 * @param limit
	 * @param page
	 * @param salary
	 * @return
	 */
	@RequestMapping("FindAllSalaryInfo")
	@ResponseBody
	@RequiresPermissions("salary:query")
	public Map<String, Object> findAllSalaryInfo(int page,int limit,Salary salary){
		//测试接收到的数据
//		System.out.println(salary);
		
		PageInfo pi = salaryService.selAllByPage(page,limit,salary);
		//以下操作
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", pi.getList());
		result.put("count", pi.getPagerecord());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}
}
