package com.tw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;
import java.util.UUID;

@RestController
@RequestMapping("Mail")
public class MailController {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;

    @Value("${mail.smtp.username}")
    private String emailFrom;

//    @Value("${mail.smtp.tousername}")
//    private String toEmail;

    public static MimeMessage createSimpleMail(HttpServletRequest request,Session session,String mail,Integer jobid)
        throws Exception {
          //创建邮件对象
         MimeMessage message = new MimeMessage(session);
          //指明邮件的发件人
         message.setFrom(new InternetAddress("twstarlord@163.com"));
         //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
         message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
              //邮件的标题
         message.setSubject("账号激活码邮件!");
          //邮件的文本内容
         String ipAddr = request.getLocalAddr();
         int serverPort = request.getServerPort();
         String contextPath = request.getContextPath();
         String activeCode = UUID.randomUUID().toString() + UUID.randomUUID().toString();
         String msg = "http://" + ipAddr + ":" + serverPort + "/" + contextPath + "/StaffInfo/ActiveStaff?activecode="+activeCode+"&jobid="+jobid;
         message.setText(msg);
              //返回创建好的邮件对象
         return message;
        }

    @RequestMapping("sendMail")
    public String sendActiveCodeMessage(HttpServletRequest request,String mail,Integer jobid){

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.163.com");
            props.put("mail.smtp.port", 25);
            // 是否需要身份验证
            props.put("mail.smtp.auth", "true");

            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("twstarlord@163.com", "FBNRGHNARNJVKWII");
                }
            });
            //使用JavaMail发送邮件的5个步骤
            //1、创建session
            session = Session.getInstance(props);
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);
            //2、通过session得到transport对象
            Transport ts = null;
            Message message = null;
        try {
            ts = session.getTransport();
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect("smtp.163.com", "twstarlord@163.com", "FBNRGHNARNJVKWII");
            //4、创建邮件
            message = createSimpleMail(request,session,mail,jobid);
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
                return "fail";
        } catch (Exception ex){
            ex.printStackTrace();
        } finally{
            if(ts != null){
                try {
                    ts.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }

        return "success";






       /* SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom("twstarlord@163.com");
            //发件人的邮箱地址
//        message.setFrom(emailFrom);
            //收件人的邮箱地址
            message.setTo(mail);
            //邮件主题
            message.setSubject("账号激活码邮件!");

            String ipAddr = request.getLocalAddr();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String activeCode = UUID.randomUUID().toString() + UUID.randomUUID().toString();
            String msg = "http://" + ipAddr + ":" + serverPort + "/" + contextPath + "/StaffInfo/ActiveStaff?activecode="+activeCode+"&jobid="+jobid;
            //邮件内容<a href="http://localhost:8080/qingjia_tw/loginCheckServlet?method=active&code={0}">\u70B9\u51FB\u8FD9\u91CC\u5B8C\u6210\u6FC0\u6D3B</a>
            message.setText(msg);
            //发送邮件
            javaMailSender.send(message);
            return "success";
        } catch (MailSendException e) {
            e.printStackTrace();
            return "fail";
        }*/


    }
}
