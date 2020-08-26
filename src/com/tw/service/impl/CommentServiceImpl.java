package com.tw.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tw.mapper.CommentMapper;
import com.tw.pojo.Comment;
import com.tw.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Resource
	private CommentMapper commentMapper;
	
	@Override
	public Integer insComment(Comment comment) {
		String oldcomment = comment.getComment();
//		评论内容过滤，只做测试
		comment.setComment(oldcomment.replaceAll("[呵呵]","**"));
		return commentMapper.insComment(comment);
	}

}
