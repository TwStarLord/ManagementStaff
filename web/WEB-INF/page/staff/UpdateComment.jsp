<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>公告评论</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
</head>
<body>

  <div class="layui-fluid">
    <div class="layui-card">
      <div class="layui-card-header">评论</div>
        <div class="layui-card-body" style="padding: 15px;">
          <form class="layui-form"  id="Form"  lay-filter="commentForm">
            <div class="layui-form-item">
            <label class="layui-form-label">标题</label>
              <div class="layui-input-block">
              <input type="text"   value="${noticeInfo.title }"  autocomplete="off"  class="layui-input" disabled="disabled">
              </div>
            </div>
          
          
          
          <div class="layui-form-item">
            <div class="layui-inline">
              <label class="layui-form-label">日期</label>
              <div class="layui-input-inline" style="width: 100px;">
                <input type="text"   autocomplete="off" value="${noticeInfo.date }"  class="layui-input"  disabled="disabled">
              </div>
              <div class="layui-form-mid">-</div>
              <label class="layui-form-label">发布者</label>
              <div class="layui-input-inline" style="width: 100px;">
                <input type="text"   autocomplete="off"  value="${noticeInfo.author }"  class="layui-input" disabled="disabled">
              </div>
            </div>
          </div>

          <%-- <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">内容</label>
            <div class="layui-input-block">
              <textarea  name="content"  id="content" autocomplete="off"  value="${noticeInfo.content }"   class="layui-textarea" disabled="disabled">${noticeInfo.content }</textarea>
            </div>
          </div>  --%>       
          <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">评论</label>
            <div class="layui-input-block">
              <textarea name="comment"  id="comment" lay-filter="commentOfNotice"  class="layui-textarea" lay-verify="required"></textarea>
            </div>
          </div>    
          <!-- 公告编号 -->
          <input  name="noticeid"  value="${noticeInfo.id }"  type="hidden"  />    
          <div class="layui-form-item layui-layout-admin">
            <div class="layui-input-block">
              <div class="layui-footer" style="left: 0;">
                <button type="submit" class="layui-btn" lay-submit="" lay-filter="commentUpdateBtn">提交</button>
                <button type="reset" id="reset" class="layui-btn layui-btn-primary">重置</button>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>

    
  <script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>  
  <script>
  layui.config({
    base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['form','jquery','layer','layedit'], function(){
    
    
    $(function(){
    	var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;
        var layedit = layui.layedit;
    	var con = layedit.build('comment'); //建立编辑器
    	form.on('submit(commentUpdateBtn)',function() {
    		  console.log($("#Form").serialize());
    		  var con = layedit.build('comment'); //建立编辑器
    		  $.ajax({
    			    url:"${pageContext.request.contextPath}/InsertCommentInfo",
    				type:"POST",
    				data:($("#Form").serialize()),
    				async:true,
    				success:function(data) {
    					if (data == "success") {
    						layer.msg("评论成功!",{icon : 1,anim : 4,time : 2000});
    						/* function() {//弹出成功之后等等待两秒窗口关闭
    							var index = parent.layer.getFrameIndex(window.name);
    							parent.layer.close(index);
    	 				}) */
    				}else {
    					layer.msg("评论失败!",{icon : 5,anim : 6,time : 2000});
    					}
    			  
    		  }
    		  });
          
    	     //经过测试，可以发现该ajax请求会导致选单向reload，从而无法根据返回值为success或者fail而发出弹窗，所以需要加上该句，防止页面重reload
    	      form.render();
    	      return false;
    });

        form.verify({
        	commentOfNotice:function(value){
        		console.log(con);
        		layedit.sync(con);
         }
      });
    });

  });
  </script>
</body>
</html>