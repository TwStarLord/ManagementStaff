<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>员工信息表</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css"
	media="all">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css"
	media="all">
</head>
<body>

	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-header">
						<div class="layui-btn-container">
							<button class="layui-btn" lay-event="batchdownload" id="addStaff"><i class="layui-icon" >&#xe600;</i>添加</button>
							<button class="layui-btn layui-btn-danger" lay-event="batchdelete" id="batchdelete"><i class="layui-icon">&#xe640;</i>批量删除</button>
							<%--粗粒度权限验证，是否拥有权限验证代码，否则不进行显示，但是url拦截仍需在后台配置--%>
							<shiro:hasPermission name="staff:excelexport">
								<button class="layui-btn layui-btn-primary" lay-event="exportExcel" id="exportExcel"><i class="layui-icon">&#xe640;</i>导出Excel</button>
							</shiro:hasPermission>
						</div>
					</div>
					<div class="layui-card-body">

						<!-- 由输入框获取关键字 -->
						<div class="layui-inline">
							<input class="layui-input" name="name" id="nameToReload"  autocomplete="off" placeholder="姓名">
						</div>
						<div class="layui-inline">
							<input class="layui-input" name="account" id="accoutToReload"  autocomplete="off" placeholder="账号">
						</div>
						<div class="layui-inline">
							<select class="layui-input" name="departid" lay-filter="" id="depaetmentinfo"></select>
						</div>
						<div class="layui-inline">
							<select class="layui-input" name="eduback" id="edubackToReload"  autocomplete="off" placeholder="学历"></select>
						</div>
						<div class="layui-inline">
							<select class="layui-input" name="status" id="statusToReload"  autocomplete="off" placeholder="状态"></select>
						</div>
						<div class="layui-inline">
							<input class="layui-input" name="timeforjob" id="timeforjobToReload"  autocomplete="off" placeholder="入职日期">
						</div>
						<button class="layui-btn" id="reloadbtn" data-type="reload">搜索</button>
					</div>
						<table class="layui-hide" id="staffinfo" lay-filter="demo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
	<script>
  layui.config({
    base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'table','jquery','excel','laydate'], function(){
    var table = layui.table,
    $ = layui.jquery,
	excel = layui.excel,
	laydate = layui.laydate;

	  laydate.render({
		  elem: '#timeforjobToReload'
	  });

	  $(function () {
		  $.get("${pageContext.request.contextPath}/Department/FindAllDepartmentInfo",function (data,status) {
			  var departstr = "<option value=''>==部门==</option>";
			  var statusstr = "<option value=''>==状态==</option>";
			  var edubackstr = "<option value=''>==学历==</option>";
			  $.each(data, function(index,item){
				  departstr += "<option value=' "+ item.departid +" '>" + item.departname + "</option>";

			  })
			  $("#depaetmentinfo").append(departstr);
			  statusstr += '<option value="在职">在职</option><option value="待激活">待激活</option><option value="请假">请假</option><option value="出差">出差</option>';
			  edubackstr += '<option value="本科">本科</option><option value="硕士">硕士</option><option value="博士">博士</option><option value="其他">其他</option>';
			  //这里需要加上重新渲染，否则数据加载不到select中  坑的yp
			  $("#statusToReload").append(statusstr);
			  $("#edubackToReload").append(edubackstr);
			  layui.form.render("select");
		  })
	  });


    table.render({
              elem: '#staffinfo'
              ,url:'${pageContext.request.contextPath}/StaffInfo/findAllStaffInfo1' 
              ,toolbar: true
              ,title: '员工信息表'
              ,totalRow: true
              ,cols: [[
			{checkbox: true, LAY_CHECKED: false,fixed: true}
  ,{field:'jobid', width:80, title: '工号', sort: true}
    ,{field:'name', width:80, title: '姓名'}
    /* ,{field:'image', width:80, title: '头像',align:'center',templet:function(d){//直接返回一个
    	return "<img  src='resources/images/b.png' 'style='width:108px,height:100px;' />";
    }} */
    ,{field:'image', width:80, title: '头像',templet:"#imgtmp"}
    ,{field:'account', width:80, title: '账号'}
    ,{field:'departid', minWidth:80, title: '部门编号',templet: function(d){
		/*var keys= Object.keys(d);
		console.log(keys); // 输出 keys*/
		// var $d = $(d)[0].department;
		// console.log($d);
		// console.log(typeof $d);
		// var keys= Object.keys(info);
		// console.log(keys); // 输出 keys
		//console.log(typeof departid); //number
		// console.log("测试部门数据："+ JSON.stringify(d));
    	/*console.log(d.department);
    	console.log(d.department.departid);*/
    	return d.departid;
				}}
    ,{field:'departid', title: '部门名称', minWidth: 80,templet: function(d){
        /*var keys= Object.keys(d);
        console.log(keys); // 输出 keys*/
    	return d.department.departname;
				}}
    ,{field:'sex', width:80, title: '性别'}
    ,{field:'birthday', width:80, title: '生日'}
    ,{field:'eduback', width:80, title: '学历'}
    ,{field:'mobile', width:135, title: '手机'}
    ,{field:'mail', width:135, title: '邮箱'}
    ,{field:'address', width:135, title: '地址'}
    ,{field:'status', width:135, title: '状态',align:'center',templet:function (d) {
		if (d.status == "在职"){
			return '<div><button class="layui-btn layui-btn-sm layui-btn" lay-event="changestatus">在职</button></div>';
		}else if(d.status == "请假"){
			return '<div><button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="changestatus">请假</button></div>';
		}else if(d.status == "出差"){
			return '<div><button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="changestatus">出差</button></div>';
		}else if(d.status == "离职"){
			return '<div><button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="changestatus">离职</button></div>';
		}else if(d.status == "待激活"){
			return '<div><button class="layui-btn layui-btn-sm layui-btn-warm" lay-event="changestatus">待激活</button></div>';
		}

    }}
    ,{field:'timeforjob', width:135, title: '入职日期'}
    ,{field:'descripetion', width:135, title: '简介'}
	,{field:'right', width:130, title: '操作',toolbar:'#btndemo',fixed: 'right'}
                            ]]
    ,page: true
	,height: 'full-100'
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

    // ========================表格重载start===============================================
	  var  active = {
		  reload: function(){
			  var nameReload = $('#nameToReload');
			  var accoutToReload = $('#accoutToReload');
			  var depaetmentinfo = $('#depaetmentinfo');
			  var edubackToReload = $('#edubackToReload');
			  var statusToReload = $('#statusToReload');
			  var timeforjobToReload = $('#timeforjobToReload');

			  var count = 6;
			  //由于后台sql自会进行模板判定，可以不用数组收纳
			  /* alert(($('#nameToReload').val())=='');   经过测试，没有输入查询关键字时，该判断为true，所以还是采用反向计算 */
			  if(nameReload.val() == null||nameReload.val() == ''){
				  count--;
			  }
			  if(accoutToReload.val() ==null||accoutToReload.val()==''){
				  count--;
			  }
			  if(depaetmentinfo.val() ==null||depaetmentinfo.val()==''){
				  count--;
			  }
			  if(edubackToReload.val() ==null||edubackToReload.val()==''){
				  count--;
			  }
			  if(statusToReload.val() ==null||statusToReload.val()==''){
				  count--;
			  }
			  if(timeforjobToReload.val() ==null||timeforjobToReload.val()==''){
				  count--;
			  }

			  /*         alert("查询关键字数为："+count); */
			  //需要加上查询条件检查，为空则不进行查询，并给出提示
			  if(count<=0){
				  layer.msg("请输入查询关键字!",{icon: 5});
				  return false;
			  }else{
				  //执行重载
				  table.reload('staffinfo', {
					  //设置为post请求，否则会有中文乱码 ？此处是不是已经做过了编码处理，不设置为post时，会导致控制器在接收到中文信息时以乱码显示（spring）自带的编码处理器已经加上了
					  method:'POST',
					  page: {
						  curr: 1 //重新从第 1 页开始
					  }
					  ,where: {
						  name:nameReload.val(),
						  account:accoutToReload.val(),
						  departid:depaetmentinfo.val(),
						  eduback:edubackToReload.val(),
						  status:statusToReload.val(),
						  timeforjob:timeforjobToReload.val()
					  }
				  });
			  }

		  }
	  };

	  $('#reloadbtn').on('click', function(){
		  var type = $(this).data('type');
		  active[type] ? active[type].call(this) : '';
	  });
    // ======================表格重载end===================================================


	  $("#addStaff").click(function ()
	  {
		  layer.open({
			  type: 2,
			  title: '添加员工信息',
			  shade: false,
			  maxmin: true,
			  area: ['90%', '90%'],
			  content: '${pageContext.request.contextPath}/StaffInfo/ToInsertStaffInfo'
		  });

	  });

	  //--------------------------excel导出----------------------------------

	  $("#exportExcel").click(function ()
	  {
		  var checkStatus = table.checkStatus('staffinfo');
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
				  url: '${pageContext.request.contextPath}/StaffInfo/exportStaffExcel',
				  type: "post",
				  data: { 'batchjobid': batchjobid },
				  dataType:"json",
				  success: function (res){
					  console.log(res.data);//,departname: '部门名称'
					  // 1. 数组头部新增表头
					  res.data.unshift({jobid:'工号',name: '姓名',account: '账号',departid: '部门名称',
						  sex: '性别',birthday: '出生日期',eduback: '学历',mobile: '手机号',mail: '邮箱',
						  address: '地址',status: '状态',timeforjob: '入职日期',descripetion: '简介'});
					  // 2. 如果需要调整顺序，请执行梳理函数
//departname jiesuanyuefen jibengongzi jiangli chengfa jiabangongzi kuanggonggongzi qingjiagongzi chuchaigongzi shijijiesuan
					  var data = excel.filterExportData(res.data, [
						  'jobid',
						  'name',
						  'account',
						  'departid',
						  'departname',
						  'sex',
						  'birthday',
						  'eduback',
						  'mobile',
						  'mail',
						  'address',
						  'status',
						  'timeforjob',
						  'descripetion',
					  ]);
					  // 3. 执行导出函数，系统会弹出弹框
					  excel.exportExcel({
						  sheet1: data
					  }, '员工信息.xlsx', 'xlsx');

					  /*                                 parent.layer.close(index);
                       */                            }
			  })

		  }
		  else
			  parent.layer.msg("请至少选择一条数据", { icon: 5, shade: 0.4, time: 1000 });
	  });
	  //-----------------------------导出Excel end--------------------------------------


	  //--------------------------批量删除start----------------------------------
	  $("#batchdelete").click(function ()
	  {
	  	var that = this;
		  var checkStatus = table.checkStatus('staffinfo');
		  var count = checkStatus.data.length;//选中的行数
		  if (count > 0)
		  {
			  parent.layer.confirm('确定删除？', { icon: 3 }, function (index)
			  {
				  var data = checkStatus.data; //获取选中行的数据
				  var batchjobid = '';
				  for (var i = 0; i < data.length; i++)
				  {
					  batchjobid += data[i].jobid + ",";
				  }
				  $.ajax({
					  url: '${pageContext.request.contextPath}/StaffInfo/BatchDeleteStaffInfo',
					  type: "post",
					  data: { 'batchjobid': batchjobid },
					  dataType:"json",
					  success: function (result){
						  if (result.data === "success"){
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
  
         table.on('tool(demo)',function(obj){
        	 var data = obj.data;  //这里的obj到底是从哪里取的数据？
        	 // console.log(data);
			 var that = this;
        	 if(obj.event =='delete'){
        		 layer.confirm('确认要删除该员工信息吗?',function(index){
        			$.ajax({
						url:"${pageContext.request.contextPath}/StaffInfo/DeleteStaffInfo",
						type:"post",
						data:{jobid:data.jobid},
						dataType:"json",
						success:function (result) {
							if(result.data === "success"){
								layer.msg('删除成功!', {icon: 2},function () {
									var index = parent.layer.getFrameIndex(window.name);
									parent.layer.close(index);
									parent.layui.table.reload('staffinfo');
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
        		          title: '员工信息编辑',
        		          shade: false,
        		          maxmin: true,
        		          area: ['90%', '90%'],
        		          content: '${pageContext.request.contextPath}/StaffInfo/ToUpdateStaff?jobid='+data.jobid
        		        });
        	 }else if (obj.event == 'changestatus'){
				 var statusflag = $(this).text();
				 if(statusflag == '在职'){
					layer.confirm('确定改变员工状态为离职吗？', {
						btn: ['确定','取消']
					}, function(){
						$.ajax({
							url:"${pageContext.request.contextPath}/StaffInfo/ChangeStaffStatus",
							type:"post",
							data:{jobid:data.jobid,status:"离职"}
						})
						.done(function (data) {
							if(data == "success"){
								layer.msg('修改成功', {icon: 1});
								// console.log($(obj.tr[1]));
								// var statusDOM = $(obj.tr.prevObject[0]).find("tr.layui-table-click").find("td[data-field=\"status\"]");
								// var choose1 = choose.find("tr.layui-table-click").find("td[data-field=\"status\"]");
								// console.log(choose.find("tr.layui-table-click").find("td[data-field=\"status\"]"));
								// console.log(statusDOM);
								// $(statusDOM[0]).attr("data-content","离职");
								var statusDOM = $(obj.tr.prevObject[0]).find("tr.layui-table-click").find("td[data-field=\"status\"]");
								$(statusDOM[0]).find("div").html('<button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="changestatus">离职</button>');
								$(statusDOM[0]).attr("data-content","离职");
								// obj.data.status = "离职";
							}else if( data == "fail"){
								layer.msg('修改失败', {icon: 2});
							}else {
								layer.msg('未知错误', {icon: 2});
							}
								})
						.fail(function (error) {
							layer.msg('请求错误,请重新尝试!', {icon: 2});
								})
					});//这里默认第二个按钮回调为关闭弹窗
				}else if(statusflag == '离职'){
					layer.confirm('确定改变员工状态为在职吗？', {
						btn: ['确定','取消']
					}, function(){
						$.ajax({
							url:"${pageContext.request.contextPath}/StaffInfo/ChangeStaffStatus",
							type:"post",
							data:{jobid:data.jobid,status:"在职"}
						})
								.done(function (data) {
									if(data == "success"){
										layer.msg('修改成功', {icon: 1});
										var statusDOM = $(obj.tr.prevObject[0]).find("tr.layui-table-click").find("td[data-field=\"status\"]");
										$(statusDOM[0]).find("div").html('<button class="layui-btn layui-btn-sm layui-btn" lay-event="changestatus">在职</button>');
										$(statusDOM[0]).attr("data-content","在职");
										obj.data.status = "在职";
									}else if( data == "fail"){
										layer.msg('修改失败', {icon: 2});
									}else {
										layer.msg('未知错误', {icon: 2});
									}
								})
								.fail(function (error) {
									layer.msg('请求错误,请重新尝试!', {icon: 2});
								})
					});//这里默认第二个按钮回调为关闭弹窗
				}else if(statusflag == '待激活'){
					layer.msg("该员工账号还未激活!");
				}
			 }
         });
  });
  </script>
	<script type="text/html" id="btndemo">
   <button class='layui-btn  layui-btn-xs layui-btn' lay-event='edit' data-type='editbtn'>编辑</button>
   <button class='layui-btn  layui-btn-xs layui-btn-danger' lay-event='delete'>删除</button>
  </script>
  <!-- 此处记录错误，模板引擎是根据table请求路径的相对位置，即该模板引擎请求的url为  项目名/Staffinfo/src 所以在此处要改为绝对路径 -->
  <script type="text/html" id="imgtmp">
<img  src="${pageContext.request.contextPath}/{{d.image}}"  style="width:50px,height:50px;" />
</script>
</body>
</html>