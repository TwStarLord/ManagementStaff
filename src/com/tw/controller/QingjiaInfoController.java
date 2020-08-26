package com.tw.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tw.annotation.Operation;
import com.tw.pojo.Kaoqin;
import com.tw.pojo.Staff;
import com.tw.utils.DateFormatUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tw.pojo.PageInfo;
import com.tw.pojo.Qingjia;
import com.tw.service.QingjiaService;

@Operation(name = "请假操作")
@Controller
@RequestMapping("Qingjia")
public class QingjiaInfoController {

	@Resource
	private QingjiaService qingjiaService;


	@RequestMapping("BatchDeleteQingjiaInfo")
	@ResponseBody
	@Operation(name = "批量删除请假信息")
	@RequiresPermissions("qingjia:batchdelete")
	public String deleteBatchQingjiaInfo(String batchid){
//		return qingjiaService.deleteBatchQingjiaInfoById(id)>0 ? "success":"fail";
		return "success";
	}

	@RequestMapping("DeleteQingjiaInfo")
	@ResponseBody
	@Operation(name = "删除请假信息")
	@RequiresPermissions("qingjia:delete")
	public String deleteQingjiaInfo(Long id){
//		return qingjiaService.deleteQingjiaInfoById(id)>0 ? "success":"fail";
		return "success";
	}

	/**
	 * 处理销假申请
	 * @param newsid
	 * @param reply
	 * @return
	 */
	@RequestMapping("HandlerEndQingjiaApply")
	@ResponseBody
	public String handlerEndQingjiaApply(@RequestParam("qingjia_id") Long qingjiaid,@RequestParam("newsid") Long newsid,@RequestParam("reply") String reply){
		return qingjiaService.updateEndQingjiaApply(qingjiaid,newsid,reply)>0 ? "success":"fail";
	}

	/**
	 * 销假申请
	 * @param id
	 * @return
	 */
	@RequestMapping("PostEndHolidayApply")
	@ResponseBody
	public String postEndHolidayApply(@RequestParam("qingjia_id") Long id){
		return qingjiaService.insEndHolidayApply(id) > 0 ?"success":"fail";
	}


	@RequestMapping("ToFindSelfQingjiaInfo")
	public String toFindSelfQingjiaInfo(){
		return "staff/StaffSelfQingjiaInfo";
	}

	/**
	 * 消息界面点击同意或者拒绝同意该申请或者拒绝该申请
	 * @param qingjia
	 * @return
	 */
	@RequestMapping("ChangeQingjiaApplyStatus")
	@ResponseBody
	public String changeQingjiaApplyStatus(Qingjia qingjia,@Param("newsid") Long newsid){
		return qingjiaService.updateQingjiaApplyStatus(qingjia,newsid)>0 ? "success":"fail";
	}

	/**
	 * 导出excel
	 * @param batchid
	 * @return
	 */
	@RequestMapping("exportQingjiaExcel")
	@ResponseBody
	@RequiresPermissions("qingjia:excelexport")
	public Map<String, Object> exportSalaryExcel(String batchid){
		String[] idlist = batchid.split(",");
		List<Qingjia> qingjiaList = new ArrayList<Qingjia>();
		for (String s : idlist) {
			Integer id = Integer.valueOf(s);
			qingjiaList.add(qingjiaService.selById(id));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "");
		result.put("data",qingjiaList);
		return result;
	}

	/**
	 * 添加请假信息
	 * @return
	 */
	@RequestMapping("InsertQingjiaInfo")
	@ResponseBody
	@Operation(name = "请假申请")
	public String insertQingjiaInfo(Qingjia qingjia){
		Integer index = qingjiaService.insertQingjiaInfo(qingjia);
		if(index>0){
			return "success";
		}else{
			return "fail";
		}

	}



	/**
	 * 跳转到添加请假信息页面（可由admin和satff共同完成）
	 * @return
	 */
	@RequestMapping("ToInsertQingjiaInfo")
	public String toInsertQingjiaInfo(HttpServletRequest request) {
		request.setAttribute("staffofqingjia",((Staff)SecurityUtils.getSubject().getPrincipal()));
		return "staff/InsertQingjiaInfo";
	}

	/**
	 * 在主页点击请假信息跳转到信息界面
	 * @return
	 */
	@RequestMapping("ToFindAllQingjiaInfo")
	public String TofindAllQingjia() {
		return "admin/AllQingjia_Info";
	}
	
	
	@RequestMapping("UpdateQingjiaInfo")
	@ResponseBody
	@RequiresPermissions("qingjia:update")
	public String UpdateQingjiaInfo(Qingjia qingjia) {
		int index = qingjiaService.updateQingjiaInfo(qingjia);
		if(index > 0) {//更新成功
			return "success";
		}else {//更新失败
			return "fail";
		}
	}
	
	/**
	 * 编辑按钮，跳转到编辑界面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("ToUpdateQingjiaInfo")
	@RequiresPermissions("qingjia:update")
	public String EditStaff(Integer id,HttpServletRequest request) {
		Qingjia _qingjia = qingjiaService.selById(id);
		request.setAttribute("editqingjiainfo", _qingjia);
		return "admin/Qingjia_Edit";
	}
	/**
	 * 
	 * @param limit
	 * @param page
	 * @param qignjia
	 * @return
	 */
	@RequestMapping(value ="FindAllQingjiaInfo")
	@ResponseBody
	public Map<String, Object> findAllQingjiaInfo(int limit,int page,Qingjia qignjia){
//		System.out.println("接收到的请假信息为"+qignjia);
//		System.out.println("分页当前页数为："+page+"分页的每页显示记录数为"+limit);
		
		PageInfo pi = qingjiaService.selAllByPage(page,limit,qignjia);
		//以下操作
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", pi.getList());
		result.put("count", pi.getPagerecord());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}

	@RequestMapping("FindSelfQingjiaInfo")
	@ResponseBody
	public Map<String, Object> findSelfQingjiaInfo(int limit,int page){
		Staff user = (Staff) SecurityUtils.getSubject().getPrincipal();
		PageInfo pi = qingjiaService.selSelfQingjiaInfo(page,limit,user.getJobid());
		//以下操作
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", pi.getList());
		result.put("count", pi.getPagerecord());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}
}
