package com.tw.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tw.annotation.Operation;
import com.tw.exception.StaffException;
import com.tw.pojo.Salary;
import com.tw.pojo.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tw.pojo.Chuchai;
import com.tw.pojo.PageInfo;
import com.tw.service.ChuchaiService;

@Operation(name = "出差操作")
@Controller
@RequestMapping("Chuchai")
public class ChuchaiInfoController {

	@Resource
	private ChuchaiService chuchaiService;

	@RequestMapping("BatchDeleteChuchaiInfo")
	@ResponseBody
	@Operation(name = "批量删除出差信息")
	@RequiresPermissions("chuchai:batchdelete")
	public String deleteBatchChuchaiInfo(String batchid){
//		return chuchaiService.deleteBatchChuchaiInfoById(id)>0 ? "success":"fail";
		return "success";
	}

	@RequestMapping("DeleteChuchaiInfo")
	@ResponseBody
	@Operation(name = "删除出差信息")
	@RequiresPermissions("chuchai:delete")
	public String deleteChuchaiInfo(Long id){
//		return chuchaiService.deleteChuchaiInfoById(id)>0 ? "success":"fail";
		return "success";
	}


	@RequestMapping("HandlerCountChuchaiApply")
	@ResponseBody
	public String handlerCountChuchaiApply(@RequestParam("chuchai_id") Long id,@RequestParam("newsid") Long newsid,@RequestParam("reply") String reply){
		return chuchaiService.updateCountChuchaiApply(id,newsid,reply)>0 ? "success":"fail";
	}

	/**
	 * 提交出差报销申请
	 * @param id
	 * @return
	 */
	@RequestMapping("PostCountChuchaiFeeApply")
	@ResponseBody
	public String postCountChuchaiFeeApply(@RequestParam("chuchai_id") Long id){
		return chuchaiService.insCountChuchaiFeeApply(id)>0 ? "success":"fail";
	}

	@RequestMapping("ToFindSelfChuchaiInfo")
	public String toFindSelfChuchaiInfo(){
		return "staff/StaffSelfChuchaiInfo";
	}

	@RequestMapping("ConfirmChuchaiManagement")
	@ResponseBody
	public String ConfirmChuchaiManagement(Chuchai chuchai, @Param("newsid") Long newsid){
		return chuchaiService.updateChuchaiManagementStatus(chuchai,newsid)>0 ? "success":"fail";
	}


	/**
	 * 导出excel
	 * @param batchid
	 * @return
	 */
	@RequestMapping("exportChuchaiExcel")
	@ResponseBody
//	@RequiresPermissions("chuchai:excelexport")
	@RequiresPermissions("smile:smile")
	public Map<String, Object> exportSalaryExcel(String batchid){
		String[] idlist = batchid.split(",");
		List<Chuchai> chuchaiList = new ArrayList<Chuchai>();
		for (String s : idlist) {
			Integer id = Integer.valueOf(s);
			chuchaiList.add(chuchaiService.selById(id));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "");
		result.put("data",chuchaiList);
		return result;
	}

	/**
	 * 在出差信息界面点击添加按钮跳转到相关信息界面
	 * @return
	 */
	@RequestMapping("ToInsertChuchaiInfo")
	public String toInsertChuchaiInfo() {
		return "admin/InsertChuchaiInfo";
	}

	/**
	 * 首页中查看所有人得出差信息
	 * @return
	 */
	@RequestMapping("ToFindAllChuchaiInfo")
	public String tofindAllChuchai() {
		return "admin/AllChuchai_Info";
	}


	@Operation(name = "安排出差")
	@RequestMapping("InsertChuchaiInfo")
	@ResponseBody
	public String insertChuchaiInfo(Chuchai chuchai) {
		int index = 0;
		try {
			index = chuchaiService.insertChuchaiInfo(chuchai);
		} catch (StaffException e) {
			return e.getMessage();
		}
		if(index > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	/**
	 * 点击编辑按钮更新信息
	 * @param chuchai
	 * @return
	 */
	@RequestMapping("UpdateChuchai")
	@ResponseBody
	@RequiresPermissions("chuchai:update")
	public String UpdateChuchai(Chuchai chuchai) {
		System.out.println(chuchai);
		int index = chuchaiService.updateChuchaiInfo(chuchai);
		if(index > 0) {//更新成功
			System.out.println("更新成功");
			return "success";
		}else {
			System.out.println("更新失败");
			return "fail";
		}
	}
	/**
	 * 编辑按钮，跳转到编辑界面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("ToUpdateChuchaiInfo")
	@RequiresPermissions("chuchai:update")
	public String EditStaff(Integer id,HttpServletRequest request) {
		request.setAttribute("editchuchaiinfo", chuchaiService.selById(id));
		return "admin/Chuchai_Edit";
	}
	/**
	 * 查询符合要求的出差信息
	 * 没有默认值，如果不配置method， 则以任何请求形式 RequestMethod.GET， RequestMethod.POST， RequestMethod.PUT， RequestMethod.DELETE都可以访问得到。
	 * 过滤器默认不处理get方式的字符编码问题吗？是不是因为layui前端的table已经处理了相关字符编码的问题，导致接收时产生了乱码问题？
	 * @param limit
	 * @param page
	 * @param chuchai
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	//,method = {RequestMethod.GET,RequestMethod.POST}
	@RequestMapping(value ="FindAllChuchaiInfo")
	@ResponseBody
	@RequiresPermissions("chuchai:query")
	public Map<String, Object> findAllChuchaiInfo(int limit,int page,Chuchai chuchai){
		PageInfo pi = chuchaiService.selAllByPage(page,limit,chuchai);
		//以下操作
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", pi.getList());
		result.put("count", pi.getPagerecord());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}

	@RequestMapping("FindSelfChuchaiInfo")
	@ResponseBody
	public Map<String, Object> findSelfChuchaiInfo(int limit,int page){
		Staff user = (Staff) SecurityUtils.getSubject().getPrincipal();
		PageInfo pi = chuchaiService.selSelfChuchaiInfo(page,limit,user.getJobid());
		//以下操作
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", pi.getList());
		result.put("count", pi.getPagerecord());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}
	
}
