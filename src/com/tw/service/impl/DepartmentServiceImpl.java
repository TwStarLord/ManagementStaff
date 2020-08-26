package com.tw.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tw.mapper.DepartmentMapper;
import com.tw.pojo.Department;
import com.tw.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Resource
	private DepartmentMapper departmentMapper;
	
	@Override
	public List<Department> selAllDepartment() {
		return departmentMapper.selAllDepartment();
	}

}
