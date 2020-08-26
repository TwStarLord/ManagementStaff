package com.tw.advice;

import com.tw.annotation.Operation;
import com.tw.pojo.*;
import com.tw.service.LogInfoService;
import com.tw.service.NewsService;
import com.tw.service.UserAgentInfoService;
import com.tw.utils.DateFormatUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定义切点获取对象
 */
@Component
@Aspect
public class OprationRecord {


    /**
     * 记录系统日志，便于管理人员后期运维
     */
    @Resource
    private LogInfoService logInfoService;

    @Resource
    private UserAgentInfoService userAgentInfoService;

    /**
     * 针对一些需要记录的操作，如安排出差、请假申请等操作，需要将信息记录到数据库，便于在首页的界面消息界面中查看。
     */
//    @Resource
//    private NewsService newsService;

    private Logger logger = LoggerFactory.getLogger(OprationRecord.class);

    /**
     * 记录访客信息
     */
    @Pointcut("execution(* com.tw.controller.StaffLoginController.toIndex(..))")
    public void RecordUserInfo(){
    }

    @Pointcut("execution(* com.tw.controller.StaffInfoController.*(..))")
    public void StaffInfo(){
    }

    @Pointcut("execution(* com.tw.controller.SalaryInfoController.*(..))")
    public void SalaryInfo(){
    }

    @Pointcut("execution(* com.tw.controller.ChuchaiInfoController.*(..))")
    public void Chuchai(){
    }

    @Pointcut("execution(* com.tw.controller.KaoqinInfoController.*(..))")
    public void Kaoqin(){
    }

    @Pointcut("execution(* com.tw.controller.QingjiaInfoController.*(..))")
    public void Qingjia(){
    }

    @Pointcut("execution(* com.tw.controller.FileInfoController.*(..))")
    public void FileInfo(){
    }

//    指定多切点的方法一，在方法上指定多个切点，然后在获取切点方法名称时进行方法名的判断，然后根据名称来决定记录日志的类型

/*
@Pointcut("execution(public * com.wyh.data.controller.DepartmentController.*(..))")
public void department(){}
    @Pointcut("execution(public * com.wyh.data.controller.UserController.*(..))")
    public void user(){}

    @Before("department()")//怎样在这里指定多个切点，逗号不可以
    public void before(JoinPoint joinPoint){do something}
    @Before("department()||user()")
    public void before(JoinPoint joinPoint){do something}
*/

/*指定多切点的方法二：使用or and 或者 || && 在execution 表达式中指定多个切点
execution(* com.tw.controller.ChuchaiInfoController.*(..))  ||  execution(* com.tw.controller.KaoqinInfoController.*(..))
    此种方法中的|| 和 && 有什么区别呢？*/




