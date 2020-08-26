<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>时间线</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid" id="LAY-component-timeline">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">常规时间线</div>
          <div class="layui-card-body">

            <ul class="layui-timeline" id="showNotice">
              
            </ul>
          </div>
        </div>
      </div>
   </div>
 </div>
<script src="${pageContext.request.contextPath }/layui/layuiadmin/layui/layui.js"></script>  
  <script>
  layui.config({
	    base: '${pageContext.request.contextPath }/layui/layuiadmin/' //静态资源所在路径
	  }).extend({
	    index: 'lib/index' //主入口模块
	  }).use(['index','jquery','layer','layedit'],function(
	  ){
		  var $ = layui.jquery,
		  layer = layui.layer,
		  form = layui.form,
		  layedit = layui.layedit;
		  //var flag = false;//用来控制评论文本域的显示与隐藏
		  /* var contentofNotice = null; */
	  
	  //-----------------------------start---------------------------------------
	  
	  
	  //-----------------------------end----------------------------------------
	  
	  //$(function(){
		  $.post('${pageContext.request.contextPath }/Notice/findAllNotice',function(data){
			  var content = "";
			 /*  var flag = false;  */
			  for(var i = 0;i < data.allNotice.length;i++){
				  content+="<li class='layui-timeline-item'><i class='layui-icon layui-timeline-axis'>&#xe63f;</i>"+
				  "<div class='layui-timeline-content layui-text'><h3 class='layui-timeline-title'>"+
				  //收藏按钮
				  "<div class='layui-btn-container'>";
				  for(var j =0;j<data.staffNotice.length;j++){
					  if(j == (data.staffNotice.length-1) && data.allNotice[i].id != data.staffNotice[j].noticeid){//未被收藏
						  content+="<button name='collect' class='layui-btn layui-btn-danger' choose='false' value='"+ data.allNotice[i].id +"'  ><i    class='layui-icon'>&#xe600;</i></button>";
						  break;
					  }else if(data.allNotice[i].id == data.staffNotice[j].noticeid){//此公告已经被该员工收藏了  || j == (data.staffNotice.length)
						  content+="<button name='collect' class='layui-btn layui-btn-danger' choose='true'  value='"+ data.allNotice[i].id +"'  ><i    class='layui-icon'>&#xe658;</i></button>";
						  break;
					  }
				  }
				  //评论按钮
				  content+="<button class='layui-btn'  name='comment' value='"+data.allNotice[i].id+"'><i class='layui-icon'>&#xe63a;</i></button></div>"
				  +data.allNotice[i].date+"---"+data.allNotice[i].title+"</h3><p>"
				  +data.allNotice[i].content+"</p></div>"
				  //这里需要区分评论表单    通过button中函数所固定的值（需要在数量较小的情况下使用）
				  +"<form style='display:none;'  class='layui-form'>"+
					//通过隐藏输入框利来控制noticeid jobid通过后台session取值
					"<input type='hidden'   name='noticeid' value='  " + data.allNotice[i].id +" '/>"+
				  "<div class='layui-card-body layui-row layui-col-space10'><div class='layui-col-md12'>"+
				  "<textarea name='comment' placeholder='请输入' class='layui-textarea'   ></textarea></div>"+
				  "<div class='layui-btn-container' id='container' ><button  type='button' class='layui-btn layui-btn-danger' lay-submit='' >"+
				  "提交</button>"+
				  "<button class='layui-btn' type='reset'>重置</button></div></div></form>"+
				  "</li>";
			  }
			   $(content).appendTo("ul#showNotice");
			   //给收藏按钮添加点击事件
			   //======================================收藏事件start================================
			   var cbtnlist = $("#showNotice li button[name='collect']");
			   $("#showNotice li").each(function(i,item){
				   cbtnlist.eq(i).on("click",function(){
				   var noticeid = $(this).val();
				   var choose = $(this).attr("choose");
				   var cbtn = $(this);
				   /* alert(typeof(choose)); */
				   if(choose == "true"){//为true说明已经被收藏过了，弹窗提示取消收藏
					   layer.confirm("取消收藏吗？",
					   {
				   			btn:['确定','取消']
					   },function(index,layero){//按钮一的回调
						   cbtn.attr("choose","false");
						   cbtn.html("<i class='layui-icon'>&#xe600;</i>");//换图标 
					       //ajax请求
					       $.ajax({
					    	   url:"${pageContext.request.contextPath}/Collects/DeleteCollection",
					    	   data:{noticeid:noticeid},
					    	   type:"POST",
					    	   success:function(data){
					    		   if(data=="success"){
					    			   layer.msg("取消收藏成功!",{icon:1,time:2000}); 
					    		   }else{
					    			   layer.msg("取消收藏失败!",{icon:1,time:2000}); 
					    		   }
					    	   } 
					       });  
						   
					   },function(index){//按钮二的回调
						   layer.close(index);
					   }
					   );
				       
				   }else if(choose == "false"){//点击进行收藏
					   
					   $.post("${pageContext.request.contextPath}/Collects/InsertCollection",{noticeid:noticeid},function(data){
						 	if(data=="success"){
						 	        layer.msg("收藏成功!",{icon:1,time:2000});
						 	        cbtn.attr("choose","true");
						 	        cbtn.html("<i class='layui-icon'>&#xe658;</i>");
						 	        /* $(this).html("<i class='layui-icon'>&#xe658;</i>");//换图标 */
						 	}else{
						 		    layer.msg("收藏失败,请重试!",{icon:2,time:2000});
						 	}
						 	
						   
					   }) 
					   
				   }
					   
				   });
				   
			   });
			   //======================================收藏事件end================================
				   
			   
			   //给评论按钮绑定显示富文本编辑器事件
			   //===============================绑定富文本编辑器start===================================
			   var $formlist = $("#showNotice li.layui-timeline-item").find("form.layui-form");
			   var array = new Array([$formlist.length]);//定义一个数组用来存放对应的编辑器返回值
			   $("#showNotice").find("li.layui-timeline-item").each(function(i,item){
				   //每次遍历要给form下的textarea绑定对应的id值，以便通过id来生成编辑器实例
				   $($formlist[i]).attr("id","form"+i);
				   //$($formlist[i]).attr("lay-filter","subcom"+i);
				   $(this).find("textarea.layui-textarea").attr("id","edit"+i);
				   $(this).find("textarea.layui-textarea").attr("lay-filter","sycncom"+i);//这里为了form表单验证做铺垫
				   //console.log($(this).find("textarea.layui-textarea"));
				   var id = "edit"+i;
				   var commentNotice = layedit.build(id); //建立编辑器
				   array.push(commentNotice);//索引编辑器
				   $(item).find("button[name='comment']").on('click',function(){
					   //给每个textarea绑定富文本编辑器
					   //var id = "edit"+i;
					   //console.log(id);
					   //var commentNotice = layedit.build(id); //建立编辑器
					   //array.push(commentNotice);
					   //console.log($($formlist[i]).attr("style"));
					   if($($formlist[i]).attr("style")=="display:none;"){//为空说明已经被显示了，所以进行隐藏
						   //$($formlist[i]).show();  后期使用is:visiable  和  is:hidden进行判断
						   $($formlist[i]).attr("style","display:block;");
					   }else if($($formlist[i]).attr("style")=="display:block;"){
						   //$($formlist[i]).hide();
						   $($formlist[i]).attr("style","display:none;");
					   }
				   });  
			   });
			 //===============================绑定富文本编辑器end===================================
				 //console.log($("#showNotice div#container").find("button:first"));
				 $("#showNotice div#container").find("button:first").each(function(i,item){
					 $(item).attr("lay-filter","subcom"+i);
				 
				 });
				 
			   //给提交按钮绑定提交事件，无法使用form.on  不知道全局变量的定义范围
			    var sublist = $("#showNotice div#container").find("button:first");
			    var nidlist = $("#showNotice form.layui-form").find("input[name='noticeid']");
			    $("#showNotice div#container").find("button:first").each(function(i,item){//选择器
			   	   //对button的filter赋值 lay-verify
			       $(item).attr("lay-filter","subcom"+i);
			       //在绑定富文本编辑器时已经对form的id和lay-filter属性进行了赋值
				   //$(item).on("click",function(){
				   var id = "form"+i;
				   //var fid = "subcom"+i;
				   var subform = $($formlist[i]);
				   var fid = $(sublist[i]).attr("lay-filter");
				   //form.on('submit(fid)',function() {
					   $(this).on('click',function(){
				  /*  console.log($(this));
				   console.log(array.length);
				   console.log(array); */
				   /* console.log(array[i+1]);
				   console.log(typeof(array[i+1]));
				   console.log(layedit.getContent(array[i+1])); */
				   console.log($(nidlist[i]).val());
				   var commentvalue = layedit.getContent(array[i+1]);
				   var noticeid = $(nidlist[i]).val();
				   //强行获取最为致命
				   $.ajax({
					   url:"${pageContext.request.contextPath }/Comment/InsertCommentInfo",
					   data:{noticeid:noticeid,comment:commentvalue},
					   async:true,
					   type:"post",
					   success:function(data){
					   if(data=="success"){//评论成功，弹窗提示
							 layer.msg("评论成功!",{icon : 1,anim : 4,time : 2000});
							 subform.attr("style","display:none;");
						 }else{//评论失败，弹窗提示
							 layer.msg("评论失败!",{icon : 1,anim : 4,time : 2000});
						 }   
					   }
				   });
					   return false;
					   })
					   
						   
					   
					     //return false;
				   		//});
			   }); 
			   
			   //设置表单提交监听
			   
			 /*  var sublist = $("#showNotice div#container").find("button:first");
			  //alert(sublist.length);
			  console.log($(sublist[0]));
			  for(var i = 0;i<$("#showNotice li").length;i++){
				   var fid = $(sublist[i]).attr("lay-filter");
				   var subform = $($formlist[i]);
				   form.on('submit(fid)',function() {
					   console.log($(this));
					   $.ajax({
						   url:"${pageContext.request.contextPath }/updateComment",
						   data:(subform.serialize()),
						   async:true,
						   type:"post",
						   success:function(data){
						   if(data=="success"){//评论成功，弹窗提示
								 layer.msg("评论成功!",{icon : 1,anim : 4,time : 2000});
								 subform.attr("style","display:none;");
							 }else{//评论失败，弹窗提示
								 layer.msg("评论失败!",{icon : 1,anim : 4,time : 2000});
							 }   
						   }
					   });
					     return false;
				   		});
			   }  */
			   
			   
			   
			   //$(this).find("textarea.layui-textarea").attr("lay-filter","sycncom"+i);//这里为了form表单验证做铺垫
			   var $textarealist = $("#showNotice").find("textarea.layui-textarea");
			  for(var i = 0;i<$("#showNotice li").length;i++){
				   var ft = $($textarealist[i]).attr("lay-filter");
				   form.verify({
					    ft:function(value){
				     	layedit.sync(array[i]);
					     }
					}); 
			   };
			   
	 
		  });
	  
	  //});	
		  
  });
  </script>
 
</body>
</html>