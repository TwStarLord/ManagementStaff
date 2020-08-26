package com.tw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.Resources;

import com.tw.mapper.*;
import com.tw.pojo.*;
import com.tw.service.ProvinceCityAreaService;
import com.tw.utils.DateFormatUtil;
import com.tw.utils.MD5Utils;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.ResolverUtil.IsA;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.tw.exception.StaffException;
import com.tw.service.StaffService;
import sun.security.provider.MD5;

//底层源码，使用xml解析技术来构建
//InputStream is = Resources.getResourceAsStream("mybatis.xml");
//XMLConfigBuilder parser = new XMLConfigBuilder(is);environment  properties
//Configuration config = parser.parse();
//DefaultSqlSessionFactory factory = new DefaultSqlSessionFactory(config);
//SqlSession session = factory.openSession();

@Service
public class StaffServiceImpl implements StaffService {
	
	//通过get/set方法进行注入
	@Resource
	private StaffMapper staffMapper;

	@Resource
	private NewsMapper newsMapper;

	@Resource
	private KaoqinMapper kaoqinMapper;

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private ProvinceCityAreaMapper provinceCityAreaMapper;
	
	public List<Staff> selAll(){
		return staffMapper.selAll();
	}

	/**
	 * 查询公司男女比例信息，用饼图来显示
	 * @return
	 */
	@Override
	public Map<String, Object> selCompanyInfoationOfSex() {
		Map<String,Object> result = new HashMap<>();
		List<String> sexDataList = new ArrayList<>();
		sexDataList.add(("男"));
		sexDataList.add(("女"));
		result.put("sexDataList",sexDataList);
		List<SexInfo> info = new ArrayList<>();
		info.add(staffMapper.selCountBySex("男"));
		info.add(staffMapper.selCountBySex("女"));
		result.put("valueDataList",info);
		return result;
	}

	@Override
	public Staff selByAccountAndPwd(Staff staff) throws StaffException {
//		这里后期改成通过账号获取密码，在通过md5加密过后的字符串与数据库中所获取的密码字符串进行对比，如果不同则抛异常，注意这里比对密码时需要忽略大小写，这是由于数据库的差异而导致的
		
		Staff _staff = staffMapper.selByAccountAndPwd(staff);
	    if(_staff==null) {//该用户不存在
	    	throw new StaffException("用户名或密码不正确!");
	    }else {
	    	return _staff;
	    }
	}

	@Override
	public Staff selByAccount(String account){
		return staffMapper.selByAccount(account);
		
	}

	@Override
	public Staff selByMail(String mail){
		return staffMapper.selByMail(mail);
	}

	
	@Override
	public Integer insStaff(Staff staff) {
//		设置地址
		String[] addressNumber = staff.getAddress().split(",");
		String address = "";
		address += provinceCityAreaMapper.selProByProId(addressNumber[0]).getProvinceName();
		address += provinceCityAreaMapper.selCityById(addressNumber[1]).getCityName();
		address += provinceCityAreaMapper.selAreaById(addressNumber[2]).getAreaName();
		staff.setAddress(address);
//		设置未激活状态
		staff.setStatus("待激活");
//		在这里设置盐和MD5util，然后在mapper中判定参数是否为空
//		staff.setPassword(new MD5(staff.getPassword()));
		staff.setSalt(staff.getAccount());
		Integer index = null;
//		初始密码与盐都是账号
		if(staff.getPassword()!= null){  //注册插入信息，需要通知人事部主管对该员工进行部门分配
			Staff receiver = staffMapper.selKaoqinManager();
			staff.setPassword(MD5Utils.getMD5Password(staff.getPassword(),staff.getAccount()));
			staff.setTimeforjob(DateFormatUtil.getCurrentTime());
			index = staffMapper.insStaff(staff);
			newsMapper.insNews(new News("系统通知",null, DateFormatUtil.getCurrentTime(),"分配部门",
					receiver.getAccount(),receiver.getDepartid(),"待审核",null,String.valueOf(staff.getJobid())
			));
		}else{  //管理员后台录入信息时，默认账号、密码、盐相同
			staff.setPassword(MD5Utils.getMD5Password(staff.getAccount(),staff.getAccount()));
			index = staffMapper.insStaff(staff);
		}
		return index;
	}

	/*@Override
	public PageInfo selAllByPage(int pagecurrent, int pagesize) {
		PageInfo pageinfo = new PageInfo();

		long count = staffMapper.selCount();

		pageinfo.setPagerecord(count);//设置总记录数

		pageinfo.setPagetotal((int)(count%pagesize==0?count/pagesize:count/pagesize+1));//设置总页数

		pageinfo.setPagecurrent(pagecurrent);
		pageinfo.setPagesize(pagesize);

		pageinfo.setPagestart((pagecurrent-1)*pagesize);
		pageinfo.setList(staffMapper.selByPage(pageinfo));//存储当页记录

		System.out.println("service中"+pageinfo.getPagerecord());

		return pageinfo;
	}*/

