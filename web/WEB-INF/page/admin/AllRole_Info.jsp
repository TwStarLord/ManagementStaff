<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
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


  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">
          <div class="layui-btn-container">
          <button class="layui-btn" lay-event="batchdownload" id="batchdownload"><i class="layui-icon" >&#xe600;</i>添加</button>
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
  }).use(['index', 'table','jquery','form'], function(){
    var table = layui.table,
    $ = layui.jquery,
    form = layui.form;



    //方法级渲染
    table.render({
      elem: '#tableToReload'
      ,url: '${pageContext.request.contextPath}/Role/FindAllRole'
      ,title: '角色数据表'
      ,totalRow: true
      ,cols: [[
         {checkbox: true, LAY_CHECKED: false,fixed: true}
        ,{field:'id', title: '角色编号', width:80,}
        ,{field:'name', title: '角色名称', width:160}
        ,{field:'available', title: '是否启用', width:160,align:"center",templet:function(d){
        	return d.available==1 ? '<div class="layui-form"><input type="checkbox" name="available" value="'+ d.id +'" lay-skin="switch" lay-text="ON|OFF"  lay-filter="changeAvailable" checked></div>' : '<div class="layui-form"><input type="checkbox" name="available"  value="'+ d.id +'" lay-skin="switch" lay-text="ON|OFF"  lay-filter="changeAvailable"></div>';
        	//这里稍作修改，使用开关的形式
        }}
        ,{field:'right', width:260, title: '操作',align:'center',toolbar:'#btndemo',fixed: 'right'}
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

    // 监听开关事件
    form.on("switch(changeAvailable)",function (data) {
      // console.log(data.elem["value"]);
      var role_id = data.elem["value"]; //通过传过来的值确定修改的role_id的属性是否为启用、停用
      var emvalue = (data.othis).find("em");
      if (emvalue.text() == "ON"){
            $.post("${pageContext.request.contextPath}/Role/ChangeRoleAvailable",{role_id:role_id,available:'1'},function (data) {
              if (data == "success"){
                layer.msg('已启用！', { icon: 1, shade: 0.4, time: 1000 });
              }else if (data == "fail"){
                layer.msg('启用失败！', { icon: 1, shade: 0.4, time: 1000 });
              }else{
                layer.msg('未知错误！', { icon: 1, shade: 0.4, time: 1000 });
              }
            })
      }else if (emvalue.text() == "OFF"){
        $.post("${pageContext.request.contextPath}/Role/ChangeRoleAvailable",{role_id:role_id,available:'0'},function (data) {
          if (data == "success"){
            layer.msg('已停用！', { icon: 1, shade: 0.4, time: 1000 });
          }else if (data == "fail"){
            layer.msg('停用失败！', { icon: 1, shade: 0.4, time: 1000 });
          }else{
            layer.msg('未知错误！', { icon: 1, shade: 0.4, time: 1000 });
          }
        })
      }
    });

    //表格重载start
    var $ = layui.$, active = {
      reload: function(){
        var typeReload = $('#typeToReload');
        var count = 1;
        //由于后台sql自会进行模板判定，可以不用数组收纳
        /* alert(($('#nameToReload').val())=='');   经过测试，没有输入查询关键字时，该判断为true，所以还是采用反向计算 */
        if(typeReload.val() == null||typeReload.val() == ''){
          count--;
        }
        /*         alert("查询关键字数为："+count); */
        //需要加上查询条件检查，为空则不进行查询，并给出提示
        if(count<=0){
          layer.msg("请输入查询关键字!",{icon: 5})
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
              type:typeReload.val()
            }
          });
        }

      }
    };

    $('.test-table-reload-btn .layui-btn').on('click', function(){
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });

    //表格重载end

    // 监听表格内点击事件
    table.on('tool(demo)',function(obj){
      var id = obj.data.id;
      var layevent = obj.event;
      var checkValue = obj["checked"];
      if(layevent == 'findstaff'){  //此事件改变该角色是否启用
          // console.log(this);
          // console.log(checkValue +","+ typeof checkValue);
        layer.open({
          type: 2
          ,scrollbar:true
          ,maxmin: true
          ,offset: "rb"
          ,content: '${pageContext.request.contextPath}/Role/ToFindStaffOfRole?id='+id
          ,success: function(layero, index){
            var body = layer.getChildFrame('body', index);
            var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            console.log(body.html()) //得到iframe页的body内容
            body.find('input#roleid').val(id);  //只能用这种方法，使用隐藏的元素来传递值
          }
          ,area: ['100%','100%']
          ,shade: true //显示遮罩
        });
      }else if (layevent == 'look'){
        layer.open({
          type: 2
          ,scrollbar:true
          ,maxmin: true
          ,offset: "rb"
          ,content: '${pageContext.request.contextPath}/Permission/ToGetPermissionWithTree?id='+id
          ,success: function(layero, index){
            var body = layer.getChildFrame('body', index);
            var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            console.log(body.html()) //得到iframe页的body内容
            body.find('input#id').val(id);  //只能用这种方法，使用隐藏的元素来传递值
          }
          ,btn: '关闭全部'
          ,btnAlign: 'c' //按钮居中
          ,area: ['30%','100%']
          ,shade: false //不显示遮罩
          ,yes: function(){
            layer.closeAll();
          }
        });
      }
    })
    
  });    
    </script>
  <script type="text/html" id="btndemo">
    <button class="layui-btn layui-btn-normal layui-btn-xs" lay-event="look" id="permissionlookbtn">权限信息</button>
    <button class="layui-btn layui-btn-primary layui-btn-xs" lay-event="findstaff" id="stafflookbtn">员工信息</button>
  </script>
</body>
</html>