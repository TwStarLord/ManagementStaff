package com.tw.mapper;

import com.tw.pojo.Comment;

public interface CommentMapper {

	Integer insComment(Comment comment);
	
	Integer upaComment(Comment comment);
}
