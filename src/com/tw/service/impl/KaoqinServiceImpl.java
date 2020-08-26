package com.tw.service.impl;

import com.tw.exception.KaoqinException;
import com.tw.mapper.KaoqinMapper;
import com.tw.mapper.NewsMapper;
import com.tw.mapper.StaffMapper;
import com.tw.pojo.*;
import com.tw.service.KaoqinService;
import com.tw.utils.DateFormatUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class KaoqinServiceImpl implements KaoqinService {

    @Resource
    private KaoqinMapper kaoqinMapper;

    @Resource
    private NewsMapper newsMapper;

    @Resource
    private StaffMapper staffMapper;

    //规定标准打卡时间
    private static final String recordtimeOfStandardString = new String("8:00:00");

    @Override
    public PageInfo selAllByPage(int page, int limit, Kaoqin kaoqin) {
        PageInfo pageInfo = new PageInfo();
        //设置pageinfo信息
        pageInfo.setPagecurrent(page);
        pageInfo.setPagesize(limit);
        long count = kaoqinMapper.selCount(kaoqin);
        pageInfo.setPagerecord(count);
        pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误
//        String name = null;
//        String departname = null;
//        String starttime = null;
//        String endtime = null;
//        if(qingjia!=null) {//有参数进行条件查询
//            name = qingjia.getName();
//            starttime = qingjia.getStarttime();
//            endtime = qingjia.getEndtime();
//        }
        pageInfo.setList(kaoqinMapper.selByPage((page-1)*limit, limit,kaoqin));

        return pageInfo;
    }

    @Override
    public String insertkaoqinRecordInfo() throws KaoqinException{
        int checkResult = DateFormatUtil.timeCompare();

        Subject subject = SecurityUtils.getSubject();
        Staff checkStaff = (Staff) subject.getPrincipal();

        if ("在职".equals(checkStaff.getStatus())){
            if(checkResult > 0){ //结果为1 说明打卡迟到了
//            成功时记录成功信息
                int index = kaoqinMapper.insKaoqinRecordInfo(new Kaoqin(checkStaff.getJobid().longValue(),checkStaff.getName(),DateFormatUtil.getCurrentTime(),1,DateFormatUtil.getMonth()));
                if(index <= 0){//插入数据失败，需要记录异常信息，提示技术人员来进行补录
                    Staff staffManager = staffMapper.selManagerByDepartId(2);//获取需要接收插入考勤异常信息的管理人员
                    newsMapper.insNews(new News("系统通知",null,
                            DateFormatUtil.getCurrentTime(),"记录插入考勤记录",staffManager.getAccount(),staffManager.getDepartid(),null,"未读","插入数据失败，待处理!"));
                }else{
                    Kaoqin kaoqin = kaoqinMapper.selByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),DateFormatUtil.getMonth()));
                    if(kaoqin != null){
                        kaoqinMapper.updateKaoqinInfoByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),
                                DateFormatUtil.getMonth(),0,1,0,0));//不能为-1 做人不能黑心
                    }else{
                        kaoqinMapper.insertKaoqinInfoByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),
                                checkStaff.getName(),checkStaff.getDepartid(),DateFormatUtil.getMonth(),0,1,0,0));
                    }

                }
                throw new KaoqinException("打卡失败，迟到！");
            }else{ //结果为-1 说明打卡正常
//            失败时记录失败信息
                int index = kaoqinMapper.insKaoqinRecordInfo(new Kaoqin(checkStaff.getJobid().longValue(),checkStaff.getName(),DateFormatUtil.getCurrentTime(),0,DateFormatUtil.getMonth()));
                if(index<0){//插入数据失败，需要记录异常信息，提示技术人员来进行补录
                    Staff staffManager = staffMapper.selManagerByDepartId(2);//获取需要接收插入考勤异常信息的管理人员,这里可以由角色名称获取角色id，但是我是在知晓该用户的情况下执行的，后台反正没人知道。
                    newsMapper.insNews(new News("系统通知",null,
                            DateFormatUtil.getCurrentTime(),"记录插入考勤记录",staffManager.getAccount(),staffManager.getDepartid(),"待审核","未读","插入数据失败，待处理!"));
                }else{
                    Kaoqin kaoqin = kaoqinMapper.selByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),DateFormatUtil.getMonth()));
                    if(kaoqin != null){
                        kaoqinMapper.updateKaoqinInfoByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),
                                DateFormatUtil.getMonth(),1));
                    }else{
                        kaoqinMapper.insertKaoqinInfoByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),
                                checkStaff.getName(),checkStaff.getDepartid(),DateFormatUtil.getMonth(),0,0,0,0));
                    }
                }
                return "打卡成功！";
            }
        }else if ("请假".equals(checkStaff.getStatus())){
            Kaoqin kaoqin = kaoqinMapper.selByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),DateFormatUtil.getMonth()));
            if(kaoqin != null){
                kaoqinMapper.updateKaoqinInfoByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),
                        DateFormatUtil.getMonth(),0,0,1,0));
            }else{
                kaoqinMapper.insertKaoqinInfoByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),
                        DateFormatUtil.getMonth(),0,0,1,0));
            }
            return "打卡成功！";
        }else if ("出差".equals(checkStaff.getStatus())){
            Kaoqin kaoqin = kaoqinMapper.selByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),DateFormatUtil.getMonth()));
            if(kaoqin != null){
                kaoqinMapper.updateKaoqinInfoByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),
                        DateFormatUtil.getMonth(),1,0,0,1));
            }else{
                kaoqinMapper.insertKaoqinInfoByJobIdAndMonth(new Kaoqin(checkStaff.getJobid().longValue(),
                        DateFormatUtil.getMonth(),1,0,0,1));
            }
            return "打卡成功！";
        }else if ("待激活".equals(checkStaff.getStatus())){
            throw new KaoqinException("该账号未激活！");
        }
        return null;
    }

    @Override
    public Kaoqin selByKaoqinInfoId(Integer id) {
        return kaoqinMapper.selByKaoqinInfoId(id);
    }

    @Override
    public Kaoqin selById(Integer id) {
        return kaoqinMapper.selById(id);
    }

    @Override
    public int insertKaoqinInfo(Kaoqin kaoqin) {
        return kaoqinMapper.insertKaoqinInfo(kaoqin);
    }

    @Override
    public Integer updateKaoqinApplyStatus(Kaoqin kaoqin, Long newsid) {
        Integer index = 0;
        if(kaoqin.getCheckstatus() == 1){  //该考勤补卡申请被拒绝
            newsMapper.updateNews(new News(newsid,"已处理","已读"));
            return 1;
        }else if(kaoqin.getCheckstatus() == 0){ //该考勤补卡申请被同意
            index = kaoqinMapper.updateKaoqinApplyStatus(kaoqin);
            if(index>0){ //修改员工的 考勤打卡 状态成功
                newsMapper.updateNews(new News(newsid,"已处理","已读"));
                Kaoqin kaoqinRecordInfo = kaoqinMapper.selKaoqinRecordById(kaoqin.getId().intValue());
                kaoqinMapper.updateKaoqinInfoByJobIdAndMonth(new Kaoqin(kaoqinRecordInfo.getJobid(),kaoqinRecordInfo.getMonth()
                ,1,-1));
            }
        }

        return index;
    }

    @Override
    public PageInfo selAllKaoqinRecordByPage(int page, int limit) {
        PageInfo pageInfo = new PageInfo();
        //设置pageinfo信息
        pageInfo.setPagecurrent(page);
        pageInfo.setPagesize(limit);
        long count = kaoqinMapper.selKaoqinRecordCount();
        pageInfo.setPagerecord(count);
        pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误
        pageInfo.setList(kaoqinMapper.selAllKaoqinRecordByPage((page-1)*limit, limit));

        return pageInfo;
    }

    @Override
    public Kaoqin selKaoqinRecordById(Integer id) {
        return kaoqinMapper.selKaoqinRecordById(id);
    }

    @Override
    public PageInfo selSelfKaoqinInfo(int page, int limit, Integer jobid) {
        PageInfo pageInfo = new PageInfo();
        //设置pageinfo信息
        pageInfo.setPagecurrent(page);
        pageInfo.setPagesize(limit);
        long count = kaoqinMapper.selSelfCount(jobid);
        pageInfo.setPagerecord(count);
        pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误

        pageInfo.setList(kaoqinMapper.selSelfKaoqinInfo((page-1)*limit, limit,jobid));

        return pageInfo;
    }

    @Override
    public PageInfo selSelfKaoqinRecord(int page, int limit, Integer jobid) {
        PageInfo pageInfo = new PageInfo();
        //设置pageinfo信息
        pageInfo.setPagecurrent(page);
        pageInfo.setPagesize(limit);
        long count = kaoqinMapper.selSelfKaoqinRecordCount(jobid);
        pageInfo.setPagerecord(count);
        pageInfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//记录数不超过20亿不会造成cast错误
        pageInfo.setList(kaoqinMapper.selSelfKaoqinRecord((page-1)*limit, limit,jobid));

        return pageInfo;
    }

    @Override
    public Integer deleteBatchKaoqinInfoById(Long id) {
        return null;
    }

    @Override
    public Integer deleteKaoqinInfoById(Long id) {
        return null;
    }
}
