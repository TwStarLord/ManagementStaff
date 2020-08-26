<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>员工出差信息</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
</head>
<body>
  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">
          <div class="layui-btn-container">
          <button class="layui-btn" lay-event="batchdownload" id="addSalary"><i class="layui-icon" >&#xe600;</i>添加</button>
          <button class="layui-btn layui-btn-danger" lay-event="batchdelete" id="batchdelete"><i class="layui-icon">&#xe640;</i>批量删除</button>
            <%--粗粒度权限验证，是否拥有权限验证代码，否则不进行显示，但是url拦截仍需在后台配置--%>
              <shiro:hasPermission name="salary:excelexport">
                <button class="layui-btn layui-btn-primary" lay-event="exportExcel" id="exportExcel"><i class="layui-icon">&#xe640;</i>导出Excel</button>
            </shiro:hasPermission>
          </div>
          </div>
          <div class="layui-card-body">
          
            <div class="test-table-reload-btn" style="margin-bottom: 10px;">
              
              <!-- 由输入框获取关键字 -->
              <div class="layui-inline">
                <input class="layui-input" name="name" id="nameToReload"  autocomplete="off" placeholder="姓名">
              </div>
              <div class="layui-inline">
                    <select class="layui-input" name="departid" lay-filter="" id="depaetmentinfo"></select>
              </div>
              <div class="layui-inline">
                  <select class="layui-input" name="jiesuanyuefen" id="jiesuanyuefenToReload"  autocomplete="off" placeholder="结算月份"></select>
              </div>
            <div class="layui-inline">
              <div class="layui-input-inline" style="width: 100px;">
                <input type="text" name=minjibengongzi placeholder="min基本工资"  id="minjibengongziToReload" autocomplete="off" class="layui-input">
              </div>
              ---
              <div class="layui-input-inline" style="width: 100px;">
                <input type="text" name="maxjibengongzi" placeholder="max基本工资" id="maxjibengongziToReload"  autocomplete="off" class="layui-input">
              </div>
             </div>
              <div class="layui-inline">
                <input class="layui-input" name="jiangli" id="jiangliToReload"  autocomplete="off" placeholder="奖金">
              </div>
              <div class="layui-inline">
                <input class="layui-input" name="chengfa" id="chengfaToReload"  autocomplete="off" placeholder="惩金">
              </div>
              <div class="layui-inline">
                <input class="layui-input" name="jiabangongzi" id="jiabangongziToReload"  autocomplete="off" placeholder="加班">
              </div>
              <div class="layui-inline">
                <input class="layui-input" name="kuanggonggongzi" id="kuanggonggongziToReload"  autocomplete="off" placeholder="旷工">
              </div>
              <div class="layui-inline">
                <input class="layui-input" name="qingjiagongzi" id="qingjiagongziToReload"  autocomplete="off" placeholder="请假">
              </div>
              <div class="layui-inline">
                <input class="layui-input" name="chuchaigongzi" id="chuchaigongziToReload"  autocomplete="off" placeholder="出差">
              </div>
              
              <div class="layui-inline">
              <div class="layui-input-inline" style="width: 100px;">
                <input type="text" name=minshijijiesuan placeholder="min实际工资" id="minshijijiesuanToReload"  autocomplete="off" class="layui-input">
              </div>
              ---
              <div class="layui-input-inline" style="width: 100px;">
                <input type="text" name="maxshijijiesuan" placeholder="max实际工资" id="maxshijijiesuanToReload"  autocomplete="off" class="layui-input">
              </div>
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
  }).use(['index', 'table','jquery','excel'], function(){
    var table = layui.table,
    $ = layui.jquery,
    excel = layui.excel;


      $(function () {
          $.get("${pageContext.request.contextPath}/Department/FindAllDepartmentInfo",function (data,status) {
              var departstr = "<option value=''>==部门==</option>";
              $.each(data, function(index,item){
                      departstr += "<option value=' "+ item.departid +" '>" + item.departname + "</option>";

              })
              $("#depaetmentinfo").append(departstr);
              //这里需要加上重新渲染，否则数据加载不到select中  坑的yp
              var monthoptions = "<option value=''>==月份==</option>";
              monthoptions += '<option value="1">1</option>\n' +
                  '                <option value="2">2</option><option value="3">3</option><option value="4">4</option>\n' +
                  '                <option value="5">5</option><option value="6">6</option><option value="7">7</option>\n' +
                  '                <option value="8">8</option><option value="9">9</option><option value="10">10</option>\n' +
                  '                <option value="11">11</option><option value="12">12</option>';
              $('#jiesuanyuefenToReload').append(monthoptions);
              layui.form.render("select");
          })
      });



      //方法级渲染
    table.render({
      elem: '#tableToReload'
      ,url: '${pageContext.request.contextPath}/SalaryInfo/FindAllSalaryInfo'
      ,toobar:true
      ,title: '员工薪资信息表'
      ,totalRow: true
      ,cols: [[
         {checkbox: true, LAY_CHECKED: false,fixed: true}
        ,{field:'id', title: '序号', width:80, sort: true,}
        ,{field:'jobid', title: '工号', width:80, sort: true,}
        ,{field:'name', title: '姓名', width:80}
        ,{field:'departid', title: '部门编号', width:160, sort: true,templet: function (d) {
            return d.departid;
                }}
        ,{field:'departname', title: '部门名称', width:160,templet: function (d) {
            return d.department.departname;
                }}
        ,{field:'jiesuanyuefen', title: '结算月份', width:160, sort: true}
        ,{field:'jibengongzi', title: '基本工资', width:160, sort: true}
        ,{field:'jiangli',title: '奖金', width:80, sort: true}
        ,{field:'chengfa', title: '惩金', width:80, sort: true}
        ,{field:'jiabangongzi', title: '加班工资', width:160, sort: true}
        ,{field:'kuanggonggongzi',title: '旷工', width:80, sort: true}
        ,{field:'qingjiagongzi',title: '请假', width:80, sort: true}
        ,{field:'chuchaigongzi',title: '出差', width:80, sort: true}
        ,{field:'shijijiesuan',title: '实际结算', width:160, sort: true}
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
  //--------------------------excel导出----------------------------------
 
    $("#exportExcel").click(function ()
            {
                var checkStatus = table.checkStatus('tableToReload');
                var count = checkStatus.data.length;//选中的行数
                if (count > 0)
                {
                        var data = checkStatus.data; //获取选中行的数据
                        var batchjobid = '';
                        for (var i = 0; i < data.length; i++)
                        {
                        	batchjobid += data[i].jobid + ",";
                        }
                        $.ajax({
                            url: '${pageContext.request.contextPath}/SalaryInfo/exportSalaryExcel',
                            type: "post",
                            data: { 'batchjobid': batchjobid },
                            dataType:"json",
                            success: function (res){
                            	  console.log(res.data);
                            	  // 1. 数组头部新增表头
                                if(res.data === "unauthorized"){//无权操作，提示无权信息
                                    console.log(that);
                                    layer.tips('无权操作!', that, {
                                        tips: 1,
                                        time: 1500 //根据需求设定时间
                                    });
                                }else {
                                    res.data.unshift({jobid:'工号',name: '用户名',departid: '部门编号',departname: '部门名称',jiesuanyuefen: '结算月份',
                                        jibengongzi: '基本工资',jiangli: '奖金',chengfa: '惩金',jiabangongzi: '加班工资',kuanggonggongzi: '旷工工资',
                                        qingjiagongzi: '请假工资',chuchaigongzi: '出差工资',shijijiesuan: '实际工资'});
                                    // 2. 如果需要调整顺序，请执行梳理函数
//departname jiesuanyuefen jibengongzi jiangli chengfa jiabangongzi kuanggonggongzi qingjiagongzi chuchaigongzi shijijiesuan
                                    var data = excel.filterExportData(res.data, [
                                        'jobid',
                                        'name',
                                        'departid',
                                        'departname',
                                        'jiesuanyuefen',
                                        'jibengongzi',
                                        'jiangli',
                                        'chengfa',
                                        'jiabangongzi',
                                        'kuanggonggongzi',
                                        'qingjiagongzi',
                                        'chuchaigongzi',
                                        'shijijiesuan',
                                    ]);
                                    // 3. 执行导出函数，系统会弹出弹框
                                    excel.exportExcel({
                                        sheet1: data
                                    }, '员工薪资信息.xlsx', 'xlsx');
                                }
                            }
                        })
                   
                }
                else
                    parent.layer.msg("请至少选择一条数据", { icon: 5, shade: 0.4, time: 1000 });
            });
    //-----------------------------导出Excel end--------------------------------------
    
    
    
    
    //--------------------------批量删除----------------------------------
    $("#batchdelete").click(function ()
            {
                var that = this;
                var checkStatus = table.checkStatus('tableToReload');
                var count = checkStatus.data.length;//选中的行数
                if (count > 0)
                {
                    parent.layer.confirm('确定删除？', { icon: 3 }, function (index)
                    {
                        var data = checkStatus.data; //获取选中行的数据
                        var batchid = '';
                        for (var i = 0; i < data.length; i++)
                        {
                            batchid += data[i].id + ",";
                        }
                        $.ajax({
                            url: '${pageContext.request.contextPath}/SalaryInfo/BatchDeleteSalaryInfo',
                            type: "post",
                            data: { batchid: batchid },
                            dataType:"json",
                            success: function (result){
                                if (result.data == "success"){
                                     parent.layer.msg('删除成功', { icon: 1, shade: 0.4, time: 1000 })
                                     $("#search").click();//重载TABLE
                                }else if(result.data === "unauthorized"){
                                    layer.tips('无权操作!', that, {
                                        tips: 1,
                                        time: 1500 //根据需求设定时间
                                    });
                                }else if(result.data === "fail"){
                                     parent.layer.msg("删除失败", { icon: 5, shade: [0.4], time: 1000 });
                                }else {
                                    layer.msg("未知错误!");
                                }
                                parent.layer.close(index);
                            }
                        })
                    });
                }
                else
                    parent.layer.msg("请至少选择一条数据", { icon: 5, shade: 0.4, time: 1000 });
            });
    //-----------------------------批量删除end--------------------------------------
    
    
     //--------------------------批量下载----------------------------------
    $("#addSalary").click(function ()
            {
    	      layer.open({
	          type: 2,
	          title: '员工薪资信息添加',
	          shade: false,
	          maxmin: true,
	          area: ['90%', '90%'],
	          content: '${pageContext.request.contextPath}/SalaryInfo/ToInsertSalaryInfo'
	        });
    	
            });
    //-----------------------------批量下载end--------------------------------------
    
    
    var $ = layui.$, active = {
      reload: function(){
        var nameReload = $('#nameToReload');
        var departidReload = $('#depaetmentinfo');
        var jiesuanyuefenReload = $('#jiesuanyuefenToReload');
        var minjibengongziReload = $('#minjibengongziToReload');
        var maxjibengongziReload = $('#maxjibengongziToReload');
        var jiangliReload = $('#jiangliToReload');
        var chengfaReload = $('#chengfaToReload');
        var jiabangongziReload = $('#jiabangongziToReload');
        var kuanggonggongziReload = $('#kuanggonggongziToReload');
        var qingjiagongziReload = $('#qingjiagongziToReload');
        var chuchaigongziReload = $('#chuchaigongziToReload');
        var minshijijiesuanReload = $('#minshijijiesuanToReload');
        var maxshijijiesuanReload = $('#maxshijijiesuanToReload');
        
        var count = 13;
        //由于后台sql自会进行模板判定，可以不用数组收纳
        /* alert(($('#nameToReload').val())=='');   经过测试，没有输入查询关键字时，该判断为true，所以还是采用反向计算 */   
        if(nameReload.val() == null||nameReload.val() == ''){
        	count--;
        }
        if(departidReload.val() ==null||departidReload.val()==''){
        	count--;
        }
        if(jiesuanyuefenReload.val() ==null||jiesuanyuefenReload.val()==''){
        	count--;
        }
        if(minjibengongziReload.val() ==null||minjibengongziReload.val()==''){
        	count--;
        }
        if(maxjibengongziReload.val() ==null||maxjibengongziReload.val()==''){
        	count--;
        }
        if(jiangliReload.val() ==null||jiangliReload.val()==''){
        	count--;
        }
        if(chengfaReload.val() ==null||chengfaReload.val()==''){
        	count--;
        }
        if(jiabangongziReload.val() ==null||jiabangongziReload.val()==''){    
        	count--;
        }
        if(kuanggonggongziReload.val() ==null||kuanggonggongziReload.val()==''){
        	count--;
        }
        if(qingjiagongziReload.val() ==null||qingjiagongziReload.val()==''){
        	count--;
        }
        if(chuchaigongziReload.val() ==null||chuchaigongziReload.val()==''){
        	count--;
        }
        if(minshijijiesuanReload.val() ==null||minshijijiesuanReload.val()==''){  
        	count--;
        }
        if(maxshijijiesuanReload.val() ==null||maxshijijiesuanReload.val()==''){
        	count--;
        }
        
/*         alert("查询关键字数为："+count); */ 
       //需要加上查询条件检查，为空则不进行查询，并给出提示
        if(count<=0){
        	layer.msg("请输入查询关键字!",{icon: 5});
        	return false;
        }else{
        	   //执行重载
            table.reload('tableToReload', {
            	//设置为post请求，否则会有中文乱码 ？此处是不是已经做过了编码处理，不设置为post时，会导致控制器在接收到中文信息时以乱码显示（spring）自带的编码处理器已经加上了
              method:'POST',
              page: {
                curr: 1 //重新从第 1 页开始
              }
              ,where: {
            		  name:nameReload.val(),
            		  departid:departidReload.val(),
            		  jiesuanyuefen:jiesuanyuefenReload.val(),   
            		  minjibengongzi:minjibengongziReload.val(),
            		  maxjibengongzi:maxjibengongziReload.val(),
            		  jiangli:jiangliReload.val(),
      				  chengfa:chengfaReload.val(),
      				  jiabangongzi:jiabangongziReload.val(),
      				  kuanggonggongzi:kuanggonggongziReload.val(),
      				  qingjiagongzi:qingjiagongziReload.val(),
      				  chuchaigongzi:chuchaigongziReload.val(),
      				  minshijijiesuan:minshijijiesuanReload.val(),
      				  maxshijijiesuan:maxshijijiesuanReload.val()
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
                 url:"${pageContext.request.contextPath}/SalaryInfo/DeleteSalaryInfo",
                 type:"post",
                 data:{id:data.id},
                 dataType:"json",
                 success:function (result) {
                     if(result.data === "success"){
                         layer.msg('删除成功!', {icon: 2},function () {
                             var index = parent.layer.getFrameIndex(window.name);
                             parent.layer.close(index);
                             parent.layui.table.reload('tableToReload');
                         });

                     }else if(result.data === "fail"){
                         layer.msg('删除失败!', {icon: 2});
                     }else if(result.data === "unauthorized"){
                         layer.tips('无权操作!', that, {
                             tips: 1,
                             time: 1500, //根据需求设定时间
                             end: function(){
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
   	 }else if(obj.event == 'edit'){
   		layer.open({
	          type: 2,
	          title: '员工薪资信息编辑',
	          shade: false,
	          maxmin: true,
	          area: ['90%', '90%'],
	          content: '${pageContext.request.contextPath}/SalaryInfo/ToUpdateSalaryInfo?jobid='+data.jobid
	        });
   		 return false;
   	 }
    })
  
  });
  </script>
	<script type="text/html" id="btndemo">
   <a class='layui-btn  layui-btn-xs layui-btn' lay-event='edit' data-type='download'>编辑</a>
   <a class='layui-btn  layui-btn-xs layui-btn-danger' lay-event='delete'>删除</a>
  </script>
</body>
</html>