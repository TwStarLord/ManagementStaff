package com.tw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tw.annotation.Operation;
import com.tw.pojo.*;
import com.tw.service.NewsService;
import com.tw.service.RoleService;
import com.tw.service.SystemInfoService;
import com.tw.utils.DateFormatUtil;
import com.tw.utils.MD5Utils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tw.service.StaffService;
import org.springframework.web.bind.annotation.RestController;

@Operation(name = "员工信息管理")
@Controller
@RequestMapping("StaffInfo")
public class StaffInfoController {

	@Resource
	private StaffService staffservice;

	@Resource
	private NewsService newsService;

	@Resource
	private RoleService roleService;

	@Resource
	private SystemInfoService systemInfoService;


	@RequestMapping("BatchDeleteStaffInfo")
	@ResponseBody
	@Operation(name = "批量删除员工信息")
	@RequiresPermissions("staff:batchdelete")
	public String deleteBatchStaffInfo(Long id){
//		return staffservice.deleteBatchStaffInfoById(id)>0 ? "success":"fail";
		return "success";
	}

	@RequestMapping("DeleteStaffInfo")
	@ResponseBody
	@Operation(name = "删除员工信息")
	@RequiresPermissions("staff:delete")
	public String deleteStaffInfo(Long id){
//		return staffservice.deleteStaffInfoById(id)>0 ? "success":"fail";
		return "success";
	}

	@RequestMapping("ChangeStaffDepartmentInfo")
	@ResponseBody
	public String changeStaffDepartmentInfo(Staff staff){
		return staffservice.updateStaffDepart(staff)>0 ? "success":"fail";
	}

	@RequestMapping("ToChangeStaffDepartmentInfo")
	public String toChangeStaffDepartmentInfo(HttpServletRequest request,Integer jobid){
		request.setAttribute("staffdepartinfo",staffservice.selByJobId(jobid));
		return "admin/DistributeDepartmentInfo";
	}

	@RequestMapping("InsertStaffRoleInfo")
	@ResponseBody
	public String insertStaffRoleInfo(Staff staff,@RequestParam("roleid") Integer roleid){
		return staffservice.insStaffRoleInfo(staff,roleid)>0 ? "success":"fail";
	}

	@RequestMapping("ToInsertStaffRoleInfo")
	public String toInsertStaffRoleInfo(HttpServletRequest request,Integer roleid){
		request.setAttribute("roleinfo",roleService.selRoleById(roleid));
		return "admin/InsertStaffRoleInfo";
	}

	@RequestMapping("UpStaffRole")
	@ResponseBody
	public String upStaffRole(Integer jobid){
		return staffservice.updateUpStaffRoleByJobId(jobid)>0 ? "success":"fail";
	}

	@RequestMapping("DownStaffRole")
	@ResponseBody
	public String downStaffRole(Integer jobid){
		return staffservice.updateDownStaffRoleByJobId(jobid)>0 ? "success":"fail";
	}



	/**
	 * 跳转到修改密码界面
	 * @return
	 */
	@RequestMapping("ToChangePassword")
	public String toChangePassword(){
		return "ChangePassword";
	}

	/**
	 * 提交修改密码
	 * @param jobid
	 * @return
	 */
	@RequestMapping("ChangePassword")
	@ResponseBody
	public String ChangePassword(@RequestParam("jobid") Integer jobid,@RequestParam("oldpassword") String oldpassword,@RequestParam("newpassword") String newpassword){
		Subject subject = SecurityUtils.getSubject();
		Staff staff = (Staff) subject.getPrincipal();
		System.out.println(MD5Utils.getMD5Password(oldpassword,staff.getSalt()));
		System.out.println(MD5Utils.getMD5Password(newpassword,staff.getSalt()));
		if(!staff.getPassword().equalsIgnoreCase(MD5Utils.getMD5Password(oldpassword,staff.getSalt()))){
			return "pwdError";
		}else{
			int index = staffservice.updateStaffPasswordByJobId(jobid,MD5Utils.getMD5Password(newpassword,staff.getSalt()));
			return index>0 ? "success":"fail";
		}

	}


