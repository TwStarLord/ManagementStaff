package com.tw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tw.pojo.Department;
import com.tw.service.DepartmentService;

@Controller
@RequestMapping("Department")
public class DepartmentInfoController {
	
	@Resource
	private DepartmentService departmentService;


	/*@RequestMapping("GetAllDepartmentInfo")
	@ResponseBody
	public List<Department> getAllDepartmentInfo(){
		return
	}*/

	/**
	 * 主页点击查看员工信息，跳转到员工信息页面
	 * @return
	 */
	@RequestMapping("ToFindAllStaffInfo")
	public String findAllStaffInfo(){
		return "admin/AllStaff_Info";
	}


	/**
	 * 主页点击部门信息查看所有部门信息，该功能使用折叠面板
	 * @return
	 */
	@RequestMapping("ToFindAllDepartmentInfo")
	@RequiresPermissions("depart:query") //执行该方法需要该权限
	public String ToFindAllDepartmentInfo() {
		return "admin/Department_Info";
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("FindAllDepartmentInfo")
	@ResponseBody
	public List<Department> findAllDepartmentInfo(){
		
//		List<Department> list = departmentService.selAllDepartment();
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("depart", result);
		return departmentService.selAllDepartment();
	}
}
