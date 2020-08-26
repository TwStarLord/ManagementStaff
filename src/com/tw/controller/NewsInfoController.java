package com.tw.controller;


import com.tw.annotation.Operation;
import com.tw.pojo.Kaoqin;
import com.tw.pojo.News;
import com.tw.pojo.Staff;
import com.tw.service.NewsService;
import com.tw.service.StaffService;
import com.tw.utils.DateFormatUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Operation(name = "消息管理")
@Controller
@RequestMapping("NewsInfo")
public class NewsInfoController {


    @Resource
    private NewsService newsService;

    @Resource
    private StaffService staffService;


    @Operation(name = "申请考勤补卡")
    @RequestMapping("InsertNewsOfChangeKaoqinStatus")
    @ResponseBody
    public String insertNewsOfChangeKaoqinStatus(Kaoqin kaoqin){
        Staff receiver = staffService.selKaoqinManager();
        return newsService.insKaoqinChangeStatusNews(new News(kaoqin.getName(), kaoqin.getDepartid(), DateFormatUtil.getCurrentTime(),
                "考勤补卡", receiver.getName(), receiver.getDepartid(), "待审核", "未读",String.valueOf(kaoqin.getId())))>0 ? "success":"fail";
    }



    @RequestMapping("GetPendingNews")
    @ResponseBody
    public Long GetPendingNews(Integer jobid){
        return newsService.getPendingNews(jobid);
    }
}
