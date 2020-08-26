<%@ page import="com.tw.pojo.Kaoqin" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>补卡申请</title>
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
    <div class="layui-card">
        <div class="layui-card-header">补卡申请</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" id="kaoqinChangeStatusForm" lay-filter="component-form-group">

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>序号</label>
                    <div class="layui-input-block">
                        <input type="text" name="id"  lay-verify="required" value="${changeKaoqinStatusInfo.id}"
                               placeholder="序号" class="layui-input" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>工号</label>
                    <div class="layui-input-block">
                        <input type="text" name="jobid"  lay-verify="required" value="${changeKaoqinStatusInfo.jobid}"
                               placeholder="工号" class="layui-input" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="name"  lay-verify="required" value="${changeKaoqinStatusInfo.name}"
                               placeholder="姓名" class="layui-input" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">部门名称</label>
                    <div class="layui-input-block">
                        <select name="departid" lay-filter="" id="depaetmentinfo" readonly="readonly">
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>月份</label>
                    <div class="layui-input-block">
                        <input type="text" name="month" lay-verify="required" value="${changeKaoqinStatusInfo.month}"
                               placeholder="月份" class="layui-input" disabled>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">打卡时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="recordtime" lay-verify="required" value="${changeKaoqinStatusInfo.recordtime}"
                               placeholder="打卡时间" class="layui-input" disabled>
                    </div>
                </div>


                <div class="layui-btn-container" style='text-align:center;'>
                    <button type="button" class="layui-btn layui-btn-normal layui-btn-radius" lay-submit="" lay-filter="changestatus-submit">提交</button>
                    <input type="reset" class="layui-btn layui-btn-warm layui-btn-radius">重置</button>
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
    }).use(['index', 'form'], function(){
        var $ = layui.$
            ,admin = layui.admin
            ,element = layui.element
            ,layer = layui.layer
            ,form = layui.form;

        $(function () {
            var departid = <%=((Kaoqin)request.getAttribute("changeKaoqinStatusInfo")).getDepartid() %>;
            $.get("${pageContext.request.contextPath}/Department/FindAllDepartmentInfo",function (data,status) {
                var departstr = "";
                $.each(data, function(index,item){
                    if (departid == item.departid){
                        departstr += "<option value=' "+ item.departid +" '  selected>" + item.departname + "</option>";
                    }else{
                        departstr += "<option value=' "+ item.departid +" ' >" + item.departname + "</option>";
                    }

                })
                $("#depaetmentinfo").append(departstr);
                //这里需要加上重新渲染，否则数据加载不到select中  坑的yp
                layui.form.render("select");
            })
        })

        form.on('submit(changestatus-submit)',function(){
            $.post("${pageContext.request.contextPath}/NewsInfo/InsertNewsOfChangeKaoqinStatus",$("#kaoqinChangeStatusForm").serialize(),function(data){
                if(data=="success"){
                    layer.msg("提交成功！",{icon:1,anim:4,time:2000},function(){
                        //关闭窗口
                        parent.layer.closeAll();
                    });
                }else{
                    layer.msg("提交！请重试！",{icon:5,anim:6,time:2000});
                }
            });
        });

        $(function () {
            $.get("${pageContext.request.contextPath}/Department/FindAllDepartmentInfo",function (data,status) {
                var departstr = "";
                $.each(data, function(index,item){
                    departstr += "<option value=' "+ item.departid +" ' >" + item.departname + "</option>";

                })
                $("#depaetmentinfo").append(departstr);
                //这里需要加上重新渲染，否则数据加载不到select中  坑的yp
                layui.form.render("select");
            })
        })

    });
</script>
</body>
</html>
