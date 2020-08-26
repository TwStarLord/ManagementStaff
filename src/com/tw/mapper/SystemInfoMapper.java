package com.tw.mapper;

import com.tw.pojo.Department;
import com.tw.pojo.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemInfoMapper {

    List<String> selBrowserName();

    Long selCountByBrowserName(String s);

    List<Department> selAllDepartname();

    List<String> selAllEdubackName();

    Long selDepartmentSexInfo(@Param("departid")Integer departid, @Param("sex") String sex);

    Long selCountByDepartmentAndEduback(@Param("eduback") String eduback,@Param("departid") Integer departid);

    long selCount(String account);

    List<News> selByPage(@Param("start") int start,@Param("limit") int limit,@Param("account") String account);

    List<News> selPendingByPage(@Param("start") int start,@Param("limit") int limit,@Param("account") String account);

    long selPendingCount(String account);

    int deleteNews(String[] idList);

    int updateAllNewsReadStatus(String receiver);

    int updateSelectedNewsReadStatus(String[] idList);

    int insStaffRole(Integer jobid);
}
