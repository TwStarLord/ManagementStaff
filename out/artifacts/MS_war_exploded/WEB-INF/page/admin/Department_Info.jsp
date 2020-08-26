<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>面板</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
</head>
<body>
<%--这里用来测试数据有没有被放到session中${staffinfo.mail}--%>

  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">部门信息</div>
          <div class="layui-card-body" >
            <div class="layui-collapse" lay-filter="component-panel" id="showDepartInfo">

        </div>
      </div>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>  
  <script>
  layui.config({
    base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index','jquery','element','admin','table','layer'], function(){
    var $ = layui.jquery
    ,admin = layui.admin
    ,element = layui.element,
    table = layui.table,
    layer = layui.layer
    ,router = layui.router();;

    element.render('collapse');
    
    //监听折叠
    element.on('collapse(component-panel)', function(data){
    	layer.msg('展开状态：'+ data.show);
    });
    
    $(function(){
    	$.ajax({
    		type:"post",
    		url:"${pageContext.request.contextPath}/Department/FindAllDepartmentInfo",
    		success:function(data){
    			
    			if(data!=null){
    				var content1 ="";
    				for(var i = 0;i<data.length;i++){
    					
    					content1 +="<div class='layui-colla-item'><h2 class='layui-colla-title'>" + data[i].departname +"<i class='layui-icon layui-colla-icon layui-icon-right'></i></h2><div class='layui-colla-content'>"
    	                     
    						 +"</div></div>";
    				}
    				$(content1).appendTo("div#showDepartInfo");
    				var $depart = $("div#showDepartInfo > div.layui-colla-item > div.layui-colla-content");
    				for(var i = 0;i<data.length;i++){
    					var content2 ="";
    					var stafflist = data[i].stafflist;
    					console.log(stafflist);
    					//这里用来显示对应的数据表格 style='display:none;'
   					 content2 +="<div class='layui-fluid'><div class='layui-row layui-col-space15'><div class='layui-col-md12'><div class='layui-card'>"
   					 +"<div class='layui-card-body'>"
   					 +"<table class='layui-table'>"
   					 +"<colgroup><col width='150'><col width='150'><col width='200'><col><col><col><col><col><col><col><col><col><col><col></colgroup>"
   					 +"<thead><tr><th>工号</th><th>姓名</th><th>账号</th><th>部门编号</th><th>部门名称</th><th>性别</th><th>出生日期</th><th>学历</th><th>手机</th><th>邮箱</th><th>地址</th><th>工作状态</th><th>入职时间</th><th>管理员</th></tr></thead>"
   					 +"<tbody>";
   					 for(var j = 0;j<stafflist.length;j++){
   					 content2 +="<tr><td>"+ stafflist[j].jobid + "</td>"
   					 +"<td>"+ stafflist[j].name + "</td>"
   					 +"<td>"+ stafflist[j].account + "</td>"
   					 +"<td>"+ stafflist[j].departid + "</td>"
   					 +"<td>"+ stafflist[j].departname + "</td>"
   					 +"<td>"+ stafflist[j].sex + "</td>"
   					 +"<td>"+ stafflist[j].birthday + "</td>"
   					 +"<td>"+ stafflist[j].eduback + "</td>"
   					 +"<td>"+ stafflist[j].mobile + "</td>"
   					 +"<td>"+ stafflist[j].mail + "</td>"
   					 +"<td>"+ stafflist[j].address + "</td>"
   					 +"<td>"+ stafflist[j].status + "</td>"
   					 +"<td>"+ stafflist[j].timeforjob + "</td>"
   					 +"<td>";
   					 if(stafflist[j].isadmin==1){
   						content2 +="是";
   					 }else if(stafflist[j].isadmin==0){//后期需要拓展角色
   						content2 +="否";
   					 }
   					 content2 +="</td></tr>";
   					 }
   					 content2 +="</tbody></table>"          
   					 +"</div></div></div></div></div>"
   					//数据表格结束  对应每个部门都要添加  div#showDepartInfo 
   					$(content2).appendTo($depart[i]);
   					//content2 = "";
   					console.log($depart[i]);
    				}
    				
    				//绑定事件 
    				var $content = $("div#showDepartInfo > div.layui-colla-item > div.layui-colla-content");
    				var $icon = $("div#showDepartInfo i.layui-icon");
    				$("div.layui-colla-item").each(function(i,item){
    					$(item).on('click',function(){
    						
    						/* alert($content.length); */
    						
    						var index1 = $($content[i]);
    						var index2 = $($icon[i]);
    						console.log(index1);
    						console.log(index2);
    						if(index1.hasClass("layui-show")){//被显示了
    							index2.removeClass("layui-icon-down");
    	    					index2.addClass("layui-icon-right");
    						    index1.removeClass("layui-show");
    						}else {
    							index2.removeClass("layui-icon-right");
    							index2.addClass("layui-icon-down");
    							index1.addClass("layui-show");
    						}
    						
    						//alert("hello");
    						/* 效果相同，使用的话分情况
    						console.log($(this));
        					console.log($(item)); 
        					var $table = $(item).find("table.layui-hide");
        					alert($table.length); 
        					console.log($table);
        					console.log($table.attr('style'));
        					alert(typeof($table.attr("style")));
        					console.log($table);
        					var flag = "display:none;"==$table.attr("style")?true:false;
        					//alert(flag);
        					//alert($table.attr("style"));
        					if(flag){//显示
        						$table.attr("style","display:block;");
        					    //
        					
        					}else{//隐藏
        						$table.attr("style","display:none;");
        					} */
        					/* if($table.is(":hidden")){//该表格不显示
        						alert("显示");
        						$table.show();
        						alert($table.attr("style"));
        					}else if($table.is(":visible")){
        						alert("隐藏");
        						$table.hide();
        					}*/
    					})
    					
    					
    				});
    		}
    		},
    		error:function(data){
    			layer.msg("数据错误,请刷新重试!",{icon:2,time:2000});
    		}
    		
    	});
    	
    });
    
    /* table.render({
                        elem: "#depatmentTable1"
                        ,url:'${pageContext.request.contextPath}/findStaffInfoByDeparId' 
                        ,toolbar: true
                        ,title: '用户数据表'
                        ,totalRow: true
                        ,cols: [[
    	                              {type:'checkbox'}
    	  ,{field:'jobid', width:80, title: '工号', sort: true}
    	    ,{field:'name', width:80, title: '姓名'}
    	    ,{field:'image', width:80, title: '头像'}
    	    ,{field:'account', width:80, title: '账号'}
    	    ,{field:'departid', width:80, title: '部门编号'}
    	    ,{field:'departname', title: '部门名称', minWidth: 80}
    	    ,{field:'sex', width:80, title: '性别'}
    	    ,{field:'birthday', width:80, title: '生日'}
    	    ,{field:'eduback', width:80, title: '学历'}
    	    ,{field:'mobile', width:135, title: '手机'}
    	    ,{field:'mail', width:135, title: '邮箱'}
    	    ,{field:'address', width:135, title: '地址'}
    	    ,{field:'status', width:135, title: '状态'}
    	    ,{field:'timeforjob', width:135, title: '入职日期'}
    	    ,{field:'isadmin', width:80, title: '管理员'}
    	    ,{field:'right', width:130, toolbar:'#btndemo'}
    	                            ]]
    	                            ,page: true
    	                            ,response: {
    	                          statusCode: 0 //重新规定成功的状态码为 200，table 组件默认为 0
    	                          
    	                    }
    	                    ,parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
    	                      return {
    		                    "data": res.data,//解析数据列表
    		                    "count": res.count,//解析数据长度
    		                    "msg": res.msg,//解析提示文本
    	                        "code": res.code //解析接口状态 
    	                      };
    	                    }
    	                  });
    	  
    	         table.on('tool(demo)',function(obj){
    	        	 var data = obj.data;
    	        	 if(obj.event=='delete'){
    	        		 layer.confirm('确认要删除该员工信息吗?',function(index){
    	        			 window.location='${pageContext.request.contextPath}/DeleteStaff1?jobid='+data.jobid;
    	        			 return false;
    	        		 })
    	        	 }else if(obj.event='edit'){
    	        		        layer.open({
    	        		          type: 2,
    	        		          title: '员工信息编辑',
    	        		          shade: false,
    	        		          maxmin: true,
    	        		          area: ['90%', '90%'],
    	        		          content: '${pageContext.request.contextPath}/ToUpdateStaff1?jobid='+data.jobid
    	        		        });
    	        		 return false;
    	        	 }
    	         }) */
    
  });
  </script>
  <script type="text/html" id="btndemo">
   <a class='layui-btn  layui-btn-xs layui-btn' lay-event='edit' data-type='editbtn'>编辑</a>
   <a class='layui-btn  layui-btn-xs layui-btn-danger' lay-event='delete'>删除</a>
  </script>
</body>
</html>