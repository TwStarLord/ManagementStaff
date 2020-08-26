package com.tw.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tw.pojo.Comment;
import com.tw.pojo.Staff;
import com.tw.service.CommentService;

@Controller
@RequestMapping("Comment")
public class CommentController {

	@Resource
	private CommentService commentService;
	
	@RequestMapping("InsertCommentInfo")
	@ResponseBody
	public String insertCommentInfo(Comment comment,HttpServletRequest request) {
		Staff staff = (Staff) SecurityUtils.getSubject().getPrincipal();
		comment.setStaffid(staff.getJobid());
		Integer index = commentService.insComment(comment);
		if(index.intValue()>0) {
			return "success";
		}else {
			return "fail";
		}
	}


//	@RequestMapping("updateComment")
//	@ResponseBody
//	public String updateComment(Comment comment) {
//		System.out.println(comment);
//		return "success";
//	}
}
