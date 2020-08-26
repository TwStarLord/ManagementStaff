package com.tw.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tw.shiro.realms.LoginRealm;
import net.sf.ehcache.terracotta.ClusteredInstanceFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.tw.exception.StaffException;
import com.tw.pojo.Staff;
import com.tw.service.StaffService;

import netscape.javascript.JSObject;
import sun.rmi.log.LogOutputStream;

@Controller
@RequestMapping("Login")
//@SessionAttributes("staffinfo")   这样是可行的，但是不知道会不会有什么影响，可以尝试在结合shiro的情况下来进行session存储信息
public class StaffLoginController {

	@Resource
	private StaffService staffservice;

	/**
	 * 请求控制台界面
	 * @return
	 */
	@RequestMapping("ShowConsole")
	public String ShowConsole(){
		return "console";
	}

	/**
	 * 登录成功跳转到index.jsp
	 * @return
	 */
	
	  @RequestMapping("ToIndex")
	  public String toIndex(Model model) {
//	  从shiro的session中获取身份信息
		  Subject subject = SecurityUtils.getSubject();
		  Staff staffinfo = (Staff) subject.getPrincipal();
//		  在model中添加的属性可以被iframe页面获取到是不是因为iframe本就是index.jsp的一部分呢
		  model.addAttribute("staffinfo",staffinfo);
	  	  return "showindex";

	  }
	 
	
	/**
	 *验证码
	 * 暂时先使用该方法来进行验证码的验证，后期有时间在将验证码的验证加入到shiro的验证中去。 2020-4-26
	 * @param verifyCode
	 * @param request
	 * @return
	 */
	@RequestMapping("checkVerifyCode")
	@ResponseBody
	public String checkVerifyCode(String verifyCode,HttpServletRequest request) {
		String vcode = (String) request.getSession().getAttribute("session_vcode");
		if(vcode.equalsIgnoreCase(verifyCode)) {
			return "success";
		}else return "fail";
	}
	
	/**
	 * 跳转到注册界面
	 * @return
	 */
	@RequestMapping("ToRegist")
	public String toRegist() {
		return "regist";
	}
	
	@RequestMapping("selByAccount")
	@ResponseBody
	public int selByAccount(String account) {
		Staff _staff = staffservice.selByAccount(account);
		if(_staff==null) {//账号没有被注册
			return 0;
		}else {//账号已经被注册
			return 1;
		}
	}
	
	@RequestMapping("Regist")
	@ResponseBody
	public String regist(Staff staff) {
//		System.out.println(staff.toString());
//		return "success";
		int index = staffservice.insStaff(staff);
		return index>0 ? "success":"fail";
	}

//	登录提交地址，和applicationContext-shiro.xml中配置的loginurl一致
	@RequestMapping("login")
	public String login(HttpServletRequest request,String remember,String account,String password) throws StaffException{
//	    System.out.println(remember);
//		如果登录失败的话从request域中获取认证异常信息
//		DefaultSecurityManager securityManager = new DefaultSecurityManager();
//		LoginRealm loginRealm = new LoginRealm();
//		securityManager.setRealm(loginRealm);
//		SecurityUtils.setSecurityManager(securityManager);
//		Subject subject = SecurityUtils.getSubject();
//		UsernamePasswordToken token = new UsernamePasswordToken(account,password);
//
//		try {
//			subject.login(token);
//		}catch (UnknownAccountException uae){
//			return "accountError";
//		}catch (IncorrectCredentialsException ice){
//			return "pwdError";
//		}catch (Exception e){
//			System.out.println("未知错误");
//		}


		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
//		System.out.println(exceptionClassName);
//		System.out.println(shiroLoginFailure);  这里输出的是异常的全限定路径名
		//根据shiro返回的异常类路径判断，抛出指定异常信息
		if(exceptionClassName!=null){
			/*if("verifyCodeError".equals(exceptionClassName)){
				throw new StaffException("verifyCodeError ");
			}else */if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				//最终会抛给异常处理器
				throw new StaffException("accountError");
			} else if (IncorrectCredentialsException.class.getName().equals(
					exceptionClassName)) {
				throw new StaffException("pwdError");
			} else {
				throw new StaffException("otherError");//最终在异常处理器生成未知错误
			}
		}
//		此方法不处理请求成功
//		这里需要设置为返回值为void时，shiro会默认在通过认证之后走设定的successUrl 即 /Login/toIndex
//		return "redirect:/Login/ToIndex";
        return "success";
	}

	
	/**
	 * 注册验证账户重名
	 * @param account
	 * @return
	 */
	@RequestMapping("CheckAccount")
	@ResponseBody
	public String registName(String account) {
		Staff _staff = staffservice.selByAccount(account);
		return _staff==null ? "success":"fail";
	}
	
	/**
	 * 验证邮箱，后期加上手机号码、邮箱、账号登录功能
	 * @param mail
	 * @return
	 */
	 @RequestMapping("CheckMail")
	 @ResponseBody
     public String registMail(String mail) {
		Staff _staff = staffservice.selByMail(mail);
		return _staff==null ? "success":"fail";
		
	}
}
