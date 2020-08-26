package com.tw.service.impl;

import com.tw.mapper.SystemInfoMapper;
import com.tw.pojo.BrowserInfo;
import com.tw.pojo.Department;
import com.tw.pojo.PageInfo;
import com.tw.pojo.Staff;
import com.tw.service.SystemInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemInfoServiceImpl implements SystemInfoService {

    @Resource
    private SystemInfoMapper systemInfoMapper;


    /**
     * 查询访客浏览器信息
     * @return
     */
    @Override
    public Map<String, Object> selBrowserInfo() {
        List<String> browserNameList = systemInfoMapper.selBrowserName();
        List<BrowserInfo> browserInfoList = new ArrayList<>();
        Map<String,Object> result = new HashMap<>();
        for (String s:browserNameList){
            browserInfoList.add(new BrowserInfo(systemInfoMapper.selCountByBrowserName(s),s));
        }
        result.put("browserNameList",browserNameList);
        result.put("browserInfoList",browserInfoList);
        return result;
    }

    @Override
    public Map<String, Object> selCompanyInfo() {
        Map<String,Object> result = new HashMap<>();
        List<Department> departmentList = systemInfoMapper.selAllDepartname();
//        List<String> edubackList  = systemInfoMapper.selAllEdubackName();  这里还是用前台给定的值比较好
        List<String> edubackList = new ArrayList<>();
        edubackList.add("博士");
        edubackList.add("硕士");
        edubackList.add("本科");
        edubackList.add("其他");
//        List<String> legendList = new ArrayList<>();
//        legendList.add("男");
//        legendList.add("女");
//          legendList.addAll(2,edubackList);
//        result.put("legendList",legend);
        //容器信息
        List<String> departnameList = new ArrayList<>();
        List<Long> maleCountList = new ArrayList<>();
        List<Long> femaleCountList = new ArrayList<>();
        List<List<Long>> edubackCountList = new ArrayList<>();
        for (Department department:departmentList){
            departnameList.add(department.getDepartname());
            maleCountList.add(systemInfoMapper.selDepartmentSexInfo(department.getDepartid(),"男"));
            femaleCountList.add(systemInfoMapper.selDepartmentSexInfo(department.getDepartid(),"女"));
        }
        result.put("departnameList",departnameList);
        //性别信息
        result.put("maleCountList",maleCountList);
        result.put("femaleCountList",femaleCountList);
        List<Long> count = null;
        for (String eduback:edubackList){
            count = new ArrayList<>();
            for(Department department:departmentList){
                count.add(systemInfoMapper.selCountByDepartmentAndEduback(eduback,department.getDepartid()));
            }
            edubackCountList.add(count);
        }
        //学历信息
        result.put("edubackCountList",edubackCountList);
        return result;
    }

    @Override
    public PageInfo selAllByPage(int page, int limit) {
        PageInfo pageInfo = new PageInfo();
        Subject subject = SecurityUtils.getSubject();
        Staff staff = (Staff)subject.getPrincipal();
        //设置pageinfo信息
        pageInfo.setPagecurrent(page);
        pageInfo.setPagesize(limit);
        long count = systemInfoMapper.selCount(staff.getAccount());
        pageInfo.setPagerecord(count);
        pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误
        pageInfo.setList(systemInfoMapper.selByPage(((page-1)*limit),limit,staff.getAccount()));

        return pageInfo;
    }

    @Override
    public PageInfo selAllPendingByPage(int page, int limit) {
        PageInfo pageInfo = new PageInfo();
        Subject subject = SecurityUtils.getSubject();
        Staff staff = (Staff)subject.getPrincipal();
        //设置pageinfo信息
        pageInfo.setPagecurrent(page);
        pageInfo.setPagesize(limit);
        long count = systemInfoMapper.selPendingCount(staff.getAccount());
        pageInfo.setPagerecord(count);
        pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误
        pageInfo.setList(systemInfoMapper.selPendingByPage(((page-1)*limit),limit,staff.getAccount()));

        return pageInfo;
    }

    @Override
    public int deleteNews(String[] idList) {
        return systemInfoMapper.deleteNews(idList);
    }

    @Override
    public int updateAllNewsReadStatus() {
        //获取身份信息，只修改接收者为自己的消息为全部已读
        Subject subject = SecurityUtils.getSubject();
        Staff staff = (Staff) subject.getPrincipal();
        return systemInfoMapper.updateAllNewsReadStatus(staff.getName());
    }

    @Override
    public int updateSelectedNewsReadStatus(String[] idList) {
        return systemInfoMapper.updateSelectedNewsReadStatus(idList);
    }

    @Override
    public Long getPendingNewsCount() throws Exception{
        Subject subject = SecurityUtils.getSubject();
        Staff staff = (Staff)subject.getPrincipal();
        return systemInfoMapper.selPendingCount(staff.getAccount());
    }

    @Override
    public int insStaffRole(Integer jobid) {
        return systemInfoMapper.insStaffRole(jobid);
    }

}
