<%@page import="com.tw.pojo.Staff"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>xxx公司员工管理系统</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/modules/layer/default/layer.css" media="all">
  <script>
  /^http(s*):\/\//.test(location.href) || alert('请先部署到 localhost 下再访问');
  </script>
</head>
<body layadmin-themealias="ocean-header">
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

          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;" layadmin-event="refresh" title="刷新">
              <i class="layui-icon layui-icon-refresh-3"></i>
            </a>
          </li>
          <%--<li class="layui-nav-item layui-hide-xs" lay-unselect>
            <input type="text" placeholder="搜索..." autocomplete="off" class="layui-input layui-input-search" layadmin-event="serach" lay-action="template/search.html?keywords="> 
          </li>--%>
            <%--考勤打卡按钮--%>
            <li class="layui-nav-item layui-hide-xs" lay-unselect>
                <%--<a href="http://www.layui.com/admin/" target="_blank" title="前台">
                  <i class="layui-icon layui-icon-website"></i>
                </a>--%>
                <button class="layui-btn layui-btn-normal layui-btn-radius" id="kaoqinCheck">打卡</button>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
          
          <li class="layui-nav-item" lay-unselect id="news">
            <a lay-href="${pageContext.request.contextPath}/MessageInfo/ToFindNewMessage" layadmin-event="message" lay-text="消息中心">
              <i class="layui-icon layui-icon-notice"></i>
              
              <!-- 如果有新消息，则显示小圆点 -->
<%--              <span class="layui-badge-dot"></span>--%>
            </a>
          </li>

