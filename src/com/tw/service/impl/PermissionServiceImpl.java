package com.tw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tw.pojo.*;
import org.apache.taglibs.standard.lang.jstl.NullLiteral;
import org.springframework.stereotype.Service;

import com.tw.mapper.PermissionMapper;
import com.tw.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{

	@Resource
	private PermissionMapper permissionMapper;

	/**
	 * 上下级权限信息，获取之后进行业务装配，返回前台权限树的数据
	 * @param parentid
	 * @return
	 */
	@Override
	public Map<String, Object> selChildrenPermissionByParentId(Integer parentid,Integer id) {

//		查询当前角色的所有权限
		List<Integer> permissionListOfRole = permissionMapper.selPermissionsByRoleId(id);


		TestType tt1 = new TestType(0,1);  //后期的check的值要根据是否角色是否拥有权限来判断
		TestType tt2 = new TestType(0,0);
		List<TestType> list1 = new ArrayList<TestType>();
		List<TestType> list2 = new ArrayList<TestType>();
		list1.add(tt1);
		list2.add(tt2);

		Permission permissionTemp = null;
		TestDataOfPermission tdopParent = null;
		TestDataOfPermission testDataOfPermissionTemp = null;
		List<TestDataOfPermission> data = new ArrayList<>();
//		List<TestDataOfPermission> childPermissionList = null;
		List<Permission> permissionList = permissionMapper.selChildrenPermissionByParentId(parentid);
		if(permissionList != null){
			for (Permission permission:permissionList) {
//			对每个权限进行信息判断，由属性parentid来判断是权限等级
				if(permission.getParentid() == 0){ //一级权限
					if(permissionListOfRole.contains(permission.getId())){
				        tdopParent = new TestDataOfPermission(permission.getId(),
						permission.getName(),permission.getParentid(),
						list1,new ArrayList<TestDataOfPermission>());
					}else{
						tdopParent = new TestDataOfPermission(permission.getId(),
						permission.getName(),permission.getParentid(),
						list2,new ArrayList<TestDataOfPermission>());
					}

					if(permission.getChildperlist()!=null){  //该权限存在子菜单，后期可以针对此功能进行封装
						for(int i=0;i<permission.getChildperlist().size();i++){  //在这里查找每个权限下对应的子孙权限
//							用一个中间变量接收当前子
							permissionTemp = permission.getChildperlist().get(i);
							if(permissionListOfRole.contains(permissionTemp.getId())){
//								判断角色是否含有该权限
								testDataOfPermissionTemp = new TestDataOfPermission(permissionTemp.getId(),permissionTemp.getName(),permissionTemp.getParentid(),list1,null);
							}else{
								testDataOfPermissionTemp = new TestDataOfPermission(permissionTemp.getId(),permissionTemp.getName(),permissionTemp.getParentid(),list2,null);
							}
							tdopParent.getChildren().add(testDataOfPermissionTemp);
						}
						data.add(tdopParent);
//						这里的是强引用，直接会影响达到后续数据
//						childPermissionList.clear();
					}else{//该权限没有子菜单

					}

				}


			}
		}
		Map<String,Object> result = new HashMap<>();
		result.put("status",new Status(200,"success"));
		result.put("data",data);

		return result;
	}

	/**
	 * 获取权限树信息
	 * 业务装配
	 * @param
	 * @return
	 */
	/*@Override
	public List<Permission> selChildrenPermissionByParentId(Integer parentid) {
//		获取一级权限信息
		List<Permission> parentidPermissionList = permissionMapper.selAllFirstPermissionByParentId(parentid);
//		根据一级菜单信息查询出所有的子孙级权限信息
		List<Permission> permissionList = permissionMapper.selChildrenPermissionByParentId(parentid);
//		容器用来存放自己权限信息
		List<Permission> childPermissionList = new ArrayList<>();
		if(parentidPermissionList != null){
			if(permissionList != null){
				for(Permission permissionParent:parentidPermissionList){
					for(Permission permissionSon:permissionList){
						if (permissionParent.getId() == permissionSon.getParentid()){//说明权限之间存在上下级关系
							childPermissionList.add(permissionSon);
						}
					}
					permissionParent.setChildperlist(childPermissionList);
				}
			}

		}


		return permissionList;
	}
*/


	@Override
	public PageInfo selAllPermissionByPage(int page,int limit,Permission permission) {
		PageInfo pageinfo = new PageInfo();
		
		long count = permissionMapper.selCount();
		
		pageinfo.setPagerecord(count);//设置总记录数
		
		pageinfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//设置总页数
		
		pageinfo.setPagecurrent(page);
		pageinfo.setPagesize(limit);
		
		pageinfo.setPagestart((page-1)*limit);
		String type = null;
		if(permission!=null) {
			type = permission.getType();
		}
		
		pageinfo.setList(permissionMapper.selPermissionByPage(pageinfo.getPagestart(),pageinfo.getPagesize(),type));//存储当页记录
		
		System.out.println("service中"+pageinfo.getPagerecord());
		
		return pageinfo;
	}

}
