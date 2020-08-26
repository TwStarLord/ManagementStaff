package com.tw.controller;

import com.tw.annotation.Operation;
import com.tw.exception.KaoqinException;
import com.tw.pojo.*;
import com.tw.service.KaoqinService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Operation(name = "考勤操作")
@Controller
@RequestMapping("Kaoqin")
public class KaoqinInfoController {

    @Resource
    private KaoqinService kaoqinService;

    @RequestMapping("ToFindSelfKaoqinInfo")
    public String toFindSelfKaoqinInfo(){
        return "staff/StaffSelfKaoqinInfo";
    }

    @RequestMapping("BatchDeleteKaoqinInfo")
    @ResponseBody
    @Operation(name = "批量删除考勤信息")
    @RequiresPermissions("kaoqin:batchdelete")
    public String deleteBatchKaoqinInfo(String batchid){
//        return kaoqinService.deleteBatchKaoqinInfoById(id)>0 ? "success":"fail";
        return "success";
    }

    @RequestMapping("DeleteKaoqinInfo")
    @ResponseBody
    @Operation(name = "删除考勤信息")
    @RequiresPermissions("kaoqin:delete")
    public String deleteKaoqinInfo(Long id){
//        return kaoqinService.deleteKaoqinInfoById(id)>0 ? "success":"fail";
        return "success";
    }


    @RequestMapping("ChangeKaoqinApplyStatus")
    @ResponseBody
    public String changeKaoqinApplyStatus(Kaoqin kaoqin,@RequestParam("newsid") Long newsid){
//        这里的 kaoqin 包含的信息为id checkstatus 此id为kaoqinrecord的id值
        return kaoqinService.updateKaoqinApplyStatus(kaoqin,newsid)>0 ? "success":"fail";
    }

    /**
     * 在考勤信息界面点击添加按钮跳转到相关信息界面
     * @return
     */
    @RequestMapping("ToInsertKaoqinInfo")
    public String toInsertKaoqinInfo() {
        return "admin/InsertKaoqinInfo";
    }

    @RequestMapping("InsertKaoqinInfo")
    @ResponseBody
    public String insertKaoqinInfo(Kaoqin kaoqin) {
        int index = kaoqinService.insertKaoqinInfo(kaoqin);
        if(index > 0) {
            return "success";
        }else {
            return "fail";
        }
    }


    /**
     * 导出excel
     * @param batchid
     * @return
     */
    @RequestMapping("exportKaoqinExcel")
    @ResponseBody
    @RequiresPermissions("kaoqin:excelexport")
    public Map<String, Object> exportSalaryExcel(String batchid){
        System.out.println("接收到的id"+batchid);
        String[] idlist = batchid.split(",");
        List<Kaoqin> kaoqinList = new ArrayList<Kaoqin>();
        for (String s : idlist) {
            Integer jobid = Integer.valueOf(s);
            kaoqinList.add(kaoqinService.selByKaoqinInfoId(jobid));
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("msg", "");
        result.put("data",kaoqinList);
        return result;
    }

    /**
     * 补卡按钮申请补卡操作
     * @param id
     * @return
     */
    @Operation(name = "跳转到补卡操作")
    @RequestMapping("ToChangeKaoqinStatus")
    public String toChangeStatus(HttpServletRequest request,Integer id){
        request.setAttribute("changeKaoqinStatusInfo",kaoqinService.selKaoqinRecordById(id));
        return "ChangeStatus";
    }

    /**
     * 员工考勤信息界面点击编辑按钮，弹窗打开编辑界面
     * @return
     */
    @RequestMapping("ToUpdateKaoqinInfo")
    @RequiresPermissions("kaoqin:update")
    public String toUpdateKaoqinInfo(Integer id, HttpServletRequest request){

        request.setAttribute("editkaoqininfo",kaoqinService.selById(id));
        return "admin/Kaoqin_Edit";
    }

    /**
     * 主界面打卡
     * @return
     */
    @Operation(name = "考勤打卡")
    @RequestMapping("KaoqinCheck")
    @ResponseBody
    public Map<String,Object> kaoqinCheck(/*@Param("jobid") Integer jobid,@Param("name") String name*/) {
        Map<String,Object> result = new HashMap<>();
        String msg = "";
        try {
            msg = kaoqinService.insertkaoqinRecordInfo();
        } catch (KaoqinException e) {
            result.put("code",500);
            result.put("msg",e.getMessage());
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result.put("code",200);
        result.put("msg",msg);
        return result;
    }

    /**
     * 在主页点击请假信息跳转到信息界面
     * @return
     */
    @RequestMapping("ToFindAllKaoqinInfo")
    public String TofindAllKaoqinInfo() {
        return "admin/AllKaoqin_Info1";
    }

    @RequestMapping("FindSelfKaoqinInfo")
    @ResponseBody
    public Map<String, Object> findSelfKaoqinInfo(int limit, int page){
        Staff user = (Staff)SecurityUtils.getSubject().getPrincipal();
        PageInfo pi = kaoqinService.selSelfKaoqinInfo(page,limit,user.getJobid());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", pi.getList());
        result.put("count", pi.getPagerecord());
        result.put("msg", "");
        result.put("code", 0);
        return result;
    }

    /**
     * 查询所欲考勤信息（条件查询）
     * @param limit
     * @param page
     * @param kaoqin
     * @return
     */
    @Operation(name = "查看考勤信息")
    @RequestMapping("FindAllKaoqinInfo")
    @ResponseBody
    public Map<String, Object> findAllKaoqinInfo(int limit, int page, Kaoqin kaoqin){
        PageInfo pi = kaoqinService.selAllByPage(page,limit,kaoqin);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", pi.getList());
        result.put("count", pi.getPagerecord());
        result.put("msg", "");
        result.put("code", 0);
        return result;
    }

    @RequestMapping("FindSelfKaoqinRecord")
    @ResponseBody
    public Map<String, Object> findSelfKaoqinRecord(int limit, int page){
        Staff user = (Staff) SecurityUtils.getSubject().getPrincipal();
        PageInfo pi = kaoqinService.selSelfKaoqinRecord(page,limit,user.getJobid());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", pi.getList());
        result.put("count", pi.getPagerecord());
        result.put("msg", "");
        result.put("code", 0);
        return result;
    }


    @RequestMapping("FindAllKaoqinRecord")
    @ResponseBody
    public Map<String, Object> findAllKaoqinRecord(int limit, int page){
        PageInfo pi = kaoqinService.selAllKaoqinRecordByPage(page,limit);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", pi.getList());
        result.put("count", pi.getPagerecord());
        result.put("msg", "");
        result.put("code", 0);
        return result;
    }

}
