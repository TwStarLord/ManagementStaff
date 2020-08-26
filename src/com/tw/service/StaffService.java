package com.tw.service;


import java.util.List;
import java.util.Map;

import com.tw.exception.StaffException;
import com.tw.pojo.*;

public interface StaffService {

	Map<String, Object> selCompanyInfoationOfSex();

	public Staff selByAccountAndPwd(Staff staff) throws StaffException;

	public Staff selByAccount(String account);

	public Staff selByMail(String mail);

	public Integer insStaff(Staff staff);
	
	public List<Staff> selAll();

//	public PageInfo selAllByPage(int pagecurrent, int pagesize);

	public Staff selByJobid(int jobid);

	public int updateStaff(Staff staff);

	/**
	 * 测试分页操作
	 * @param page
	 * @param limit
	 * @return
	 */
	public PageInfo selAllByPage1(int page, int limit,Staff staff);

	/**
	 * 删除
	 * @return
	 */
	public List<Smile> selAllSmile();

    List<Permission> selPermissionByJobId(Integer jobid);

    Staff selByJobId(Integer jobid);

	List<Role> selRoleByJobId(Integer jobid);

	Staff selStaffInfoByJobId(Integer jobid);

	List<Integer> selAllStaffJobId();

    Integer updateByactiveStaffByJobId(Integer jobid,String activecode);

    Staff selKaoqinManager();

    int updateStaffStatus(Integer jobid, String status);

    int updateStaffPasswordByJobId(Integer jobid, String md5Password);

	Role selExistRoleByJobId(Integer jobid);

    Integer updateStaffSelfInfo(Staff staff);

    Integer updateDownStaffRoleByJobId(Integer jobid);

	Integer updateUpStaffRoleByJobId(Integer jobid);

	Integer insStaffRoleInfo(Staff staff, Integer roleid);

    Integer updateStaffDepart(Staff staff);

    Integer deleteBatchStaffInfoById(Long id);

	Integer deleteStaffInfoById(Long id);
}
