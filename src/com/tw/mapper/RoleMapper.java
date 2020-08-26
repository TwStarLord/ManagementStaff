package com.tw.mapper;

import java.util.List;

import com.tw.pojo.PageInfo;
import com.tw.pojo.Permission;
import com.tw.pojo.Role;
import com.tw.pojo.Staff;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {

	List<Role> selRoleById(Integer jobid);

	long selCount();
	
	List<Role> selRoleByPage(PageInfo pageinfo);
	
	/**
	 * 角色管理界面通过输入角色id查询拥有该角色id的所有员工信息
	 * @param role
	 * @return
	 */
	List<Staff> selStaffByRoleID(Integer role);
	
	Role selByJobid(Integer jobid);

    List<Permission> selPermissionsByRoleId(Integer role_id);

	Integer insPermisionAfterChangeByRoleId(@Param("role_id") Integer role_id,@Param("list") List<Integer> permissionIdList);

	Integer deleteRolePermissionsByRoleId(Integer role_id);

	Long selRolePermissionCountByRoleId(Integer role_id);

	List<Staff> selStaffByRoleId(Integer id);

	Role selRoleInfoById(Integer roleid);
}
