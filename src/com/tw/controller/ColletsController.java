package com.tw.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tw.pojo.Collects;
import com.tw.pojo.Staff;
import com.tw.service.CollectsService;

@Controller
@RequestMapping("Collects")
public class ColletsController {

	@Resource
	private CollectsService collectsService;
	
	@RequestMapping("DeleteCollection")
	@ResponseBody
	public String deleteCollection(Integer noticeid,HttpServletRequest request) {
		System.out.println("接收到的公告编号为"+noticeid.intValue());
		return "success";
		
		/*
		 * Staff staff = (Staff) request.getSession().getAttribute("staffinfo");
		 * Collects collects = new Collects(); collects.setNoticeid(noticeid);
		 * collects.setStaffid(staff.getJobid()); Integer index =
		 * collectsService.deleteCollects(collects); if(index.intValue()>0) { return
		 * "success"; }else { return "fail"; }
		 */
		
	}
	@RequestMapping("InsertCollection")
	@ResponseBody
	public String insertCollection(Integer noticeid,HttpServletRequest request) {
		System.out.println("接收到的公告编号为"+noticeid.intValue());
		return "success";
		/*
		 * Staff staff = (Staff) request.getSession().getAttribute("staffinfo");
		 * Collects collects = new Collects(); collects.setNoticeid(noticeid);
		 * collects.setStaffid(staff.getJobid()); Integer index =
		 * collectsService.insertCollects(collects); if(index.intValue()>0) { return
		 * "success"; }else { return "fail"; }
		 */
	}
}
