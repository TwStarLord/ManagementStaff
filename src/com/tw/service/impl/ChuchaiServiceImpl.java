package com.tw.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.tw.controller.ChuchaiInfoController;
import com.tw.exception.StaffException;
import com.tw.mapper.KaoqinMapper;
import com.tw.mapper.NewsMapper;
import com.tw.mapper.StaffMapper;
import com.tw.pojo.*;
import com.tw.utils.DateFormatUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.tw.mapper.ChuchaiMapper;
import com.tw.service.ChuchaiService;

@Service
public class ChuchaiServiceImpl implements ChuchaiService {

	@Resource
	private ChuchaiMapper chuchaiMapper;

	@Resource
	private NewsMapper newsMapper;

	@Resource
	private KaoqinMapper kaoqinMapper;

	/**
	 * 在确认出差安排之后修改员工的状态为出差
	 */
	@Resource
	private StaffMapper staffMapper;

	@Override
	public List<Chuchai> selAll() {
		return chuchaiMapper.selAll();
	}

	@Override
	public PageInfo selAllByPage(int page, int limit, Chuchai chuchai) {
		Staff getter = (Staff) SecurityUtils.getSubject().getPrincipal();
		PageInfo pageInfo = null;
		if("超级管理员".equals(getter.roleList.get(0).getName())){
			pageInfo = new PageInfo();
			//设置pageinfo信息
			pageInfo.setPagecurrent(page);
			pageInfo.setPagesize(limit);
			long count = chuchaiMapper.selCount(chuchai);
			pageInfo.setPagerecord(count);
			pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误
			String name = null;
			Integer departid = null;
			String destination = null;
			String status = "";
			String starttime = null;
			String endtime = null;
			if(chuchai!=null) {//有参数进行条件查询
				name = chuchai.getName();
				departid = chuchai.getDepartid();
				destination = chuchai.getDestination();
				status = chuchai.getStatus();
				starttime = chuchai.getStarttime();
				endtime = chuchai.getEndtime();
			}
			pageInfo.setList(chuchaiMapper.selByPage((page-1)*limit, limit,name,departid,destination,status,starttime,endtime,null));

		}else {
			pageInfo = new PageInfo();
			//设置pageinfo信息
			pageInfo.setPagecurrent(page);
			pageInfo.setPagesize(limit);
			long count = chuchaiMapper.selCount(chuchai);
			pageInfo.setPagerecord(count);
			pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误
			String name = null;
			Integer departid = null;
			String destination = null;
			String status = "";
			String starttime = null;
			String endtime = null;
			if(chuchai!=null) {//有参数进行条件查询
				name = chuchai.getName();
				departid = chuchai.getDepartid();
				destination = chuchai.getDestination();
				status = chuchai.getStatus();
				starttime = chuchai.getStarttime();
				endtime = chuchai.getEndtime();
			}
			pageInfo.setList(chuchaiMapper.selByPage((page-1)*limit, limit,name,departid,destination,status,starttime,endtime,getter.getJobid()));

		}


		return pageInfo;
	}

	@Override
	public Chuchai selByJobId(Integer jobid) {
		return chuchaiMapper.selByJobId(jobid);
	}
	@Override
	public int updateChuchaiInfo(Chuchai chuchai) {
		return chuchaiMapper.updateChuchaiInfo(chuchai);
	}
	@Override
	public int deleteChuchaiInfo(int jobid) {
		return chuchaiMapper.deleteChuchaiInfo(jobid);
	}


	@Override
	public int insertChuchaiInfo(Chuchai chuchai) throws StaffException {
		Chuchai checkChuhchai1 = chuchaiMapper.selChuchaiInfoByJobIdAndStartTime(chuchai.getJobid(),chuchai.getStarttime());
		Chuchai checkChuhchai2 = chuchaiMapper.selChuchaiInfoByJobIdAndEndTime(chuchai.getJobid(),chuchai.getEndtime());
//		大区间或有多个出差信息，所以用list获取
		List<Chuchai> checkChuhchai3 = chuchaiMapper.selChuchaiInfoByJobIdAndTime(chuchai.getJobid(),chuchai.getStarttime(),chuchai.getEndtime());
		if(checkChuhchai1 != null || checkChuhchai2 != null || checkChuhchai3.size()>0){
			throw new StaffException("alreadyHasChuchaiInfo");//该员工在该时间段已经有出差安排了!
		}
		Subject subject = SecurityUtils.getSubject();
		Staff shenpi = (Staff) subject.getPrincipal();
		chuchai.setShenpi(shenpi.getName());
		int result = chuchaiMapper.insertChuchaiInfo(chuchai);
		newsMapper.insNews(new News(shenpi.getAccount(),shenpi.getDepartid(), DateFormatUtil.getCurrentTime(),"出差安排",
				chuchai.getAccount(),chuchai.getDepartid(),"待确认",null,String.valueOf(chuchai.getId())));
		return result;
	}

