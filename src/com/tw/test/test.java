package com.tw.test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.tw.pojo.Permission;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mysql.fabric.xmlrpc.base.Data;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.tw.pojo.Notice;
import com.tw.pojo.Staff;
import com.tw.service.NoticeService;
import com.tw.service.StaffService;
import com.tw.service.impl.NoticeServiceImpl;
import com.tw.service.impl.StaffServiceImpl;

public class test {



	
	
//	@Test
	public void getKey() {
		InputStream is = null;
		SqlSessionFactory factory = null;
		SqlSession session = null;
		try {
			is = Resources.getResourceAsStream("mybatis-test.xml");
			factory = new SqlSessionFactoryBuilder().build(is);
			session = factory.openSession();
//			Notice notice = new Notice();
//			notice.setTitle("主键返回测试");
//			notice.setAuthor("user3");
//			notice.setContent("主键返回测试");
//			notice.setDate(new Date());
			//动态代理，接口绑定
			//session.getMapper("com.tw.mapper.NoticeMapper.insNotice");
//			int index = session.insert("com.tw.mapper.NoticeMapper.insNotice", notice);
//			System.out.println("返回的主键应该为21："+notice.getId());

//			测试权限树
			List<Permission> permissionList = session.selectList("com.tw.mapper.PermissionMapper.selChildrenPermissionByParentId");
			System.out.println(permissionList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

//	public static void main(String[] args) throws IOException {
		
//		Date date = new Date();
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
//		InputStream is = Resources.getResourceAsStream("mybatis.xml");
//		//工厂设计模式
//		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
//		//使用工厂生产session
//		SqlSession session = factory.openSession();
//		
//		Staff staff = new Staff();
//		staff.setAccount("admin");
//		staff.setPassword("admin");
////		List<Staff> list = session.selectList("com.tw.mapper.selAll");
//		Staff s = session.selectOne("com.tw.mapper.selByAccountAndPwd",staff);
//		
//		System.out.println(s);
//		
//		session.close();
		
		//ClassPathXmlApplicationContext 默认去classes文件夹根目录开始寻找
//				ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//				String[] names = ac.getBeanDefinitionNames();
//				for (String string : names) {
//					System.out.println(string);
//				}
//				StaffService bean;
//				bean = ac.getBean("staffService",StaffServiceImpl.class);
//				System.out.println(bean);
//				List<Staff> list = bean.selAll();
//				System.out.println(list);
		
		//测试批量删除操作时切割数据
		//1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,
//		String batchfile_id = "1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,";
//		String[] list = batchfile_id.split(",");
//		System.out.println("切割之后的总个数为"+list.length);
//		for (String string : list) {
//			System.out.print(string+"   ");
//		}
		
		//测试对于日期的查询操作
//		NoticeService noticeService = new NoticeServiceImpl();
//		List<Notice> list = noticeService.selByDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//		for (Notice notice : list) {
//			System.out.println(notice);
//		}
		
//		InputStream is = null;
//		SqlSessionFactory factory = null;
//		SqlSession session = null;
//		try {
//			is = Resources.getResourceAsStream("mybatis-test.xml");
//			factory = new SqlSessionFactoryBuilder().build(is);
//			session = factory.openSession();
//			Notice notice = new Notice();
//			notice.setTitle("主键返回测试");
//			notice.setAuthor("user3");
//			notice.setContent("主键返回测试");
//			//notice.setDate(null);
//			notice.setDate(new Date());
//			//动态代理，接口绑定
//			//session.getMapper("com.tw.mapper.NoticeMapper.insNotice");
//			int index = session.insert("com.tw.mapper.NoticeMapper.insNotice", notice);
//			System.out.println("受影响的行数为："+index);
//			System.out.println("返回的主键应该为21："+notice.getId());
//			if(index>0) {
//				session.commit();
//			}else session.rollback();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			if(session!=null) {
//				session.close();
//			}
//			if(is!=null) {
//				try {
//					is.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//
//	}

	public static void main(String[] args) {
//		LocalTime recordtimeOfStandard = null;
//		LocalTime recordtime = null;
//		try {
//			recordtimeOfStandard = LocalTime.now();
//			recordtime = LocalTime.now();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//			System.out.println("标准时间为："+ recordtimeOfStandard.format(formatter));
//			System.out.println("打卡时间为："+ recordtime.format(formatter));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String recordtime = formatter.format(new Date());
//		String recordtimeOfStandardStr = "8:00:00";
//		Calendar c1 = Calendar.getInstance();
//		Calendar c2 = Calendar.getInstance();
//		int year = c1.get(Calendar.YEAR);
//		int month = c1.get(Calendar.MONTH) + 1; //这里默认月份是从0开始
//		int day = c1.get(Calendar.DAY_OF_MONTH);
//		recordtimeOfStandardStr = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day) + " " +recordtimeOfStandardStr;
////		System.out.println(recordtimeOfStandardStr);
//		try {
//			c1.setTime(formatter.parse(recordtime));  //c1是打卡时间
//			c2.setTime(formatter.parse(recordtimeOfStandardStr));  //c2是标准时间
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		//打卡时间比标准时间提前则为-1  延后则为1
//		System.out.println(c1.compareTo(c2));


//		测试权限树
		InputStream is = null;
		SqlSessionFactory factory = null;
		SqlSession session = null;
		try {
			is = Resources.getResourceAsStream("mybatis.xml");
			factory = new SqlSessionFactoryBuilder().build(is);
			session = factory.openSession();
//			Notice notice = new Notice();
//			notice.setTitle("主键返回测试");
//			notice.setAuthor("user3");
//			notice.setContent("主键返回测试");
//			notice.setDate(new Date());
			//动态代理，接口绑定
			//session.getMapper("com.tw.mapper.NoticeMapper.insNotice");
//			int index = session.insert("com.tw.mapper.NoticeMapper.insNotice", notice);
//			System.out.println("返回的主键应该为21："+notice.getId());

//			测试权限树
			List<Permission> permissionList = session.selectList("com.tw.mapper.PermissionMapper.selChildrenPermissionByParentId");
			System.out.println(permissionList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