	@RequestMapping("FindStaffInfoByJobId")
	@ResponseBody
	public Staff findStaffInfoByJobId(Integer jobid){
		return staffservice.selStaffInfoByJobId(jobid);
	}

	/**
	 * 获取除管理员之外的工号
	 * @return
	 */
	@RequestMapping("FindAllStaffJobId")
	@ResponseBody
	public List<Integer> findAllStaffJobId(){
		return staffservice.selAllStaffJobId();
	}


	/**
	 * 激活之后跳转到登录界面
	 * @param activecode
	 * @return
	 */
	@RequestMapping("ActiveStaff")
	@Operation(name = "激活")
	public String activeStaff(String activecode,Integer jobid){
		Staff receiver = staffservice.selByJobId(jobid);
		int index = staffservice.updateByactiveStaffByJobId(jobid,activecode);
		if(index>0){//已激活
			newsService.insNews(new News("系统通知",null, DateFormatUtil.getCurrentTime(),
					"激活账号成功",receiver.getAccount(),receiver.getDepartid(),null,"未读",null));
//			账号激活默认角色为普通员工

			Role role  = staffservice.selExistRoleByJobId(jobid); //先从数据中获取该用户的角色信息，如果有则不再进行权限角色的默认分配
			if(role == null) {
				int resultOfAuthorization = systemInfoService.insStaffRole(jobid);
				if(resultOfAuthorization>0){//授权成功
					newsService.insNews(new News("系统通知",null, DateFormatUtil.getCurrentTime(),
							"账号授权成功",receiver.getAccount(),receiver.getDepartid(),null,"未读",null));
					return "redirect:/ActiveSuccess.jsp";
				}else{//授权失败
					newsService.insNews(new News("系统通知",null, DateFormatUtil.getCurrentTime(),
							"账号授权失败，请联系管理员!",receiver.getAccount(),receiver.getDepartid(),null,"未读",null));
					return "redirect:/refuse.jsp";
				}
			}
			return "redirect:/ActiveSuccess.jsp";
		}else{//未激活
			newsService.insNews(new News("系统通知",null, DateFormatUtil.getCurrentTime(),
					"激活账号未成功",receiver.getAccount(),receiver.getDepartid(),"待审核","未读",null));
			return "redirect:/refuse.jsp";
		}
	}

	/**
	 * 更改员工工作状态
	 * @param jobid
	 * @return
	 */
	@RequestMapping("ChangeStaffStatus")
	@ResponseBody
	public String changeStaffStatus(@RequestParam("jobid") Integer jobid,@RequestParam("status") String status){

		return staffservice.updateStaffStatus(jobid,status)>0 ? "success":"fail";
	}

	/**
	 * 后台录入员工信息
	 * @param staff
	 * @return
	 */
	@RequestMapping("InsertStaffInfo")
	@ResponseBody
	public String insertStaffInfo(Staff staff){
		int index = staffservice.insStaff(staff);
		return index>0 ? "success":"fail";
	}

	/**
	 * 在员工信息界面点击添加按钮跳转到相关信息界面
	 * @return
	 */
	@RequestMapping("ToInsertStaffInfo")
	public String toInsertStaffInfo() {
		return "admin/InsertStaffInfo";
	}


	/**
	 * 导出excel
	 * @param batchjobid
	 * @return
	 */
	@RequestMapping("exportStaffExcel")
	@ResponseBody
	@RequiresPermissions("staff:excelexport")
	public Map<String, Object> exportSalaryExcel(String batchjobid){
		System.out.println("接收到的id"+batchjobid);
		String[] idlist = batchjobid.split(",");
		List<Staff> staffList = new ArrayList<Staff>();
		for (String s : idlist) {
			Integer jobid = Integer.valueOf(s);
			staffList.add(staffservice.selByJobId(jobid));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "");
		result.put("data",staffList);
		return result;
	}

	/**
	 * 第一个echarts报表信息 男女比例
	 * @return
	 */
	@RequestMapping("selCompanyInfoationOfSex")
	@ResponseBody
	public Map<String, Object> selCompanyInfoationOfSex(){
		return staffservice.selCompanyInfoationOfSex();
	}

