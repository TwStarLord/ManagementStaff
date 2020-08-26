<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>layui.dtree帮助手册</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/dtree/dtree.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/dtree/font/dtreefont.css">
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/dtree/font/dtreefont.css">--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/dtree/dtree.css">--%>
<%--    我真是醉了，为什么加上后面这个在去掉就可以找得到呢？--%>
</head>

<body>
<input type="hidden" name="id" id="id" value="">

<div class="container">
    <div class="layui-row layui-col-space10">
        <div class="layui-fluid">
            <fieldset class="layui-elem-field layui-field-title">
                <legend>权限信息</legend>
                <div class="layui-field-box">
                    <div class="layui-row layui-col-space10" style="margin-top: 10px;">
                        <div class="layui-col-xs12">
                            <div id="toolbarDiv" style="overflow: auto">
                                <ul id="demoTree" class="dtree" data-id="0"></ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-btn-container" align="center">
                    <button class="layui-btn layui-btn-normal layui-btn-radius" id="permissionChange">提交</button>
                </div>
            </fieldset>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    layui.config({
        base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'table','jquery','form','layer','element','util','dtree'], function(){
        var element = layui.element, layer = layui.layer, table = layui.table, util = layui.util, dtree = layui.dtree, form = layui.form, $ = layui.$;
        var id = $("#id").val(); //这里是角色信息界面传过来的角色id值，根据角色查询对应权限。
        // console.log("在权限树："+$("#id").val());

        /*var data = {
            "status":{"code":200,"message":"操作成功"},
            "data": [{
            "id":"001",
            "title": "湖南省",
            "parentId": "0",
            "checkArr": [{"type": "0", "checked": "0"}],
            "children":[]
        },{
            "id":"002",
            "title": "湖北省",
            "parentId": "0",
            "checkArr": [{"type": "0", "checked": "0"}],
            "children":[]
        },{
            "id":"003",
            "title": "广东省",
            "parentId": "0",
            "checkArr": [{"type": "0", "checked": "0"}],
            "children":[]
        },{
            "id":"004",
            "title": "浙江省",
            "parentId": "0",
            "checkArr": [{"type": "0", "checked": "0"}],
            "children":[]
        },{
            "id":"005",
            "title": "福建省",
            "parentId": "0",
            "checkArr": [{"type": "0", "checked": "0"}],
            "children":[]
        }]
        };*/

        dtree.render({
            elem: "#demoTree",
            url: "${pageContext.request.contextPath}/Permission/GetPermissionTree?id="+id,
            checkbar: true,
            checkbarData: "choose" // 记录选中（默认）， "change"：记录变更， "all"：记录全部， "halfChoose"："记录选中和半选（V2.5.0新增）"
        });
        $("#permissionChange").click(function(){
            var params = dtree.getCheckbarNodesParam("demoTree");
            layer.msg(JSON.stringify(params));
            console.log(params);
            console.log("角色id为："+id);
            var permissionids = "";
            for(var pid of params){
                // console.log(pid);
                permissionids += pid["nodeId"]+",";
            }
            // console.log(permissionids);
            $.ajax({
                url:"${pageContext.request.contextPath}/Role/ChangeRoleCheckedPermission",
                type:"post",
                data:{role_id:id,permissionids:permissionids},
                success:function (data) {
                    if(data === "success"){
                        layer.msg("修改权限成功!");
                    }else if(data === "fail"){
                        layer.msg("修改权限失败!");
                    }
                },
                error:function (error) {
                    layer.msg("请求失败,请重新尝试!");
                }

            })
            // console.log(typeof params);
        });
    });
</script>
</html>
