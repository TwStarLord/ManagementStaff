package com.tw.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tw.pojo.Areas;
import com.tw.pojo.Chuchai;
import com.tw.pojo.Notice;
import com.tw.pojo.Smile;
import com.tw.pojo.Staff;
import com.tw.service.NoticeService;
import com.tw.service.StaffService;
import com.tw.service.impl.NoticeServiceImpl;

import sun.awt.OSInfo;

/**
 * 此控制器专门用来从主页发起的页面请求
 * @author tw
 *
 */
@Controller
public class DispactherController {
	@Resource
	private StaffService staffservice;
	
	@Resource
	private NoticeService noticeService;
	
	@RequestMapping("onlineCount")
	@ResponseBody
	public int onlineStaff(HttpSession session) {
		ServletContext context = session.getServletContext();
		Map<String, Object> result = new HashMap<String, Object>();
		HashSet<HttpSession> sessionSet = (HashSet<HttpSession>) session.getServletContext().getAttribute("sessionSet");
		if(sessionSet!=null) {
			for (HttpSession httpSession : sessionSet) {
				System.out.println(httpSession.getAttribute("staffinfo"));
		}
			return sessionSet.size();
		}else {
			return 0;
		}
		
	}
	
	/**
	 * 主页点击部门信息查看所有部门信息，该功能使用折叠面板
	 * @return
	 */
	@RequestMapping("ToFindAllDepartmentInfo")
	public String ToFindAllDepartmentInfo() {
		return "admin/Department_Info";
	}
	
	/**
	 * 在公告显示界面添加评论，以弹窗形式显示
	 * @param noticeid
	 * @param request
	 * @return
	 */
	@RequestMapping("ToUpdateComment")
	public String ToUpdateComment(Integer noticeid,HttpServletRequest request) {
		Notice result = noticeService.selById(noticeid);
		request.setAttribute("noticeInfo", result);
		System.out.println(result);
		return "staff/UpdateComment";
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("testdate")
	@ResponseBody
	public List<Notice> Date() {
//		List<Notice> list = noticeService.selByDate(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
//		for (Notice notice : list) {
//			System.out.println(notice);
//		}
//		return list;
		List<Notice> list = noticeService.selAllNoticeByDate();
		for (Notice notice : list) {
			System.out.println(notice);
		}
		return list;
		
	}
	
	
	@RequestMapping("ToFindAllNotice")
	public String ToFindAllNotice() {
		return "AllNotice_Info";
	}

	
	/**
	 * 主页上点击发布公告进行公告编辑
	 * @return
	 */
	@RequestMapping("ToInsertNotice")
	public String toInsertNotice() {
		System.out.println("====================我进的是ToInsertNotice============================");
		return "admin/AddNotice";
	}
	
	/**
	 * 字节数组转换序列化问题，存在非16进制无法完成序列化转换的操作。
	 * @return
	 */
	@RequestMapping("justdo")
	@ResponseBody
	public String justdo() 
	{
	
		List<Smile> list =  staffservice.selAllSmile();
		for (Smile smile : list) {
			smile.setId(smile.getId()+5);
		}
		String string = list.toString();
		byte[] bytes = string.getBytes();
		
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		
		try {
			oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("f:/smile/a.txt"))));
			ois = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(bytes)));
			Object readObject = ois.readObject();
	        oos.writeObject(readObject);
	        oos.flush();
			
		} catch (IOException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}finally {
			if(ois!=null) {
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(oos!=null) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		        
		    
		return "success";
	}
	
	
	@RequestMapping("testimagepath")
	@ResponseBody
	public String testimagepath(HttpServletRequest request,HttpServletResponse response) {
		
		String path = request.getServletContext().getRealPath("resources/files");
		
		File file = new File(path);
		
		System.out.println(file.listFiles());
		
		return path;
		
	}
	
	/**
	 * 主页点击薪水信息，查看所有员工薪资信息
	 * @return
	 */
	@RequestMapping("ToAllSalary_Info")
	public String ToAllSalary_Info() {
		return "admin/AllSalary_Info";
	}
	/**
	 * 添加请假信息（可由admin和satff共同完成）
	 * @return
	 */
	@RequestMapping("ToInsertQingjiaInfo")
	public String ToInsertQingjiaInfo() {
		return "staff/InsertQingjiaInfo";
	}
	/**
	 * 在主页点击请假信息跳转到信息界面
	 * @return
	 */
	@RequestMapping("TofindAllQingjia")
	public String TofindAllQingjia() {
		return "admin/AllQingjia_Info";
	}
	
	/**
	 * 在出差信息界面点击添加按钮跳转到相关信息界面
	 * @return
	 */
	@RequestMapping("ToInsertChuchaiInfo")
	public String ToInsertChuchaiInfo() {
		return "admin/InsertChuchaiInfo";
	}
	/**
	 * 首页中查看所有人得出差信息
	 * @return
	 */
	@RequestMapping("TofindAllChuchai")
	public String TofindAllChuchai() {
		return "admin/AllChuchai_Info";
	}
	/**
	 * 首页中查看基本信息跳转
	 * @return
	 */
	@RequestMapping("/basic_info")
	public String basicinfo() {
		return "basic_info";
	}
	/**
	 * 首页查看所有员工信息
	 * @return
	 */
	@RequestMapping("/ToAllStaff_Info")
	public String AllStaff_Info() {
		return "admin/AllStaff_Info";
	}
	/**
	 * 首页查看所有的文件信息
	 * @return
	 */
	@RequestMapping("/TofindAllFile")
    public String TofindAllFile() {
    	return "admin/AllFile_Info";
    }
	/**
	 * 首页点击文件上传，跳转到文件上传界面
	 */
	@RequestMapping("/ToUploadFile")
	public String ToUploadFile() {
		return "UploadFile";
	}
	
	
}
