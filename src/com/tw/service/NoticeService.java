package com.tw.service;

import java.util.List;

import com.tw.pojo.Notice;
import com.tw.pojo.PageInfo;

public interface NoticeService {

	Integer insNotice(Notice notice);

	List<Notice> selByDate(String format);

	List<Notice> selAllNoticeByDate();

	Notice selById(Integer noticeid);

    PageInfo selAllByPage(int page, int limit);

    Notice selNoticeById(Integer noticeid);
}
