package com.tw.quartz;


import com.tw.mapper.*;
import com.tw.pojo.Chuchai;
import com.tw.pojo.News;
import com.tw.pojo.Qingjia;
import com.tw.pojo.Staff;
import com.tw.utils.DateFormatUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务
 * 1.请假：判断请假时间是否到期
 * 2.出差：判断出差时间是否到了，如果到了，就安排
 * 3.工资：每个月底自动生成工资信息，并以邮件附件形式发送到财务部主管。（这里也可以考虑给每个员工发各自的工资信息）
 */
@Service
@EnableScheduling
@Lazy(false) //据说spring的懒加载机制可能
public class SalaryGenerateTimeTask {

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private ChuchaiMapper chuchaiMapper;

    @Resource
    private QingjiaMapper qingjiaMapper;

    @Resource
    private KaoqinMapper kaoqinMapper;

    @Resource
    private NewsMapper newsMapper;

    /**
     *在每个月底中午12点生成工资Excel文件，并将该文件发送到财务部主管邮箱
     */
//    @Scheduled(cron = "0 0 12 L * ?")
//    public void salaryGenerate(){
//
//    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void test(){
        System.out.println("这里是在测试定时器任务，每隔五秒输出语句!");
    }

//    @Scheduled(cron = "0 0 1 * * ?")
    @Scheduled(cron = "*/5 * * * * ?")
    public void getChuchaiAndQingjiaStaff(){
//        1.由于请假和出差时会判断请假与出差的起始时间是否为当天，不为当天则不会立即修改员工的状态为请假或者出差
        List<Qingjia> qingjiaListOfNow = qingjiaMapper.selQingjiaInfoByNow(); //获取当天需要请假的员工
        List<Chuchai> chuchaiListOfNow = chuchaiMapper.selChuchaiInfoByNow(); //获取当天需要出差的员工
//        2.以上获取到的员工信息为在提交请假并被同意、出差安排被确认之后由于起始时间不为当天，所以没有修改对应的员工状态，
//        现在需要修改各位员工的状态为对应的请假或者出差
        Staff receiver = staffMapper.selKaoqinManager();
        for(Qingjia qingjia:qingjiaListOfNow){
            staffMapper.updateStaffStatusByJobId(qingjia.getJobid(),"请假");
            newsMapper.insNews(new News(qingjia.getAccount(),qingjia.getDepartid(),DateFormatUtil.getCurrentTime(),
                    "修改状态",receiver.getAccount(),receiver.getDepartid(),"待审核",null,String.valueOf(qingjia.getId())));
        }
        for(Chuchai chuhcai:chuchaiListOfNow){
            staffMapper.updateStaffStatusByJobId(chuhcai.getJobid(),"出差");
            newsMapper.insNews(new News(chuhcai.getAccount(),chuhcai.getDepartid(),DateFormatUtil.getCurrentTime(),
                    "修改状态",receiver.getAccount(),receiver.getDepartid(),"待审核",null,String.valueOf(chuhcai.getId())));
        }

    }

    /**
     * 在每日凌晨一点去查找状态不为在职状态的员工，
     */
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void generateKaoqinInfo(){
//        List<Staff> staffList = staffMapper.selStaffStatusNotJobing();
//        List<Integer> chuhcaiJobids = new ArrayList<>();
//        List<Integer> qingjiaJobids = new ArrayList<>();
//        for(Staff staff:staffList){  //将出差和请假状态的员工进行分类
//            if("请假".equals(staff.getStatus())){
//                qingjiaJobids.add(staff.getJobid());
//            }else if("出差".equals(staff.getStatus())){
//                chuhcaiJobids.add(staff.getJobid());
//            }
//        }
//
//
//        List<Chuchai> chuhcaiInfo = chuchaiMapper.selChuchaiInfoByJobid();
//    }

}