    /**
     * 无论切点是否被执行完成，都会执行该方法
     * @param joinPoint  切点
     *         || Chuchai()          Chuchai() ||
     */
    @After("SalaryInfo() || StaffInfo() ||  Kaoqin() || Chuchai() || Qingjia() || RecordUserInfo() || FileInfo()")  //这样也可以，或者直接在pointcut中用  ||  连接  但是用&& 却不行
    public void after(JoinPoint joinPoint){
        // 1:在切面方法里面获取一个request，
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 2:通过springAOP切面JoinPoint类对象，获取该类，或者该方法，或者该方法的参数
        Class<? extends Object> clazz =  joinPoint.getTarget().getClass();
        String controllerOperation = clazz.getName();
        if(clazz.isAnnotationPresent(Operation.class)){
            // 当前controller操作的名称
            controllerOperation = clazz.getAnnotation(Operation.class).name();
        }
        // 获取当前方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //        这里只测试安排出差与请假申请两种消息
//        if(controllerOperation.equalsIgnoreCase("ChuchaiInfoController") || controllerOperation.equalsIgnoreCase("QingjiaInfoController")){
////            //提交出差安排 或者 员工提交请假申请都会被记录到消息中 且标明了参数
////            if(method.getName().equalsIgnoreCase("insertChuchaiInfo")){//出差安排操作
////
////            }else if (method.getName().equalsIgnoreCase("insertQingjiaInfo")){//请假申请操作
////
////            }
////        }


        // clazz类下的所有方法
        Method[] methods = clazz.getDeclaredMethods(); //反射机制中的 getDeclaredMethods 方法可以获取到 private 方法吗，如果不可以的话 就需要将确认的几个方法改为 protected
        String methodOperation = "";
        String operationName = "";
        int index = 0;
        Object[] args = null;
        Subject subject = SecurityUtils.getSubject();
        Staff staffinfo = (Staff) subject.getPrincipal();
        for (Method m : methods) {
            if(m.equals(method)){
                methodOperation = m.getName();
                if(staffinfo != null){
                    if(m.isAnnotationPresent(Operation.class)){
                        operationName = m.getAnnotation(Operation.class).name();
                    }
                    if("deleteStaffInfo".equals(methodOperation) || "deleteBatchStaffInfo".equals(methodOperation)){
                        index = logInfoService.insLogInfo(new LogInfo(staffinfo.getAccount(),request.getRemoteHost(),DateFormatUtil.getCurrentTime(),
                                operationName));
                        logger.info(staffinfo.getName() + " 执行了 " + controllerOperation + " 下的  " + methodOperation + " 操作！ ip地址为"
                                + request.getRemoteHost());
                        if(index<=0){ //文件记录日志，方便后期运维管理
                            logger.error("日志记录到数据库失败,记录时间为："+DateFormatUtil.getCurrentTime()+"操作人账号为："+staffinfo.getAccount()+"操作为："+methodOperation);
                        }
                    }if("deleteSalaryInfo".equals(methodOperation) || "deleteBatchSalaryInfo".equals(methodOperation)){
                        index = logInfoService.insLogInfo(new LogInfo(staffinfo.getAccount(),request.getRemoteHost(),DateFormatUtil.getCurrentTime(),
                                operationName));
                        logger.info(staffinfo.getName() + " 执行了 " + controllerOperation + " 下的  " + methodOperation + " 操作！ ip地址为"
                                + request.getRemoteHost());
                        if(index<=0){ //文件记录日志，方便后期运维管理
                            logger.error("日志记录到数据库失败,记录时间为："+DateFormatUtil.getCurrentTime()+"操作人账号为："+staffinfo.getAccount()+"操作为："+methodOperation);
                        }
                    }if("deleteKaoqinInfo".equals(methodOperation) || "deleteBatchKaoqinInfo".equals(methodOperation)){
                        index = logInfoService.insLogInfo(new LogInfo(staffinfo.getAccount(),request.getRemoteHost(),DateFormatUtil.getCurrentTime(),
                                operationName));
                        logger.info(staffinfo.getName() + " 执行了 " + controllerOperation + " 下的  " + methodOperation + " 操作！ ip地址为"
                                + request.getRemoteHost());
                        if(index<=0){ //文件记录日志，方便后期运维管理
                            logger.error("日志记录到数据库失败,记录时间为："+DateFormatUtil.getCurrentTime()+"操作人账号为："+staffinfo.getAccount()+"操作为："+methodOperation);
                        }
                    }if("deleteChuchaiInfo".equals(methodOperation) || "deleteBatchKaoqinInfo".equals(methodOperation)){
                        index = logInfoService.insLogInfo(new LogInfo(staffinfo.getAccount(),request.getRemoteHost(),DateFormatUtil.getCurrentTime(),
                                operationName));
                        logger.info(staffinfo.getName() + " 执行了 " + controllerOperation + " 下的  " + methodOperation + " 操作！ ip地址为"
                                + request.getRemoteHost());
                        if(index<=0){ //文件记录日志，方便后期运维管理
                            logger.error("日志记录到数据库失败,记录时间为："+DateFormatUtil.getCurrentTime()+"操作人账号为："+staffinfo.getAccount()+"操作为："+methodOperation);
                        }
                    }if("deleteQingjiaInfo".equals(methodOperation) || "deleteBatchQingjiaInfo".equals(methodOperation)){
                        index = logInfoService.insLogInfo(new LogInfo(staffinfo.getAccount(),request.getRemoteHost(),DateFormatUtil.getCurrentTime(),
                                operationName));
                        logger.info(staffinfo.getName() + " 执行了 " + controllerOperation + " 下的  " + methodOperation + " 操作！ ip地址为"
                                + request.getRemoteHost());
                        if(index<=0){ //文件记录日志，方便后期运维管理
                            logger.error("日志记录到数据库失败,记录时间为："+DateFormatUtil.getCurrentTime()+"操作人账号为："+staffinfo.getAccount()+"操作为："+methodOperation);
                        }
                    }
                    if("deleteFileInfo".equals(methodOperation) || "deleteBatchFileInfo".equals(methodOperation)){
                        index = logInfoService.insLogInfo(new LogInfo(staffinfo.getAccount(),request.getRemoteHost(),DateFormatUtil.getCurrentTime(),
                                operationName));
                        logger.info(staffinfo.getName() + " 执行了 " + controllerOperation + " 下的  " + methodOperation + " 操作！ ip地址为"
                                + request.getRemoteHost());
                        if(index<=0){ //文件记录日志，方便后期运维管理
                            logger.error("日志记录到数据库失败,记录时间为："+DateFormatUtil.getCurrentTime()+"操作人账号为："+staffinfo.getAccount()+"操作为："+methodOperation);
                        }
                    }else if("toIndex".equals(methodOperation)){ //记录访客信息
                        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
                        index = userAgentInfoService.insUserAgentInfo(new UserAgentInfo(
                                ((Staff)SecurityUtils.getSubject().getPrincipal()).getName(),DateFormatUtil.getCurrentTime(),userAgent.getBrowser().toString(),userAgent.getOperatingSystem().toString(),request.getRemoteAddr()
                        ));
                        if(index<0){
//                        插入数据失败，需要记录日志到文件中
                            logger.error(((Staff)request.getAttribute("staffinfo")).getName() + " 在 " + DateFormatUtil.getCurrentTime() + " 时  " + " 登录！ ip地址为"
                                    + request.getRemoteHost()+"记录数据到数据库失败!");
                        }
                    }
                }else{
                    logger.info("未知用户 执行了 " + controllerOperation + " 下的  " + methodOperation + " 操作！ ip地址为"
                            + request.getRemoteHost());
                }
            }
        }


    }