	@Override
	public Chuchai selById(Integer id) {
		return chuchaiMapper.selById(id);
	}

	@Override
	public Integer updateChuchaiManagementStatus(Chuchai chuchai,Long newsid) {
		Integer index = chuchaiMapper.updateChuchaiManagementStatus(chuchai);
		if(index>0){
//			在这里修改员工的状态为出差,然后获取该员工的出差的详细信息，并比较出差信息中的开始时间与结束时间是否在一个月内，如果不在一个月内的话，就分两次处理出差信息。
			int statusDate = DateFormatUtil.dateCompare((chuchaiMapper.selChuchaiInfoById(chuchai.getId())).getStarttime());
			if(statusDate == 0){ //出差日期就是今天
//				第一步，修改该员工的状态为出差
				staffMapper.updateStaffStatusByJobId(((Staff)(SecurityUtils.getSubject().getPrincipal())).getJobid(),"出差");
//				这里需要考虑到的情况，如果出差安排和确认时间是在打完卡之后，需要重新修改打卡状态


//				第二步，根据返回的出差信息获取开始与结束、
				Chuchai chuchaiInfo = chuchaiMapper.selChuchaiInfoById(chuchai.getId());
				int startMonth = DateFormatUtil.getMonthByStringDate(chuchaiInfo.getStarttime());
//				int endMonth = DateFormatUtil.getMonthByStringDate(chuchaiInfo.getEndtime());
//				if(startMonth == endMonth ){ //开始与结束时间在同一月
//					保存出差考勤信息到考勤记录中去，
//					先要判断考勤记录中是否有该员工当前月份的考勤记录，
//					如果有，则直接更新，如果没有，则直接插入数据
					Kaoqin kaoqin = kaoqinMapper.selKaoqinInfoByJobIdAndMonth(chuchaiInfo.getJobid(),startMonth);
					if(kaoqin == null){//该月没有考勤记录
						kaoqinMapper.insertKaoqinChuchaiInfoByJobIdAndMonth(chuchaiInfo.getJobid(),chuchaiInfo.getName(),
								chuchaiInfo.getDepartid(),startMonth,
								1L);
					}else {//该月有考勤记录
						kaoqinMapper.updateKaoqinChuchaiInfoByJobIdAndMonth(chuchaiInfo.getJobid(),startMonth,
								1L);
					}
//				}
			}
			newsMapper.updateNews(new News(newsid,"已确认",null));
		}
		return index;
	}

	@Override
	public PageInfo selSelfChuchaiInfo(int page, int limit, Integer jobid) {
		PageInfo pageInfo = new PageInfo();
		//设置pageinfo信息
		pageInfo.setPagecurrent(page);
		pageInfo.setPagesize(limit);
		long count = chuchaiMapper.selSelfCount(jobid);
		pageInfo.setPagerecord(count);
		pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误
		pageInfo.setList(chuchaiMapper.selSelfChuchaiInfo((page-1)*limit, limit,jobid));

		return pageInfo;
	}

	@Override
	public Integer insCountChuchaiFeeApply(Long id) {
		Staff sender = (Staff) SecurityUtils.getSubject().getPrincipal();
		Staff receiver = staffMapper.selKaoqinManager();
		return newsMapper.insNews(new News(sender.getAccount(),sender.getDepartid(),DateFormatUtil.getCurrentTime(),
				"出差报销",receiver.getAccount(),receiver.getDepartid(),"待审核",null,String.valueOf(id)));
	}

	@Override
	public Integer updateCountChuchaiApply(Long id, Long newsid, String reply) {
		if("agree".equals(reply)){ //同意出差报销申请
			Chuchai chuchaiInfo = chuchaiMapper.selById(id.intValue());
//			int i = DateFormatUtil.dateCompare(qingjiaInfo.getEndtime());
//			if(i>=0){ //销假结束日期比提交请假申请时要提前，按照销假申请的日期来更新考勤记录,且销假操作时
			Integer index1 = staffMapper.updateStaffStatusByJobId(chuchaiInfo.getJobid(), "在职");
			Integer index2 = chuchaiMapper.updateChuchaiInfoById(id.intValue(),DateFormatUtil.getCurrentDate(),"已结束");
			Integer index3 = newsMapper.updateNews(new News(newsid,"同意",null));
			return (index1>0 && index2>0 && index3>0) ? 1 : 0;
		}else if("refuse".equals(reply)){  //拒绝出差报销申请
			Integer index1 = newsMapper.updateNews(new News(newsid,"拒绝",null));
			return index1;
		}
		return 0;
	}

	@Override
	public Integer deleteBatchChuchaiInfoById(Long id) {
		return null;
	}

	@Override
	public Integer deleteChuchaiInfoById(Long id) {
		return null;
	}

}
