package com.tw.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.tw.pojo.Permission;
import com.tw.pojo.Staff;
import org.springframework.stereotype.Service;

import com.tw.mapper.RoleMapper;
import com.tw.pojo.PageInfo;
import com.tw.pojo.Role;
import com.tw.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleMapper roleMapper;

	@Override
	public PageInfo selAllRoleByPage(int page, int limit) {
PageInfo pageinfo = new PageInfo();
		
		long count = roleMapper.selCount();
		
		pageinfo.setPagerecord(count);//设置总记录数
		
		pageinfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//设置总页数
		
		pageinfo.setPagecurrent(page);
		pageinfo.setPagesize(limit);
		
		pageinfo.setPagestart((page-1)*limit);
		
		pageinfo.setList(roleMapper.selRoleByPage(pageinfo));//存储当页记录
		
		System.out.println("service中"+pageinfo.getPagerecord());
		
		return pageinfo;
	}

	@Override
	public Role selRoleByJobid(Integer jobid) {
		return roleMapper.selByJobid(jobid);
	}

	@Override
	public Integer updateRolePermissionsByRoleId(Integer role_id, String permissionids) {
//		这里的权限因为组件的原因，目前无法实现监听到经过修改后的权限信息，即以下几种情况：
//		1.仅权限增加
//		2.权限仅减少
//		3.权限有增有减
//		因此此处直接采用暴力方式，即全部删除再重新添加
		String[] permissionStringArray = permissionids.split(",");
		List<String> permissionStringList = Arrays.asList(permissionStringArray);
//		List<Permission> roleAlreadyHasPermissionList = roleMapper.selPermissionsByRoleId(role_id);
		List<Integer> permissionIdList = new ArrayList<>();
		Integer id = null;
		for (String s:permissionStringList){
			id = Integer.valueOf(s);
			permissionIdList.add(id);
		}
		Long permissionOfRoleCount = roleMapper.selRolePermissionCountByRoleId(role_id);
//		Integer index1 = roleMapper.deleteRolePermissionsByRoleId(role_id);
		Integer index2 = roleMapper.insPermisionAfterChangeByRoleId(role_id,permissionIdList);
		return index2>0 ? 1 : 0;
	}

	@Override
	public List<Staff> selStaffByRoleId(Integer id) {
		return roleMapper.selStaffByRoleId(id);
	}

	@Override
	public Role selRoleById(Integer roleid) {
		return roleMapper.selRoleInfoById(roleid);
	}
}
