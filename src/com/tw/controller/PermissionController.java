package com.tw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tw.pojo.*;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.apache.ibatis.executor.loader.ResultLoader;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tw.service.PermissionService;

@Controller
@RequestMapping("Permission")
public class PermissionController {

	@Resource
	private PermissionService permissionService;

	@RequestMapping("GetPermissionTree")
	@ResponseBody
	public Map<String, Object> getPermissionTree(Integer id){//这里的id值是角色id
		return permissionService.selChildrenPermissionByParentId(0,id);
	}


	@RequestMapping("ToGetPermissionInfo")
	public String toGetPermissionInfo(Integer id){
		return "admin/PermissionInfo";
	}


	@RequestMapping("ToGetPermissionWithTree")
//	@RequiresPermissions("permission:query")
	public String toGetPermissionWithTree(){
		return "admin/Permission_Tree";
	}


	/**
	 * 通过开关修改权限是否开启
	 * @param permission
	 * @return
	 */
	@RequestMapping("ChangePermissionAvailable")
	@RequiresPermissions("permission:query")
	@ResponseBody
	public String changePermissionAvailable(Permission permission){
		System.out.println("在这里测试："+ permission);
		return "success";
	}

	/**
	 * 主页菜单选项跳转到权限查看界面
	 * @return
	 */
	@RequestMapping("ToFindAllPermission")
	public ModelAndView toFindAllPermission() {
		return new ModelAndView("admin/AllPermission_Info");
	}
	
	/**
	 * 分页查询所有权限
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping("FindAllPermission")
	@ResponseBody
	public Map<String, Object> findALlPermission(int limit,int page,Permission permission){
		PageInfo pi = permissionService.selAllPermissionByPage(page,limit,permission);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", pi.getList());
		result.put("count", pi.getPagerecord());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}
	
}