	@Override
	public Staff selByJobid(int jobid) {
		return staffMapper.selByJobid(jobid);
	}

	@Override
	public int updateStaff(Staff staff) {
		return staffMapper.updateStaff(staff);
	}

	@Override
	public PageInfo selAllByPage1(int page, int limit,Staff staff) {

		Staff getter = (Staff) SecurityUtils.getSubject().getPrincipal();
		PageInfo pageinfo = null;
		if("超级管理员".equals(getter.roleList.get(0).getName())){
			pageinfo = new PageInfo();

			long count = staffMapper.selCount(staff);

			pageinfo.setPagerecord(count);//设置总记录数

			pageinfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//设置总页数

			pageinfo.setPagecurrent(page);
			pageinfo.setPagesize(limit);

			pageinfo.setPagestart((page-1)*limit);

			pageinfo.setList(staffMapper.selByPage(pageinfo.getPagestart(),pageinfo.getPagesize(),staff,null));//存储当页记录

		}else {
			pageinfo = new PageInfo();

			long count = staffMapper.selCount(staff);

			pageinfo.setPagerecord(count);//设置总记录数

			pageinfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//设置总页数

			pageinfo.setPagecurrent(page);
			pageinfo.setPagesize(limit);

			pageinfo.setPagestart((page-1)*limit);

			pageinfo.setList(staffMapper.selByPage(pageinfo.getPagestart(),pageinfo.getPagesize(),staff,getter.getJobid()));//存储当页记录

		}


//		System.out.println("service中"+pageinfo.getPagerecord());
		
		return pageinfo;
		
	}

	@Override
	public List<Smile> selAllSmile() {
		return staffMapper.selAllSmile();
	}

	@Override
	public List<Permission> selPermissionByJobId(Integer jobid) {
		return staffMapper.selPermissionByJobId(jobid);
	}

	@Override
	public Staff selByJobId(Integer jobid) {
		return staffMapper.selByJobId(jobid);
	}

	@Override
	public List<Role> selRoleByJobId(Integer jobid) {
		return staffMapper.selRoleByJobId(jobid);
	}

	@Override
	public Staff selStaffInfoByJobId(Integer jobid) {
		return staffMapper.selStaffInfoByJobId(jobid);
	}

	@Override
	public List<Integer> selAllStaffJobId() {
		return staffMapper.selAllStaffJobId();
	}

	@Override
	public Integer updateByactiveStaffByJobId(Integer jobid,String activecode) {
		return staffMapper.activeStaffByJobId(jobid,activecode);
	}

	@Override
	public Staff selKaoqinManager() {
		return staffMapper.selKaoqinManager();
	}

	@Override
	public int updateStaffStatus(Integer jobid, String status) {
		return staffMapper.updateStaffStatus(jobid,status);
	}

	@Override
	public int updateStaffPasswordByJobId(Integer jobid, String newpassword) {
		return staffMapper.updateStaffPasswordByJobId(jobid,newpassword);
	}

	@Override
	public Role selExistRoleByJobId(Integer jobid) {
		return staffMapper.selExistRoleByJobId(jobid);
	}

	@Override
	public Integer updateStaffSelfInfo(Staff staff) {
		String[] addressNumber = staff.getAddress().split(",");
		String address = "";
		address += provinceCityAreaMapper.selProByProId(addressNumber[0]).getProvinceName();
		address += provinceCityAreaMapper.selCityById(addressNumber[1]).getCityName();
		address += provinceCityAreaMapper.selAreaById(addressNumber[2]).getAreaName();
		staff.setAddress(address);
		return staffMapper.updateStaffSelfInfo(staff);
	}

	@Override
	public Integer updateDownStaffRoleByJobId(Integer jobid) {
		return staffMapper.updateDownStaffRoleByJobId(jobid);
	}

	@Override
	public Integer updateUpStaffRoleByJobId(Integer jobid) {
		return staffMapper.updateUpStaffRoleByJobId(jobid,staffMapper.selDepartidByJobid(jobid));
	}

	@Override
	public Integer insStaffRoleInfo(Staff staff, Integer roleid) {
		List<Role> roleList = staffMapper.selRoleByJobId(staff.getJobid());
		Integer index = null;
		if(roleList!=null){//该角色有角色信息
			index = staffMapper.updateStaffRoleInfo(staff.getJobid(),roleid);
		}else{
			index = staffMapper.insStaffRoleInfo(staff.getJobid(),roleid);
		}
		return index;
	}

	@Override
	public Integer updateStaffDepart(Staff staff) {
		return staffMapper.updateStaffDepartByJobId(staff);
	}

	@Override
	public Integer deleteBatchStaffInfoById(Long id) {
		return null;
	}

	@Override
	public Integer deleteStaffInfoById(Long id) {
		return null;
	}

}
