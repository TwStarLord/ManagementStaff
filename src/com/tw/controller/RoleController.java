package com.tw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tw.pojo.Staff;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tw.pojo.PageInfo;
import com.tw.pojo.Role;
import com.tw.service.RoleService;

@Controller
@RequestMapping("Role")
public class RoleController {

	@Resource
	private RoleService roleService;


	@RequestMapping("ToFindStaffOfRole")
	public String toFindStaffOfRole(){
		return "admin/StaffWithRole";
	}


	@RequestMapping("FindStaffOfRole")
	@ResponseBody
	public Map<String, Object> findStaffOfRole(Integer id){
		Map<String, Object> result = new HashMap<>();
		List<Staff> staffList = roleService.selStaffByRoleId(id);
		result.put("data", staffList);
		result.put("count", staffList.size());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}


	/**
	 *
	 * @param role_id  角色id
	 * @param permissionids  选中权限
	 * @return
	 */
	@RequestMapping("ChangeRoleCheckedPermission")
	@ResponseBody
	public String ChangeRoleCheckedPermission(@RequestParam("role_id") Integer role_id,@RequestParam("permissionids") String permissionids){
		return roleService.updateRolePermissionsByRoleId(role_id,permissionids)>0 ? "success":"fail";
	}

	/**
	 * 通过开关开启、关闭角色
	 * @param role
	 * @return
	 */
	@RequestMapping("ChangeRoleAvailable")
	@ResponseBody
	@RequiresPermissions("role:query")
	public String changeRoleAvailable(Role role){
		System.out.println(role);
		return "success";
	}
	
	@RequestMapping("FindAllPermissionById")
	@ResponseBody
	public Map<String, Object> findAllPermissionById(Integer jobid){
		Map<String, Object> result = new HashMap<String, Object>();
		Role resultRole = roleService.selRoleByJobid(jobid);
		if(resultRole!=null) {
			result.put("code", 0);
			result.put("msg", "");
			result.put("list",resultRole.getPermissionlist());
			return result;
		}else {
			result.put("code", 1);
			result.put("msg", "权限菜单请求失败，请激活账号!");
			return result;
		}
		
	}
	
	/**
	 * 主页菜单跳转到角色查看界面
	 * @return
	 */
	@RequestMapping("ToFindAllRole")
	public ModelAndView toFindAllPermission() {
		return new ModelAndView("admin/AllRole_Info");
	}
	
	/**
	 * 分页查询所有的角色信息
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping("FindAllRole")
	@ResponseBody
	public Map<String, Object> findAllRole(int limit,int page){
		PageInfo pi = roleService.selAllRoleByPage(page, limit);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", pi.getList());
		result.put("count", pi.getPagerecord());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}
	
}