	@RequestMapping("ToBasicInfo")
	public String ToBasicInfo(Integer jobid){
//		System.out.println("接收到的工号为："+jobid);
		return "basic_info";
	}

	/**
	 * 点击个人信息查看，跳转到个人信息界面
	 * @return
	 */
	@RequestMapping("ToFindSelfStaffInfo")
	public String toFindSelfStaffInfo(){
		return "StaffSelfInfo";
	}

	/**
	 * 修改个人信息
	 * @param staff
	 * @return
	 */
	@RequestMapping("UpdateStaffSelfInfo")
	@ResponseBody
	public String updateStaffSelfInfo(Staff staff){
		return staffservice.updateStaffSelfInfo(staff)>0 ? "success":"fail";
	}


	/**
	 * 查看个人信息  promise获取更多个人信息  后期根据需求加
	 * @return
	 */
	@RequestMapping("FindSelfStaffInfo")
	@ResponseBody
	public Map<String, Object> findSelfStaffInfo(){
		Staff self = (Staff) SecurityUtils.getSubject().getPrincipal();
//		System.out.println(self);
		List<Staff> data = new ArrayList<>();
		data.add(self);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", data);
		result.put("count", data.size());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}

	/**
	 * 首页查看所有员工信息
	 * @return
	 */
	@RequestMapping("ToFindAllStaffInfo")
	public String AllStaff_Info() {
		return "admin/AllStaff_Info";
	}
	
	@RequestMapping("findAllStaffInfo1")
	@ResponseBody
	@RequiresPermissions("staff:query")
	public Map<String, Object> findAllStaffInfo1(int limit,int page,Staff staff){
//		System.out.println("分页当前页数为："+page+"分页的每页显示记录数为"+limit);
		PageInfo pi = staffservice.selAllByPage1(page,limit,staff);
		//以下操作
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", pi.getList());
		result.put("count", pi.getPagerecord());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}
	
	
	/**
	 * 分页查询，后期改进为条件查询
	 * @param
	 * @return
	 */
//	@RequestMapping("findAllStaffInfo")
//	@ResponseBody
//	public Map<String, Object> findAllStaffInfo(int page,int limit)
//	{
////		System.out.println("分页当前页数为："+page+"分页的每页显示记录数为"+limit);
//		int pagecurrent = 1;
//		int pagesize = 5;
//		Map<String, Object>	result = new HashMap<String, Object>();
//		PageInfo pi = staffservice.selAllByPage(page,pageInfo.getPagesize());
//		System.out.println("控制器中"+pageInfo.getPagerecord());
////		System.out.println(pi);
//		result.put("code", 0);
//		result.put("msg", "");
//		result.put("count", pi.getPagerecord());
//		result.put("data", pi.getList());
//
//		return result;
//	}
	
	@RequestMapping("ToUpdateStaff")
	@RequiresPermissions("staff:update")  /*在jsp页面添加了shiro:hasPermission标签时也会默认进入realm取数据*/
	public String EditStaff(int jobid,HttpServletRequest request) {
		
		Staff _staff = staffservice.selByJobid(jobid);
		request.setAttribute("editstaffinfo", _staff);
		return "admin/Staff_Edit";
	}

	/**
	 * 员工编辑界面点击提交之后插入数据
	 * @param staff
	 * @return
	 */
	@RequestMapping("UpdateStaff")
	@RequiresPermissions("staff:update")
	@ResponseBody
	public String UpdateStaff(Staff staff) {
		
	    int index = staffservice.updateStaff(staff);
//	    这里根据插入数据的结果返回对应信息  前端的提交方式要改为异步请求  用ajax请求来获取相应json数据并作出交互
		/*if(index>0){

		}else{

		}*/
		if(index > 0){//插入数据成功
			return "success";
		}else{ //插入数据失败
			return "fail";
		}

	}

	@RequestMapping("hello")
	@ResponseBody
	public Staff testGetSubjectInfo(){
		return (Staff) SecurityUtils.getSubject().getPrincipal();
	}
}
