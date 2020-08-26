package com.tw.service;

import com.tw.pojo.Chuchai;
import com.tw.pojo.PageInfo;

import java.util.Map;

public interface SystemInfoService {

    Map<String, Object> selBrowserInfo();

    Map<String, Object> selCompanyInfo();

    PageInfo selAllByPage(int page, int limit);

    PageInfo selAllPendingByPage(int page, int limit);

    int deleteNews(String[] idList);

    int updateAllNewsReadStatus();

    int updateSelectedNewsReadStatus(String[] idList);

    Long getPendingNewsCount() throws Exception;

    int insStaffRole(Integer jobid);
}