    @AfterReturning("Kaoqin()")
    public void afterReturning(JoinPoint joinPoint){
        // 1:在切面方法里面获取一个request，
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 2:通过springAOP切面JoinPoint类对象，获取该类，或者该方法，或者该方法的参数
        Class<? extends Object> clazz =  joinPoint.getTarget().getClass();
        String controllerOperation = clazz.getName();
        if(clazz.isAnnotationPresent(Operation.class)){
            // 当前controller操作的名称
            controllerOperation = clazz.getAnnotation(Operation.class).name();
        }
        // 获取当前方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // clazz类下的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        String methodOperation = "";
        for (Method m : methods) {
            if(m.equals(method)){
                methodOperation = m.getName();
                if(m.isAnnotationPresent(Operation.class)){
                    methodOperation = m.getAnnotation(Operation.class).name();
                }
            }
        }
        Subject subject = SecurityUtils.getSubject();
        Staff staffinfo = (Staff) subject.getPrincipal();
        if(staffinfo != null){
            logger.info(staffinfo.getName() + " 执行了 " + controllerOperation + " 下的  " + methodOperation + " 操作！ ip地址为"
                    + request.getRemoteHost());
        }else{
            logger.info("未知用户 执行了 " + controllerOperation + " 下的  " + methodOperation + " 操作！ ip地址为"
                    + request.getRemoteHost());
        }

    }
}
