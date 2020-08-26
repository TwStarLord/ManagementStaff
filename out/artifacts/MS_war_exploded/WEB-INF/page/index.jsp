<%@page import="com.tw.pojo.Staff"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>layuiAdmin std - 通用后台管理模板系统（iframe标准版）</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/modules/layer/default/layer.css" media="all">
  <script>
  /^http(s*):\/\//.test(location.href) || alert('请先部署到 localhost 下再访问');
  </script>
</head>
<body class="layui-layout-body">
  
  <div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
      <div class="layui-header">
        <!-- 头部区域 -->
        <ul class="layui-nav layui-layout-left">
          <li class="layui-nav-item layadmin-flexible" lay-unselect>
            <a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
              <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="http://www.layui.com/admin/" target="_blank" title="前台">
              <i class="layui-icon layui-icon-website"></i>
            </a>
          </li>
          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;" layadmin-event="refresh" title="刷新">
              <i class="layui-icon layui-icon-refresh-3"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <input type="text" placeholder="搜索..." autocomplete="off" class="layui-input layui-input-search" layadmin-event="serach" lay-action="template/search.html?keywords="> 
          </li>
        </ul>
        <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
          
          <li class="layui-nav-item" lay-unselect>
            <a lay-href="app/message/index.html" layadmin-event="message" lay-text="消息中心">
              <i class="layui-icon layui-icon-notice"></i>  
              
              <!-- 如果有新消息，则显示小圆点 -->
              <span class="layui-badge-dot"></span>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="theme">
              <i class="layui-icon layui-icon-theme"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="note">
              <i class="layui-icon layui-icon-note"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="fullscreen">
              <i class="layui-icon layui-icon-screen-full"></i>
            </a>
          </li>
          <li class="layui-nav-item" lay-unselect>
          <% 
          Staff staff = (Staff)request.getSession().getAttribute("staffinfo");
          %>
            <a href="javascript:;"><img src="${pageContext.request.contextPath}/resources/images/b.png" style="width:30px;height:30px;" class="layui-nav-img">
              <cite>欢迎<%=staff.getAccount() %>登录,当前身份:
              <%--根据保存到session中的user来获取其中是否为管理员的信息，从而达到一个界面两种效果 --%>
              <c:if test="${staffinfo.isadmin==1 }">
                           管理员
              </c:if>
              <c:if test="${staffinfo.isadmin==0 }">
                           员工
              </c:if>
              </cite>
            </a>
            <dl class="layui-nav-child">
              <dd><a lay-href="<c:url value='basic_info' />">基本资料</a></dd>
              <dd><a lay-href="set/user/password.html">修改密码</a></dd>
              <dd><a id="logout"  href="<c:url value='logout' />" >退出</a></dd>
            </dl>
          </li>
          
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="about"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li>
          <li class="layui-nav-item layui-show-xs-inline-block layui-hide-sm" lay-unselect>
            <a href="javascript:;" layadmin-event="more"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li>
        </ul>
      </div>
      
      <!-- 侧边菜单 -->
      <div class="layui-side layui-side-menu">
        <div class="layui-side-scroll">
          <div class="layui-logo" lay-href="home/console.html">
            <span>员工管理</span>
          </div>
          
          <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu">
            <!-- 权限管理start -->
            <li data-name="set" class="layui-nav-item">
              <a href="javascript:;" lay-tips="公告" lay-direction="2">
                <i class="layui-icon layui-icon-set"></i>
                <cite>系统详情</cite>
              </a>
              <dl class="layui-nav-child">
                    <dd><a lay-href="<c:url value='/System/ToFindSystemInfo'  />">设备详情</a></dd>
                    <dd><a lay-href="<c:url value='/Permission/ToFindAllPermission'  />">权限详情</a></dd>
                    <dd><a lay-href="<c:url value='/Role/ToFindAllRole'  />">角色详情</a></dd>
                  </dl>
              </dl>
            </li>
            <!-- 权限管理end -->
            <li data-name="home" class="layui-nav-item layui-nav-itemed">
              <a href="javascript:;" lay-tips="公司信息" lay-direction="2">
                <i class="layui-icon layui-icon-home"></i>
                <cite>公司信息</cite>
              </a>
              <dl class="layui-nav-child">
                <dd data-name="console" class="layui-this">
                  <a lay-href="<c:url value='/ToFindAllDepartmentInfo' />">部门情况</a>
                </dd>
                <dd data-name="console">
                  <a lay-href="">员工情况</a>
                </dd>
              </dl>
            </li>
            <li data-name="component" class="layui-nav-item">
              <a href="javascript:;" lay-tips="员工信息" lay-direction="2">
                <i class="layui-icon layui-icon-component"></i>
                <cite>员工信息</cite>
              </a>
              <dl class="layui-nav-child">
                    <dd><a lay-href="<c:url value='/findSelfStaffInfo?jobid=${staffinfo.jobid }'/>">个人查看</a></dd>
                    <dd><a lay-href="<c:url value='/ToAllStaff_Info'/>">员工信息查看</a></dd>
                    <dd><a lay-href="<c:url value='/ToAllSalary_Info'/>">员工薪资查看</a></dd>
                  </dl>
            </li>
            
            <li data-name="template" class="layui-nav-item">
              <a href="javascript:;" lay-tips="考勤信息" lay-direction="2">
                <i class="layui-icon layui-icon-template"></i>
                <cite>考勤</cite>
              </a>
              <dl class="layui-nav-child">
                <dd><a lay-href="<c:url value='/findSelfKaoqin'/>">个人考勤信息</a></dd>
                <dd><a lay-href="<c:url value='/findAllKaoqin'/>">员工考勤信息</a></dd>
              </dl>
            </li>
            
            <li data-name="app" class="layui-nav-item">
              <a href="javascript:;" lay-tips="出差管理" lay-direction="2">
                <i class="layui-icon layui-icon-app"></i>
                <cite>出差管理</cite>
              </a>
              <dl class="layui-nav-child">
                    <dd><a lay-href="<c:url value='/findSelfChuchai'/>">个人出差信息</a></dd>
                    <dd><a lay-href="<c:url value='/TofindAllChuchai'/>">员工出差信息</a></dd>
                    <dd><a lay-href="<c:url value='/addChuchai'/>">添加出差信息</a></dd>
                  </dl>
            </li>
            
            <li data-name="senior" class="layui-nav-item">
              <a href="javascript:;" lay-tips="请假管理" lay-direction="2">
                <i class="layui-icon layui-icon-senior"></i>
                <cite>请假管理</cite>
              </a>
              <dl class="layui-nav-child">
                    <dd><a lay-href="<c:url value='/findSelfQingjia'/>">个人请假信息</a></dd>
                    <dd><a lay-href="<c:url value='/TofindAllQingjia'/>">员工请假信息</a></dd>
                    <dd><a lay-href="<c:url value='/addQingjia'/>">添加请假信息</a></dd>
              </dl>
            </li>
            
            <li data-name="user" class="layui-nav-item">
              <a href="javascript:;" lay-tips="文件" lay-direction="2">
                <i class="layui-icon layui-icon-user"></i>
                <cite>文件</cite>
              </a>
              <dl class="layui-nav-child">
                    <dd><a lay-href="<c:url value='/TofindAllFile'/>">文件查看</a></dd>
                    <dd><a lay-href="<c:url value='/ToUploadFile'/>">上传文件</a></dd>
                  </dl>
            </li>
            
            <li data-name="set" class="layui-nav-item">
              <a href="javascript:;" lay-tips="公告" lay-direction="2">
                <i class="layui-icon layui-icon-set"></i>
                <cite>公告</cite>
              </a>
              <dl class="layui-nav-child">
                    <dd><a lay-href="<c:url value='ToInsertNotice'  />">发布公告</a></dd>
                    <dd><a lay-href="<c:url value='ToFindAllNotice'  />">查看公告</a></dd>
                  </dl>
              </dl>
            </li>
            
            
            
          </ul>
        </div>
      </div>

      <!-- 页面标签 -->
      <div class="layadmin-pagetabs" id="LAY_app_tabs">
        <div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-down">
          <ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
            <li class="layui-nav-item" lay-unselect>
              <a href="javascript:;"></a>
              <dl class="layui-nav-child layui-anim-fadein">
                <dd layadmin-event="closeThisTabs"><a href="javascript:;">关闭当前标签页</a></dd>
                <dd layadmin-event="closeOtherTabs"><a href="javascript:;">关闭其它标签页</a></dd>
                <dd layadmin-event="closeAllTabs"><a href="javascript:;">关闭全部标签页</a></dd>
              </dl>
            </li>
          </ul>
        </div>
        <div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
          <ul class="layui-tab-title" id="LAY_app_tabsheader">
            <li lay-id="home/console.html" lay-attr="home/console.html" class="layui-this"><i class="layui-icon layui-icon-home"></i></li>
          </ul>
        </div>
      </div>
      
      
      <!-- 主体内容 -->
      <div class="layui-body" id="LAY_app_body">
        <div class="layadmin-tabsbody-item layui-show">
          <iframe src="${pageContext.request.contextPath}/layui/views/home/console.html" frameborder="0" class="layadmin-iframe"></iframe>
        </div>
      </div>
      
      <!-- 辅助元素，一般用于移动设备下遮罩 -->
      <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
  <script>
  layui.config({
    base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use('index');
  </script>
</body>
</html>
