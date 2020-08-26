package com.tw.mapper;

import java.util.List;

import com.tw.pojo.Department;

public interface DepartmentMapper {

	List<Department> selAllDepartment();

	Department selDepartmentByDepartid(Integer departid);

	/*List<Department> getAllDepartmentInfo();*/
	
}
