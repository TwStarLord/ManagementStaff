package com.tw.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.tw.pojo.HotPost;
import com.tw.pojo.PageInfo;
import org.springframework.stereotype.Service;

import com.tw.mapper.NoticeMapper;
import com.tw.pojo.Notice;
import com.tw.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Resource
	private NoticeMapper noticeMapper;
	
	@Override
	public Integer insNotice(Notice notice) {
		return noticeMapper.insNotice(notice);
	}

	@Override
	public List<Notice> selByDate(String format) {
		return noticeMapper.selByDate(format);
	}

	@Override
	public List<Notice> selAllNoticeByDate() {
		return noticeMapper.selAllNoticeByDate();
	}

	@Override
	public Notice selById(Integer noticeid) {
		return noticeMapper.selById(noticeid);
	}

	@Override
	public PageInfo selAllByPage(int page, int limit) {
		PageInfo pageInfo = new PageInfo();
		//设置pageinfo信息
		pageInfo.setPagecurrent(page);
		pageInfo.setPagesize(limit);
		long count = noticeMapper.selCount();
		pageInfo.setPagerecord(count);
		pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误
		List<HotPost> hotPostList = noticeMapper.selByPage((page - 1) * limit, limit);
//		设置点击次数
		for(HotPost hotPost:hotPostList){
			hotPost.setCollectCount(noticeMapper.selCountById(hotPost.getId()));
		}
		pageInfo.setList(hotPostList);

		return pageInfo;
	}

	@Override
	public Notice selNoticeById(Integer noticeid) {
		return noticeMapper.selNoticeById(noticeid);
	}

}
