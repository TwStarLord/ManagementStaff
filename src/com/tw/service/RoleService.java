package com.tw.service;

import com.tw.pojo.PageInfo;
import com.tw.pojo.Role;
import com.tw.pojo.Staff;

import java.util.List;

public interface RoleService {

	PageInfo selAllRoleByPage(int page, int limit);
	
	Role selRoleByJobid(Integer jobid);

    Integer updateRolePermissionsByRoleId(Integer role_id, String permissionids);

	List<Staff> selStaffByRoleId(Integer id);

    Role selRoleById(Integer roleid);
}
