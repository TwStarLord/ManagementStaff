package com.tw.service.impl;

import javax.annotation.Resource;

import com.tw.mapper.KaoqinMapper;
import com.tw.mapper.NewsMapper;
import com.tw.mapper.StaffMapper;
import com.tw.pojo.*;
import com.tw.utils.DateFormatUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.tw.mapper.QingjiaMapper;
import com.tw.service.QingjiaService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class QingjiaServiceImpl implements QingjiaService{

	@Resource
	private QingjiaMapper qingjiaMapper;

	@Resource
	private NewsMapper newsMapper;

	@Resource
	private StaffMapper staffMapper;

	@Resource
	private KaoqinMapper kaoqinMapper;

	@Override
	public PageInfo selAllByPage(int page, int limit, Qingjia qingjia) {
		PageInfo pageInfo = new PageInfo();
		//设置pageinfo信息
		pageInfo.setPagecurrent(page);
		pageInfo.setPagesize(limit);
		long count = qingjiaMapper.selCount(qingjia);
		pageInfo.setPagerecord(count);
		pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误
		String name = null;
		Integer departid = null;
		String status = "";
		String starttime = null;
		String endtime = null;
		if(qingjia!=null) {//有参数进行条件查询
			name = qingjia.getName();
			departid = qingjia.getDepartid();
			status = qingjia.getStatus();
			starttime = qingjia.getStarttime();
			endtime = qingjia.getEndtime();
		}

		pageInfo.setList(qingjiaMapper.selByPage((page-1)*limit, limit,name,departid,status,starttime,endtime));
		
		return pageInfo;
	}

	@Override
	public Qingjia selByJobId(Integer jobid) {
		return qingjiaMapper.selByJobid(jobid);
	}

	@Override
	public Qingjia selById(Integer id) {
		return qingjiaMapper.selById(id);
	}

	@Override
	public Integer updateQingjiaApplyStatus(Qingjia qingjia,Long newsid) {
		Subject subject = SecurityUtils.getSubject();
		Staff shenpi = (Staff) subject.getPrincipal();
		qingjia.setShenpi(shenpi.getAccount());
		Integer index = qingjiaMapper.updateQingjiaApplyStatus(qingjia);
		if(index>0){ //同意请假申请成功
			if(qingjia.getStatus().equals("同意")){ //该员工的请假申请被同意了，修改该员工的状态为请假
				int statusDate = DateFormatUtil.dateCompare(qingjiaMapper.selQingjiaInfoById(qingjia.getId()).getStarttime());
				if(statusDate == 0){
					staffMapper.updateStaffStatusByJobId(qingjiaMapper.selJobIdByQingjiaId(qingjia.getId()),"请假");
				}
			}
			newsMapper.updateNews(new News(newsid,"已处理",null));
		}
		return index;
	}

	@Override
	public PageInfo selSelfQingjiaInfo(int page, int limit,Integer jobid) {
		PageInfo pageInfo = new PageInfo();
		//设置pageinfo信息
		pageInfo.setPagecurrent(page);
		pageInfo.setPagesize(limit);
		long count = qingjiaMapper.selSelfCount(jobid);
		pageInfo.setPagerecord(count);
		pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误

		pageInfo.setList(qingjiaMapper.selSelfQingjiaInfo((page-1)*limit, limit,jobid));

		return pageInfo;
	}

	@Override
	public Integer insEndHolidayApply(Long id) {
		Staff sender = (Staff) SecurityUtils.getSubject().getPrincipal();
		Staff receiver = staffMapper.selKaoqinManager();
		return newsMapper.insNews(new News(sender.getAccount(),sender.getDepartid(),DateFormatUtil.getCurrentTime(),
				"销假申请",receiver.getAccount(),receiver.getDepartid(),"待审核",null,String.valueOf(id)));
	}

	@Override
	public Integer updateEndQingjiaApply(Long id, Long newsid, String reply) {
		if("agree".equals(reply)){ //同意销假申请
			Qingjia qingjiaInfo = qingjiaMapper.selById(id.intValue());
//			int i = DateFormatUtil.dateCompare(qingjiaInfo.getEndtime());
//			if(i>=0){ //销假结束日期比提交请假申请时要提前，按照销假申请的日期来更新考勤记录,且销假操作时
			Integer index1 = staffMapper.updateStaffStatusByJobId(qingjiaInfo.getJobid(), "在职");
			Integer index2 = qingjiaMapper.updateQingjiaInfoById(id.intValue(),DateFormatUtil.getCurrentDate(),"已结束");
			Integer index3 = newsMapper.updateNews(new News(newsid,"同意",null));
			return (index1>0 && index2>0 && index3>0) ? 1 : 0;
		}else if("refuse".equals(reply)){  //拒绝销假申请
			Integer index1 = newsMapper.updateNews(new News(newsid,"拒绝",null));
			return index1;
		}
		return 0;
	}

	@Override
	public Integer deleteBatchQingjiaInfoById(Long id) {
		return null;
	}

	@Override
	public Integer deleteQingjiaInfoById(Long id) {
		return null;
	}

	@Override
	public int updateQingjiaInfo(Qingjia qingjia) {
		return qingjiaMapper.updateQingjiaInfo(qingjia);
	}

	@Override
	public int deleteQingjiaInfo(int jobid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer insertQingjiaInfo(Qingjia qingjia) {
//		后台自动计算时间
		qingjia.setLeavedays(DateFormatUtil.getBetweenDaysFromTwoDates(qingjia.getStarttime(),qingjia.getEndtime()));
//		后台默认提交时间为当前时间并将时间以字符串格式化
		qingjia.setSubdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());


		Staff receiver = null;
		Integer result = qingjiaMapper.insertQingjiaInfo(qingjia);
//		在这里判断业务需求 请假天数小于七天则设置接收人为部门主管  否则为超级管理员
		if(qingjia.getLeavedays()<=7){
			receiver = staffMapper.selManagerByDepartId(qingjia.getDepartid());
			newsMapper.insNews(new News(qingjia.getAccount(),qingjia.getDepartid(),
					DateFormatUtil.getCurrentTime(),"请假申请",receiver.getAccount(),receiver.getDepartid(),"待审核","未读",String.valueOf(qingjia.getId())));
		}else {
			receiver = staffMapper.selManagerByDepartId(1);
			newsMapper.insNews(new News(qingjia.getAccount(),qingjia.getDepartid(),
					DateFormatUtil.getCurrentTime(),"请假申请",receiver.getAccount(),receiver.getDepartid(),"待审核","未读",String.valueOf(qingjia.getId())));
		}
		return result;
	}

}
