<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>消息中心</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid" id="LAY-app-message">
    <div class="layui-card">
        <div class="layui-tab layui-tab-brief">
            <ul class="layui-tab-title">
                <li class="layui-this">全部消息</li>
                <li id="pendingNewsCount">通知</li>
            </ul>
            <div class="layui-tab-content">

                <div class="layui-tab-item layui-show">
                    <div class="LAY-app-message-btns" style="margin-bottom: 10px;">
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="all" data-events="del">删除</button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="all" data-events="ready">标记已读</button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="all" data-events="readyAll">全部已读</button>
                    </div>
                    <%--LAY-app-message-all--%>
                    <table id="allmessage" lay-filter="allmessage"></table>
                </div>
                <div class="layui-tab-item">

                    <div class="LAY-app-message-btns" style="margin-bottom: 10px;">
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="notice" data-events="del">删除</button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="notice" data-events="ready">标记已读</button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="notice" data-events="readyAll">全部已读</button>
                    </div>
                    <%--LAY-app-message-notice--%>
                    <table id="noticemessage" lay-filter="noticemessage"></table>
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
    }).use(['index','admin', 'table', 'util'],function () {
    var $ = layui.$
        ,admin = layui.admin
        ,table = layui.table
        ,element = layui.element;

    var DISABLED = 'layui-btn-disabled'

        //区分各选项卡中的表格
        ,tabs = {
            all: {
                text: '全部消息'
                ,id: 'allmessage'
            }
            ,notice: {
                text: '通知'
                ,id: 'noticemessage'
            }
        };

    //标题内容模板
    var tplTitle = function(d){
        return '<a href="detail.html?id='+ d.id +'">'+ d.title;
    };

    $(function(){
        $.ajax({
            url:"${pageContext.request.contextPath}/SystemInfo/GetPendingNewsCount",
            type:"get",
            success:function(data){
                if(data.code == 0){
                    var pendingNewCountStr = '通知<span class="layui-badge">'+ data.count +'</span>';
                    // $("#pendingNewsCount").find("span").text(data.count);
                    $("#pendingNewsCount").html(pendingNewCountStr);
                }else {
                    layer.msg('获取数据失败，请刷新界面!', {
                        icon: 2
                    });
                }
            },
            error:function (error) {
                layer.msg('请求失败，请重新尝试!', {
                    icon: 2
                });
            }
        })
    })

    //全部消息
    table.render({
        elem: '#allmessage'
        ,url:"${pageContext.request.contextPath}/SystemInfo/GetAllNews"
        ,page: true
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', title: '序号', minWidth: 300}
            ,{field: 'sender', title: '发送者', minWidth: 300}
            ,{field: 'senderdepartid', title: '发送部门编号', minWidth: 300}
            ,{field: 'receiver', title: '接收者', minWidth: 300}
            ,{field: 'receiverdepartid', title: '接收部门编号', minWidth: 300}
            ,{field: 'recordtime', title: '记录时间', width: 170, templet: '<div>{{ layui.util.timeAgo(d.time) }}</div>'}
            ,{field: 'operation', title: '操作说明', minWidth: 300}
            ,{field: 'status', title: '状态', minWidth: 300}
            ,{field:'right', width:130, title: '操作',align:'center',fixed: 'right',templet:function (d) {
                    if(d.operation == "分配部门" && d.status === "待审核"){
                        return "<button class='layui-btn  layui-btn-xs layui-btn' lay-event='handler'>分配</button>";
                    }else if(d.operation == "分配部门" && d.status === "已处理"){
                        return "<button class='layui-btn-disabled  layui-btn-xs layui-btn'>分配</button>";
                    }else if(d.operation == "请假申请" && d.status === "待审核"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'agree\' >同意</button>\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn-danger\' lay-event=\'refuse\'>拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "请假申请" && d.status === "已处理"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn\'>同意</button>\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn-danger\'>拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "考勤补卡"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'agree\' >同意</button>\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn-danger\' lay-event=\'refuse\'>拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "出差安排" && d.status === "已确认"){
                        return '<button class=\'layui-btn-disabled  layui-btn-xs layui-btn\' id=\'confirmChuChai\'>确认</button>';
                    }else if(d.operation == "出差安排" && d.status === "待确认"){
                        return '<button class=\'layui-btn  layui-btn-xs layui-btn\' id=\'confirmChuChai\' lay-event=\'confirm\'>确认</button>';
                    }else if(d.operation == "销假申请" && d.status === "待审核"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'agree\' >同意</button>\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn-danger\' lay-event=\'refuse\'>拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "销假申请" && d.status != "待审核"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn\' >同意</button>\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn-danger\' >拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "出差报销" && d.status === "待审核"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'agree\' >同意</button>\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn-danger\' lay-event=\'refuse\'>拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "出差报销" && d.status != "待审核"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn\'>同意</button>\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn-danger\'>拒绝</button>\n' +
                            '    </div>';
                    }else{
                        return "系统通知";
                    }
                }}
            ]]
        ,skin: 'line'
    });

    //待处理消息
    table.render({
        elem: '#noticemessage'
        ,url:"${pageContext.request.contextPath}/SystemInfo/GetPendingNews"
        ,page: true
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', title: '序号', minWidth: 300}
            ,{field: 'sender', title: '发送者', minWidth: 300}
            ,{field: 'senderdepartid', title: '发送部门编号', minWidth: 300}
            ,{field: 'receiver', title: '接收者', minWidth: 300}
            ,{field: 'receiverdepartid', title: '接收部门编号', minWidth: 300}
            ,{field: 'recordtime', title: '记录时间', width: 170, templet: '<div>{{ layui.util.timeAgo(d.time) }}</div>'}
            ,{field: 'operation', title: '操作说明', minWidth: 300}
            ,{field: 'status', title: '状态', minWidth: 300}
            ,{field:'right', width:130, title: '操作',align:'center',fixed: 'right',templet:function (d) {
                    if(d.operation == "分配部门" && d.status === "待审核"){
                        return "<button class='layui-btn  layui-btn-xs layui-btn' lay-event='handler'>分配</button>";
                    }else if(d.operation == "分配部门" && d.status === "已处理"){
                        return "<button class='layui-btn-disabled  layui-btn-xs layui-btn'>分配</button>";
                    }else if(d.operation == "请假申请" && d.status === "待审核"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'agree\' >同意</button>\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn-danger\' lay-event=\'refuse\'>拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "请假申请" && d.status === "已处理"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn\'>同意</button>\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn-danger\'>拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "考勤补卡"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'agree\' >同意</button>\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn-danger\' lay-event=\'refuse\'>拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "出差安排" && d.status === "已确认"){
                        return '<button class=\'layui-btn-disabled  layui-btn-xs layui-btn\' id=\'confirmChuChai\'>确认</button>';
                    }else if(d.operation == "出差安排" && d.status === "待确认"){
                        return '<button class=\'layui-btn  layui-btn-xs layui-btn\' id=\'confirmChuChai\' lay-event=\'confirm\'>确认</button>';
                    }else if(d.operation == "销假申请" && d.status === "待审核"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'agree\' >同意</button>\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn-danger\' lay-event=\'refuse\'>拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "销假申请" && d.status != "待审核"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn\' >同意</button>\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn-danger\' >拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "出差报销" && d.status === "待审核"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'agree\' >同意</button>\n' +
                            '    <button class=\'layui-btn  layui-btn-xs layui-btn-danger\' lay-event=\'refuse\'>拒绝</button>\n' +
                            '    </div>';
                    }else if(d.operation == "出差报销" && d.status != "待审核"){
                        return '<div class="layui-btn-container">\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn\'>同意</button>\n' +
                            '    <button class=\'layui-btn-disabled  layui-btn-xs layui-btn-danger\'>拒绝</button>\n' +
                            '    </div>';
                    }else{
                        return "系统通知";
                    }
                }}
        ]]
        ,skin: 'line'
    });


    //事件处理
    var events = {
        del: function(othis, type){
            var thisTabs = tabs[type]
                ,checkStatus = table.checkStatus(thisTabs.id)
                ,data = checkStatus.data; //获得选中的数据
            if(data.length === 0) return layer.msg('至少选中一行数据!');
            for(var i=0;i<data.length;i++){
                var batchid = data[i].id + ",";
            }
            layer.confirm('确定删除选中的数据吗？', function(){
               $.ajax({
                   url:"${pageContext.request.contextPath}/SystemInfo/DeleteNews",
                   type:"post",
                   data:{batchid:batchid},
                   success:function (data) {
                       if(data == "success"){
                           layer.msg('删除成功', {
                               icon: 1
                           });
                       }else{
                           layer.msg('删除失败', {
                               icon: 2
                           });
                       }

                   },
                   error:function (error){
                       layer.msg('请求失败，请重新尝试!', {
                           icon: 2
                       });
                   }
               })
                table.reload(thisTabs.id); //刷新表格
            });
        }
        ,ready: function(othis, type){
            var thisTabs = tabs[type]
            ,checkStatus = table.checkStatus(thisTabs.id)
            ,data = checkStatus.data; //获得选中的数据
            if(data.length === 0) return layer.msg('至少选中一行数据!');
            for(var i=0;i<data.length;i++){
                var batchid = data[i].id + ",";
            }
            $.ajax({
                type:"post",
                url:"${pageContext.request.contextPath}/SystemInfo/ChangeReadStatusBySelect",
                data:{batchid:batchid},
                success:function (data) {
                    if(data == "success"){
                        layer.msg('标记已读成功', {
                            icon: 1
                        });
                    }else{
                        layer.msg('标记已读失败!', {
                            icon: 1
                        });
                    }
                },
                error:function (error) {
                    layer.msg('请求失败，请重新尝试!', {
                        icon: 2
                    });
                }
            })
            table.reload(thisTabs.id); //刷新表格
        }
        ,readyAll: function(othis, type){
            var thisTabs = tabs[type];
            $.ajax({
                type:"post",
                url:"${pageContext.request.contextPath}/SystemInfo/ChangeAllNewsReadStatus",
                success:function (data) {
                    if(data == "success"){
                        layer.msg(thisTabs.text + '：全部已读', {
                            icon: 1
                        });
                    }else{
                        layer.msg('失败', {
                            icon: 2
                        });
                    }
                },
                error:function (error) {
                    layer.msg('请求失败，请重新尝试!', {
                        icon: 2
                    });
                }
            })
        }
    };

    $('.LAY-app-message-btns .layui-btn').on('click', function(){
        var othis = $(this)
            ,thisEvent = othis.data('events')
            ,type = othis.data('type');
        events[thisEvent] && events[thisEvent].call(this, othis, type);
    });

    // 监听表格按钮点击事件
        table.on('tool(noticemessage)',function(obj){
            var data = obj.data;
            var layevent = obj.event;

            // 审批请假信息
            if(layevent === "agree" && data.operation == "请假申请"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Qingjia/ChangeQingjiaApplyStatus",
                    type:"post",
                    data:{id:data.descripetion,status:"同意",newsid:data.id},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已同意该请假申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('noticemessage');
                        }else{
                            layer.msg("提交失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "refuse" && data.operation == "请假申请"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Qingjia/ChangeQingjiaApplyStatus",
                    type:"post",
                    data:{id:data.descripetion,status:"不同意",newsid:data.id},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("不同意该请假申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('noticemessage');
                        }else{
                            layer.msg("提交失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "confirm" && data.operation == "出差安排"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Chuchai/ConfirmChuchaiManagement",
                    type:"post",
                    data:{id:data.descripetion,status:"已确认",newsid:data.id},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已确认该出差安排!");
                            console.log($("button#confirmChuChai"));
                            $("button#confirmChuChai").removeClass("layui-btn").addClass("layui-btn-disabled").attr("disabled","disabled");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('noticemessage');
                        }else{
                            layer.msg("确认失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "agree" && data.operation == "考勤补卡"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Kaoqin/ChangeKaoqinApplyStatus",
                    type:"post",
                    data:{id:data.descripetion,checkstatus:0,newsid:data.id},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已同意该考勤补卡申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('noticemessage');
                        }else{
                            layer.msg("确认失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "refuse" && data.operation == "考勤补卡"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Kaoqin/ChangeKaoqinApplyStatus",
                    type:"post",
                    data:{id:data.descripetion,checkstatus:1,newsid:data.id},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已同意该考勤补卡申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('noticemessage');
                        }else{
                            layer.msg("同意失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "agree" && data.operation === "销假申请"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Qingjia/HandlerEndQingjiaApply",
                    type:"post",
                    data:{qingjia_id:data.descripetion,newsid:data.id,reply:layevent},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已同意该销假申请申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('noticemessage');
                        }else{
                            layer.msg("同意失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "refuse" && data.operation === "销假申请"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Qingjia/HandlerEndQingjiaApply",
                    type:"post",
                    data:{qingjia_id:data.descripetion,newsid:data.id,reply:layevent},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已拒绝该销假申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('noticemessage');
                        }else{
                            layer.msg("拒绝失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "agree" && data.operation === "出差报销"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Chuchai/HandlerCountChuchaiApply",
                    type:"post",
                    data:{chuchai_id:data.descripetion,newsid:data.id,reply:layevent},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已同意该出差报销申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('noticemessage');
                        }else{
                            layer.msg("同意失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "refuse" && data.operation === "出差报销"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Chuchai/HandlerCountChuchaiApply",
                    type:"post",
                    data:{chuchai_id:data.descripetion,newsid:data.id,reply:layevent},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已拒绝该出差报销申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('noticemessage');
                        }else{
                            layer.msg("同意失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "handler" && data.operation === "分配部门"){
                console.log(data);
            }

        })

        // 监听表格按钮点击事件
        table.on('tool(allmessage)',function(obj){
            var data = obj.data;
            var layevent = obj.event;
            console.log(layevent === 'agree');
            console.log(data.operation == "请假申请");

            // 审批请假信息
            if(layevent === "agree" && data.operation == "请假申请"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Qingjia/ChangeQingjiaApplyStatus",
                    type:"post",
                    data:{id:data.descripetion,status:"同意",newsid:data.id},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已同意该请假申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('allmessage');
                        }else{
                            layer.msg("提交失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "refuse" && data.operation == "请假申请"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Qingjia/ChangeQingjiaApplyStatus",
                    type:"post",
                    data:{id:data.descripetion,status:"不同意",newsid:data.id},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("不同意该请假申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('allmessage');
                        }else{
                            layer.msg("提交失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "confirm" && data.operation == "出差安排"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Chuchai/ConfirmChuchaiManagement",
                    type:"post",
                    data:{id:data.descripetion,status:"已确认",newsid:data.id},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已确认该出差安排!");
                            console.log($("button#confirmChuChai"));
                            $("button#confirmChuChai").removeClass("layui-btn").addClass("layui-btn-disabled").attr("disabled","disabled");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('allmessage');
                        }else{
                            layer.msg("确认失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "agree" && data.operation == "考勤补卡"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Kaoqin/ChangeKaoqinApplyStatus",
                    type:"post",
                    data:{id:data.descripetion,checkstatus:0,newsid:data.id},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已同意该考勤补卡申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('allmessage');
                        }else{
                            layer.msg("确认失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "refuse" && data.operation == "考勤补卡"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Kaoqin/ChangeKaoqinApplyStatus",
                    type:"post",
                    data:{id:data.descripetion,checkstatus:1,newsid:data.id},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已同意该考勤补卡申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('allmessage');
                        }else{
                            layer.msg("同意失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "agree" && data.operation === "销假申请"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Qingjia/HandlerEndQingjiaApply",
                    type:"post",
                    data:{qingjia_id:data.descripetion,newsid:data.id,reply:layevent},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已同意该销假申请申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('allmessage');
                        }else{
                            layer.msg("同意失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "refuse" && data.operation === "销假申请"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Qingjia/HandlerEndQingjiaApply",
                    type:"post",
                    data:{qingjia_id:data.descripetion,newsid:data.id,reply:layevent},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已拒绝该销假申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('allmessage');
                        }else{
                            layer.msg("拒绝失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "agree" && data.operation === "出差报销"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Chuchai/HandlerCountChuchaiApply",
                    type:"post",
                    data:{chuchai_id:data.descripetion,newsid:data.id,reply:layevent},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已同意该出差报销申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('allmessage');
                        }else{
                            layer.msg("同意失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "refuse" && data.operation === "出差报销"){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Chuchai/HandlerCountChuchaiApply",
                    type:"post",
                    data:{chuchai_id:data.descripetion,newsid:data.id,reply:layevent},
                    success:function (result) {
                        if(result == "success"){
                            layer.msg("已拒绝该出差报销申请!");
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('allmessage');
                        }else{
                            layer.msg("同意失败，请重新尝试!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(layevent === "handler" && data.operation === "分配部门"){
                // console.log(data);
                layer.open({
                    type: 2,
                    title: '分配部门',
                    shade: false,
                    maxmin: true,
                    area: ['60%', '60%'],
                    content: '${pageContext.request.contextPath}/StaffInfo/ToChangeStaffDepartmentInfo?jobid='+data.descripetion
                });
            }
        })

    });
</script>

<script type="text/html" id="btndemo">
    <div class="layui-btn-container">
    <button class='layui-btn  layui-btn-xs layui-btn' lay-event='agree' >同意</button>
    <button class='layui-btn  layui-btn-xs layui-btn-danger' lay-event='refuse'>拒绝</button>
    </div>
</script>
</body>
</html>