<%--          <li class="layui-nav-item layui-hide-xs" lay-unselect>--%>
<%--            <a href="javascript:;" layadmin-event="theme">--%>
<%--              <i class="layui-icon layui-icon-theme"></i>--%>
<%--            </a>--%>
<%--          </li>--%>
<%--          <li class="layui-nav-item layui-hide-xs" lay-unselect>--%>
<%--            <a href="javascript:;" layadmin-event="note">--%>
<%--              <i class="layui-icon layui-icon-note"></i>--%>
<%--            </a>--%>
<%--          </li>--%>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" layadmin-event="fullscreen">
              <i class="layui-icon layui-icon-screen-full"></i>
            </a>
          </li>
          <li class="layui-nav-item" lay-unselect>
          <%--<%
            Staff staff = (Staff)session.getAttribute("staffinfo");
            %>--%>
            <a href="javascript:;"><img src="${pageContext.request.contextPath}/${staffinfo.image}" style="width:30px;height:30px;" class="layui-nav-img">
              <cite>${staffinfo.account}<%--欢迎<%=staff.getAccount() %>--%>【${staffinfo.roleList[0].name}】登录
              </cite>
            </a>
            <%--${pageContext.request.contextPath}/StaffInfo/ToBasicInfo?jobid=${staffinfo.jobid}--%>
            <dl class="layui-nav-child">
              <dd><a lay-href="${pageContext.request.contextPath}/StaffInfo/ToBasicInfo" >基本资料</a></dd>
              <dd><a lay-href="${pageContext.request.contextPath}/StaffInfo/ToChangePassword">修改密码</a></dd>
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
          
          <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="leftmenu" lay-filter="layadmin-system-side-menu">
          
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
          <iframe src="${pageContext.request.contextPath}/Login/ShowConsole" frameborder="0" class="layadmin-iframe"></iframe>
        </div>
      </div>
      
      <!-- 辅助元素，一般用于移动设备下遮罩 -->
      <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
  </div>
  <% 
  String path = request.getServletContext().getContextPath();
  pageContext.setAttribute("path",path);
  %>
  
  <script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
  <script>
  layui.config({
    base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index','jquery','layer'],function(){
	  
	  var $ = layui.jquery,
	  layer = layui.layer;
	  var jobid = null;
	  // var name = null;


	  
	  $(function(){
	    console.log(<%=((Staff)request.getAttribute("staffinfo")).getPermissionUrlList().size()%>);
	    jobid = <%=((Staff)request.getAttribute("staffinfo")).getJobid() %>;
		  $.ajax({//开始请求权限菜单
			  url:"${pageContext.request.contextPath}/Role/FindAllPermissionById",
			  type:"post",
			  data:{jobid:jobid},
			  success:function(data){
			      console.log(data.list);
				  if(data.code==0){//code为0说明根据角色查找权限成功
				   //console.log(data);
				   var $leftmenu = $("#leftmenu");
				   var content = "";
				   //athourlist 和  menulist分别装载了权限和权限下列菜单   \" <c:url value=' "+ itemj.url +" ' /> \"
				   $.each(data.list,function(i,item){
					   //console.log(item);
					   content+="<li data-name='set' class='layui-nav-item'>"+
			              "<a href='javascript:;' lay-tips='"+ item.name +"' lay-direction='2'>"+
			                "<i class='layui-icon layui-icon-set'></i>"+
			                "<cite>"+item.name+"</cite></a>"+
			                "<dl class='layui-nav-child'>";
			                 $.each(item.childperlist,function(j,itemj){
			                	 //console.log(itemj);
			                content+="<dd><a lay-href='${pageContext.request.contextPath}"+ itemj.url +" '>"+ itemj.name +"</a></dd>";
			                //content+="<dd><a lay-href='javascript:;' value='${pageContext.request.contextPath}"+ itemj.url +"'>"+ itemj.name +"</a></dd>";
			                 }); 
			              content+="</dl></li>";
				   });
				   $(content).appendTo($leftmenu);
				   //console.log($(content));
				   //li标签事件失效，只能委托,点击li标签时展开
				   //console.log($("ul#leftmenu li"));
				   //console.log($("ul#leftmenu li").find("dd>a"));
				   function stop(event){
					   event.stopPropagation();
				   }
				   
				   $("ul#leftmenu li").find("dd>a").each(function(i,item){
					 $(this).attr("onclick","stop()");
					   
				   });
				   
				   $("ul#leftmenu li").on("click",function(){
					   //console.log($(this).find("dl.layui-nav-child"));
					 //$(this).find("dl.layui-nav-child").attr("style","display:block;");
					 $(this).find("dl.layui-nav-child").toggle();
					 console.log($(this).find("dl.layui-nav-child").is(":hidden"));
					 if(!$(this).find("dl.layui-nav-child").is(":hidden")){//显示状态
						 console.log($(this));
					     $(this).siblings().find("dl.layui-nav-child").hide();
					 }
				   });
				   //console.log($("ul#leftmenu li").find("dd"));
				   $("ul#leftmenu li").find("dd>a").on("click",function(event){
					    console.log($(this));
					    var $li = $(this).parent().parent().parent();
					    //不阻止事件冒泡
					    $li.find("dl.layui-nav-child").hide();
					    $li.siblings().find("dl.layui-nav-child").hide();

				   });

				   // ajax轮询，如果有新的消息未处理就显示小红点
				   setInterval(function () {
                      $.ajax({
                        type:"post",
                        url:"${pageContext.request.contextPath}/NewsInfo/GetPendingNews/",
                        data:{jobid:jobid},
                        success:function (data) {
                          if(data>0){//说明有最新消息，显示小红点提示信息
                              // 这里加上layer右下角弹出并提示信息
                            $("#news").find("a").append("<span class='layui-badge-dot'></span>");
                          }
                     }
                      })
                   },1000*30);//每30s就发一个异步请求，如果有最新的消息就显示小红点



                    // 打卡按钮绑定事件
                    $("#kaoqinCheck").on("click",function () {
                    <%--name = <%=((Staff)request.getAttribute("staffinfo")).getName() %>;--%>
                      $.ajax({
                        url:"${pageContext.request.contextPath}/Kaoqin/KaoqinCheck",
                        type:"post",
                        // data:{jobid:jobid,name:name},
                        success:function (data) {
                          if (data.code == 500){
                            layer.msg(data.msg,{icon : 5,anim : 6,time : 1500});
                          }else if (data.code == 200 ){
                            layer.msg(data.msg,{icon : 4,anim : 6,time : 1500});
                          }

                        },
                        error:function (error) {
                          layer.msg("请求失败，请重试！",{icon : 5,anim : 6,time : 1500});
                        }
                      })
                    })
				   
				  }else {
					  layer.msg(data.msg,{icon : 5,anim : 6,time : 2000});
				  }
			  },
			  error:function(data){
				  layer.msg("权限菜单请求失败,请重试!",{icon : 5,anim : 6,time : 2000});
			  }
		  });
	  
  	});
	  
  });
  </script>
</body>
</html>