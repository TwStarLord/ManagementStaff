<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>数据表格的重载 - 数据表格</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
</head>
<body>

  <%-- <div class="layui-card layadmin-header">
    <div class="layui-breadcrumb" lay-filter="breadcrumb">
      <a lay-href="">主页</a>
      <a><cite>组件</cite></a>
      <a><cite>数据表格</cite></a>
      <a><cite>数据表格的重载</cite></a>
    </div>
  </div> --%>
  
  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">
          <div class="layui-btn-container">
          <button class="layui-btn" lay-event="batchdownload" id="batchdownload"><i class="layui-icon" >&#xe600;</i>批量下载</button>
          <button class="layui-btn layui-btn-danger" lay-event="batchdelete" id="batchdelete"><i class="layui-icon">&#xe640;</i>批量删除</button>
          </div>
          </div>
          <div class="layui-card-body">
          
            <div class="test-table-reload-btn" style="margin-bottom: 10px;">
              搜索ID：
              <div class="layui-inline">
              <!-- 由输入框获取关键字 -->
                <input class="layui-input" name="id" id="keyToReload" lay-verify="required" autocomplete="off">
              </div>
              <button class="layui-btn" data-type="reload">搜索</button>
            </div>
             
            <table class="layui-hide" id="tableToReload" lay-filter="demo"></table>
          </div>
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
  }).use(['index', 'table','jquery'], function(){
    var table = layui.table,
    $ = layui.jquery;

    //方法级渲染
    table.render({
      elem: '#tableToReload'
      ,url: '${pageContext.request.contextPath}/File/findAllFileInfo'
      ,title: '文件数据表'
      ,totalRow: true
      ,cols: [[
         {checkbox: true, LAY_CHECKED: false,fixed: true}
        ,{field:'file_id', title: '文件编号', width:80, sort: true,}
        ,{field:'file_name', title: '文件名称', width:160}
        ,{field:'file_size', title: '文件大小', width:160, sort: true}
        ,{field:'file_date', title: '上传日期', width:160}
        ,{field:'file_manager', title: '上传人', width:160,}
        ,{field:'right', width:130, title: '操作',toolbar:'#btndemo',fixed: 'right'}
      ]]
      ,page: true
      ,limits:[5,10,15]
      ,height: 'full-100'
      ,response: {//自定义返回码状态
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
    //--------------------------批量删除----------------------------------
    $("#batchdelete").click(function ()
            {
              var that = this;
                var checkStatus = table.checkStatus('tableToReload');
                var count = checkStatus.data.length;//选中的行数
                if (count > 0)
                {
                    parent.layer.confirm('确定要删除所选文件？', { icon: 3 }, function (index)
                    {
                        var data = checkStatus.data; //获取选中行的数据
                        var batchfile_id = '';
                        for (var i = 0; i < data.length; i++)
                        {
                        	batchfile_id += data[i].file_id + ",";
                        }
                        $.ajax({
                            url: '${pageContext.request.contextPath}/File/BatchDeleteFileInfo',
                            type: "post",
                            data: { 'batchfile_id': batchfile_id },
                            dataType:"json",
                            success: function (result){
                                if (result.data === "success"){
                                     parent.layer.msg('删除成功', { icon: 1, shade: 0.4, time: 1000 })
                                     // $("#search").click();//重载TABLE
                                  var index = parent.layer.getFrameIndex(window.name);
                                  parent.layer.close(index);
                                  parent.layui.table.reload('tableToReload');
                                }else if(result.data === "unauthorized"){
                                  layer.tips('无权操作!', that, {
                                    tips: 1,
                                    time: 1500, //根据需求设定时间
                                    end:function () {
                                      // console.log(parent.layer);
                                      parent.layer.closeAll();
                                        // layer.closeAll();
                                    }
                                  });
                                }else if(result.data === "fail"){
                                     parent.layer.msg("删除失败", { icon: 5, shade: [0.4], time: 1000 });
                                }else {
                                  layer.msg("未知错误!");
                                }
                                // parent.layer.close(index);
                            }
                        })
                    });
                }
                else
                    parent.layer.msg("请至少选择一条数据", { icon: 5, shade: 0.4, time: 1000 });
            });
    //-----------------------------批量删除end--------------------------------------
    
    
     //--------------------------批量下载----------------------------------
    $("#batchdownload").click(function ()
            {
    			var checkStatus = table.checkStatus('tableToReload');
        		var count = checkStatus.data.length;//选中的行数
                if (count > 0){
                	parent.layer.confirm('确定要下载所选文件？', { icon: 3 }, function (index)
              {//确认下载
                		var data = checkStatus.data; //获取选中行的数据
                		$.each(data,function(n,item){
                			layer.open({
                				type: 2,
                				shade: false,
    							resize: true,
                				offset: 'auto',
    							icon: 0,
    							title:'下載中',
    							time:1000 * n,
    							maxmin: true,
    							//链接地址后台路径
    							content: "${pageContext.request.contextPath}/DownloadFile?file_id="+item.file_id,
    							success:function(){
    								layer.msg("下载成功", {
										icon: 5,
										time: 1500
									});
    						    }
                			})
                		})
                		
                		parent.layer.close(index);	
                		layer.closeAll();
              })
                	
                }else
                    parent.layer.msg("请至少选择一条数据", { icon: 5, shade: 0.4, time: 1000 });
            });
				//====================================================================================
                
              /*   if (count > 0)
                {
                    parent.layer.confirm('确定要下载所选文件？', { icon: 3 }, function (index)
                    {
                        var data = checkStatus.data; //获取选中行的数据
                        for (var i = 0; i < data.length; i++)
                        {
                        $.ajax({
                            url: '${pageContext.request.contextPath}/DownloadFile',
                            type: "post",
                            data: { 'file_id': data[i].file_id }
                        })
                      }
                        success: function (result){
                            if (result=="success"){
                                 parent.layer.msg('下载成功', { icon: 1, shade: 0.4, time: 1000 })
                                 $("#search").click();//重载TABLE
                            }else{
                                 parent.layer.msg("下载失败", { icon: 5, shade: [0.4], time: 1000 });
                            }
                            parent.layer.close(index);
                        } 
                        
                    });
                }
                else
                    parent.layer.msg("请至少选择一条数据", { icon: 5, shade: 0.4, time: 1000 });
            }); */
    //-----------------------------批量下载end--------------------------------------
    
    
    var $ = layui.$, active = {
      reload: function(){
        var demoReload = $('#keyToReload');
        //需要加上查询条件检查，为空则不进行查询，并给出提示
        if(demoReload.val()==null||demoReload.val()==''){
        	layer.msg("请输入查询关键字!",{icon: 5})
        	return false;
        }else{
        	   //执行重载
            table.reload('tableToReload', {
              method:'POST',
              page: {
                curr: 1 //重新从第 1 页开始
              }
              ,where: {
            	  file_id:demoReload.val()
              }
            });
        }
     
      }
    };
    
    $('.test-table-reload-btn .layui-btn').on('click', function(){
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });
    
    table.on('tool(demo)',function(obj){
   	 var data = obj.data;
      var that = this;
   	 if(obj.event == 'delete'){
       layer.confirm('确认要删除该员工信息吗?',function(index){
         $.ajax({
           url:"${pageContext.request.contextPath}/File/DeleteFileInfo",
           type:"post",
           data:{file_id:data.file_id},
           dataType:"json",
           success:function (result) {
             if(result.data === "success"){
               layer.msg('删除成功!', {icon: 2});
               var index = parent.layer.getFrameIndex(window.name);
               parent.layer.close(index);
               parent.layui.table.reload('tableToReload');
             }else if(result.data === "fail"){
               layer.msg('删除失败!', {icon: 2});
             }else if(result.data === "unauthorized"){
               layer.tips('无权操作!', that, {
                 tips: 1,
                 time: 1500, //根据需求设定时间
                 end:function () {
                    layer.closeAll();
                 }
               });
             }else {
               layer.msg('服务器错误!', {icon: 2});
             }
           },
           error:function (error) {
             layer.msg('请求失败，请重新尝试!', {icon: 2});
           }
         })
       })
   	 }else if(obj.event == 'download'){
   		 window.location='${pageContext.request.contextPath}/File/DownloadFile?file_id='+data.file_id;
   		 return false;
   	 }
    })
  
  });
  </script>
	<script type="text/html" id="btndemo">
   <a class='layui-btn  layui-btn-xs layui-btn' lay-event='download' data-type='download'>下载</a>
   <a class='layui-btn  layui-btn-xs layui-btn-danger' lay-event='delete'>删除</a>
  </script>
</body>
</html>