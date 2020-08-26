package com.tw.controller;

import com.tw.pojo.PageInfo;
import com.tw.service.SystemInfoService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("SystemInfo")
public class SystemInfoController {

    @Resource
    private SystemInfoService systemInfoService;




    /**
     * 全部已读
     * @return
     */
    @RequestMapping("ChangeAllNewsReadStatus")
    @ResponseBody
    public String changeAllNewsReadStatus(){
        int index = systemInfoService.updateAllNewsReadStatus();
        return index>0 ? "success":"fail";
    }


    /**
     * 根据选中行改变已读状态
     * @param batchid
     * @return
     */
    @RequestMapping("ChangeReadStatusBySelect")
    @ResponseBody
    public String changeReadStatusBySelect(String batchid){
        String[] idList = batchid.split(",");
        int index = systemInfoService.updateSelectedNewsReadStatus(idList);
        return index>0 ? "success":"fail";
    }


    @RequestMapping("DeleteNews")
    @ResponseBody
    public String deleteNews(String batchid){
        String[] idList = batchid.split(",");
        int index = systemInfoService.deleteNews(idList);
        return index>0 ? "success":"fail";
    }

    /**
     * 获取待处理的消息数量
     * @return
     */
    @RequestMapping("GetPendingNewsCount")
    @ResponseBody
    public Map<String, Object> getPendingNewsCount(){
        Map<String,Object> result = new HashMap<>();
        try {
            Long count = systemInfoService.getPendingNewsCount();
            result.put("code",0);
            result.put("count",count);
            return result;
        } catch (Exception e) {
            result.put("code",1);
            return result;
        }

    }

    /**
     * 全部消息
     * @return
     */
    @RequestMapping("GetPendingNews")
    @ResponseBody
    public Map<String,Object> getPendingNews(int page,int limit){
        PageInfo pi = systemInfoService.selAllPendingByPage(page,limit);
        //以下操作
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", pi.getList());
        result.put("count", pi.getPagerecord());
        result.put("msg", "");
        result.put("code", 0);
        return result;
    }

    /**
     * 全部消息
     * @return
     */
    @RequestMapping("GetAllNews")
    @ResponseBody
    public Map<String,Object> getAllNews(int page,int limit){
        PageInfo pi = systemInfoService.selAllByPage(page,limit);
        //以下操作
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", pi.getList());
        result.put("count", pi.getPagerecord());
        result.put("msg", "");
        result.put("code", 0);
        return result;
    }

    /**
     * 首页点击查看权限信息时跳转到权限信息界面
     * @return
     */
    @RequestMapping("ToFindAllPermissionInfo")
    public String findAllPermissionInfo(){
        return "admin/AllPermission_Info";
    }

    /**
     * 首页点击查看角色信息时跳转到角色信息界面
     * @return
     */
    @RequestMapping("ToFindAllRoleInfo")
    public String findAllRoleInfo(){
        return "admin/AllRole_Info";
    }

    /**
     * 查询系统访客浏览器信息
     * @return
     */
    @RequestMapping("GetBrowserInfo")
    @ResponseBody
    public Map<String,Object> getBrowserInfo(){
        return systemInfoService.selBrowserInfo();
    }

    @RequestMapping("GetCompanyInfo")
    @ResponseBody
    public Map<String, Object> getCompanyInfo(){
        return systemInfoService.selCompanyInfo();
    }
}
