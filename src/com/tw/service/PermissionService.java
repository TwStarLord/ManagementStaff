package com.tw.service;

import java.util.List;
import java.util.Map;

import com.tw.pojo.PageInfo;
import com.tw.pojo.Permission;

public interface PermissionService {

	Map<String,Object> selChildrenPermissionByParentId(Integer parentid,Integer id);

	PageInfo selAllPermissionByPage(int page, int limit, Permission permission);
}
