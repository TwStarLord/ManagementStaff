<%@page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>公告</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
</head>
<body>

  <div class="layui-fluid">
    <div class="layui-card">
      <div class="layui-card-header">公告</div>
      <div class="layui-card-body">
        <form  id="NoticeForm"  class="layui-form" lay-filter="insNotice">
          <div class="layui-form-item">
             <label class="layui-form-label">标题</label>
            <div class="layui-input-block">
              <input type="text" name="title" placeholder="标题" autocomplete="off" class="layui-input"  lay-verify="required">
            </div>
          </div>
          
           <div class="layui-form-item">
             <label class="layui-form-label">发布人</label>
           <div class="layui-input-block">
              <input type="text" name="author" value="${login_name }" autocomplete="off" class="layui-input"> 
            </div>
            </div>
            <script type="text/javascript">
            
            
            </script>
            <div class="layui-form-item">
             <label class="layui-form-label">时间</label>
            <div class="layui-input-block">
              <input type="text" name="date"  readonly="readonly"  value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>"  autocomplete="off" class="layui-input">
            </div>
            </div>
            
            <div class="layui-form-item">
                <label class="layui-form-label">内容</label>
                <div class="layui-input-block">
                  <textarea name="content" placeholder="内容" id="content"  class="layui-textarea" lay-verify="noticeContent"></textarea>
                </div>
              </div>
            
              
              <div class="layui-form-item">
                <div class="layui-input-block">
                  <button class="layui-btn" lay-submit="" lay-filter="insNoticeSbumit">提交</button>
                  <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
              </div>
              
              </form>
            
          </div>
          </div>
          </div>
</body>

<script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
  <script>
  layui.config({
    base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['form','layer','jquery','layedit'],function(){
	  var $ = layui.jquery,
	  layer = layui.layer,
	  form = layui.form,
	  layedit = layui.layedit;
	  var contentofNotice = layedit.build('content'); //建立编辑器
	  
	  
	  form.on('submit(insNoticeSbumit)',function() {
		  console.log($("#NoticeForm").serialize());
		  console.log(layedit.getContent(contentofNotice));
		  $.ajax({
			    url:"${pageContext.request.contextPath}/InsertNoticeInfo",
				type:"POST",
				data:($("#NoticeForm").serialize()),
				async:true,
				success:function(data) {
					if (data == "success") {
						layer.msg("更新成功!",{icon : 1,anim : 4,time : 2000});
						/*function() {//弹出成功之后等等待两秒窗口关闭
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
	 						parent.layui.table.reload('tableToReload');
	 				})*/
				}else {
					layer.msg("更新失败!",{icon : 5,anim : 6,time : 2000});
					}
			  
		  }
		  });
	     //经过测试，可以发现该ajax请求会导致选单向reload，从而无法根据返回值为success或者fail而发出弹窗，所以需要加上该句，防止页面重reload
	      form.render();
	      return false;
  });
  
	  form.verify({
		        noticeContent: function(value){
		     	layedit.sync(contentofNotice);
		     }
		});
  });
	  
	  
	 
  </script>
</html>