package com.tw.mapper;

import java.util.List;
import java.util.Map;

import com.tw.pojo.*;
import org.apache.ibatis.annotations.Param;

public interface StaffMapper {

	SexInfo selCountBySex(String sex);

	/**
	 * 查询部门时同时查询所包含的员工
	 * @param departid
	 * @return
	 */
	List<Staff> selByDepartId(Integer departid);
	
	/**
	 * 分页查询，查询总条数
	 * @return
	 */
	public long selCount(Staff staff);
	
	
	/**
	 * 查询全部
	 * @return
	 */
	public List<Staff> selAll();
	/**
	 * 该方法用来登录检测
	 * @param user
	 * @return
	 */
	public Staff selByAccountAndPwd(Staff staff);
	
	/**
	 * 该方法用来注册检测用户名是否已经被注册
	 * @param account
	 * @return
	 */
	public Staff selByAccount(String account);
	
	
	/**
	 * 该方法用来检测邮箱是否被注册
	 * @param mail
	 * @return
	 */
	public Staff selByMail(String mail);

	
	/**
	 * 该方法用来注册用户
	 * @param staff
	 */
	public int insStaff(Staff staff);

	/**
	 * 分页查询
	 * @param pagestart
	 * @param pagesize
	 * @param staff
	 * @return
	 */
	public List<Staff> selByPage(@Param("pagestart") int pagestart,@Param("pagesize")int pagesize,@Param("staff")Staff staff,@Param("jobid") Integer jobid);


	/**
	 * 该方法用来更新信息之前的信息回显
	 * @param jobid
	 * @return
	 */
	public Staff selByJobid(int jobid);


	/**
	 * 该方法用来由管理员对员工信息进行更新
	 * @param staff
	 * @return
	 */
	public int updateStaff(Staff staff);


	public List<Smile> selAllSmile();

	List<Permission> selPermissionByJobId(Integer jobid);

    Staff selManagerByDepartId(Integer departid);

    Staff selByJobId(Integer jobid);

	List<Role> selRoleByJobId(Integer jobid);

    List<Integer> selAllStaffJobId();

	Staff selStaffInfoByJobId(Integer jobid);

    Integer activeStaffByJobId(@Param("jobid") Integer jobid,@Param("activecode") String activecode);

    Staff selKaoqinManager();

    int updateStaffStatus(@Param("jobid") Integer jobid,@Param("status") String status);

    int updateStaffPasswordByJobId(@Param("jobid") Integer jobid,@Param("newpassword") String newpassword);

    Role selExistRoleByJobId(Integer jobid);

    Integer updateStaffStatusByJobId(@Param("jobid") Integer jobid,@Param("status") String status);

    List<Staff> selStaffStatusNotJobing();

    Integer updateStaffSelfInfo(Staff staff);

    Integer updateDownStaffRoleByJobId(Integer jobid);

	Integer updateUpStaffRoleByJobId(@Param("jobid") Integer jobid,@Param("departid") Integer departid);

	Integer selDepartidByJobid(Integer jobid);

	Integer updateStaffRoleInfo(@Param("jobid") Integer jobid,@Param("roleid") Integer roleid);

	Integer insStaffRoleInfo(@Param("jobid") Integer jobid,@Param("roleid") Integer roleid);

    Integer updateStaffDepartByJobId(Staff staff);
}
