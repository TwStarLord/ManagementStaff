package com.tw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tw.pojo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tw.service.CollectsService;
import com.tw.service.NoticeService;
import com.tw.service.StaffService;

@Controller
@RequestMapping("Notice")
public class NoticeInfoController {

	@Resource
	private NoticeService noticeService;
	
	@Resource
	private StaffService staffService;
	
	@Resource
	private CollectsService collectsService;


	/**
	 * 点击详情详情获取公告的详细信息
	 * @param noticeid
	 * @return
	 */
	@RequestMapping("FindNoticeByNoticeId")
	public String FindNoticeByNoticeId(HttpServletRequest request,Integer noticeid){
		System.out.println("接收到的公告id为："+noticeid);
		request.setAttribute("noticeinfo",noticeService.selNoticeById(noticeid));
		return "NoticeDetail";
	}

	@RequestMapping("GetHotPostInfo")
	@ResponseBody
	public Map<String, Object> getHotPostInfo(int limit,int page){
		PageInfo pi = noticeService.selAllByPage(page,limit);
		//以下操作
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", pi.getList());
		result.put("count", pi.getPagerecord());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}

	/**
	 * 首页点击发布公告跳转到增加公告界面
	 * @return
	 */
	@RequestMapping("ToInsertNoticeInfo")
	public String insertNoticeInfo(){
		return "admin/AddNotice";
	}

	/**
	 * 首页点击查看公告跳转到公告界面
	 * @return
	 */
	@RequestMapping("ToFindAllNoticeInfo")
	public String findAllNoticeInfo(){
		return "AllNotice_Info";
	}
	
	@RequestMapping("findAllNotice")
	@ResponseBody
	public Map<String, Object> findAllNotice(HttpServletRequest request){
		//获取session中当前登陆者的信息，然后将根据其中的jobid查询该用户所收藏的所有公告
		Staff staff = (Staff) request.getSession().getAttribute("staffinfo");
		List<Collects> staffNotice = (List<Collects>) collectsService.selByJobId(staff.getJobid());
		//所有公告信息
		List<Notice> allNotice = noticeService.selAllNoticeByDate();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("staffNotice", staffNotice);
		result.put("allNotice", allNotice);
		
		return result;
	}
	
	/**
	 * 发布公告
	 * @param notice
	 * @return
	 */
	@RequestMapping("InsertNoticeInfo")
	@ResponseBody
	public String insertNoticeInfo(Notice notice){
		System.out.println("====================我进的是InsertNoticeInfo============================");
		Integer index = noticeService.insNotice(notice);
		if(index.intValue()>0) {
			return "success";
		}else {
			return "fail";
		}
	}
}
