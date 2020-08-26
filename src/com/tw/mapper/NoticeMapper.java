package com.tw.mapper;

import java.util.List;

import com.tw.pojo.HotPost;
import com.tw.pojo.Notice;
import org.apache.ibatis.annotations.Param;

public interface NoticeMapper {

	Integer insNotice(Notice notice);

	List<Notice> selByDate(String format);

	List<Notice> selAllNoticeByDate();

	Notice selById(Integer noticeid);


    long selCount();

	List<HotPost> selByPage(@Param("start") int i,@Param("limit") int limit);

	Long selCountById(Long id);

    Notice selNoticeById(Integer noticeid);
}
