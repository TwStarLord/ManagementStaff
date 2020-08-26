package com.tw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tw.pojo.PageInfo;
import com.tw.pojo.Permission;
import com.tw.pojo.Role;
import com.tw.pojo.Staff;

public interface PermissionMapper {

	List<Permission> selAllFirstPermissionByParentId(Integer parentid);

	List<Permission> selChildrenPermissionByParentId(Integer parentid);

	List<Permission> selPermissionById(Integer jobid);
	
	//List<Staff> selStaffByPermissionId(Integer permission_id);

	long selCount();

	List<Role> selPermissionByPage(@Param("pagestart") Integer pagestart, @Param("pagesize") Integer pagesize, @Param("type") String type);
	
	/**
	 * 该方法用来在加载index.jsp时根据用户信息查询出当前用户所具有的所有权限以及权限菜单
	 * @param jobid
	 * @return
	 */
	List<Permission> selPermissionByRoleid(Integer jobid);
	
	List<Permission> selPermissionByParentid(Integer parentid);

    List<Integer> selPermissionsByRoleId(Integer id);
}
