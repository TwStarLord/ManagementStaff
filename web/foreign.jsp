<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>新冠型肺炎分析系统</title>
    <script src="${pageContext.request.contextPath}/layui/js/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/map.css">


    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/map.css">
</head>
<body>
<!-- <div class="loading">
  <div class="loadbox"> <img src="picture/loading.gif"> 页面加载中... </div>
</div> -->
<div class="data">
    <div class="data-title">
        <div class="title-center ">新冠型肺炎国外疫情分析</div>
    </div>
    <div class="data-content">
        <div class="con-left fl">
            <div class="left-top">
                <div class="info boxstyle">
                    <div class="title">国外疫情实时统计</div>
                    <div class="info-main">
                        <ul>
                            <li><span>现有确诊(认)</span><p id="newConfirm"></p></li>
                            <li><span>累计确诊(人)</span><p id="allConfirm"></p></li>
                            <li><span>治愈(人)</span><p id="cured"></p></li>
                            <li><span>死亡(人)</span><p id="dead"></p></li>
                            <li><span>疫情最严重国家</span><p id="maxPname"></p></li>
                            <li><span>疫情最轻国家</span><p id="minPname"></p></li>
                        </ul>
                    </div>
                </div>
                <div class="top-bottom boxstyle">
                    <div class="title">今日国外疫情分析</div>
                    <div id="foreignCountryCurrentInfo" class="charts"></div>
                </div>
            </div>
            <div class="left-bottom boxstyle">
                <div class="title">
                    <span>各国疫情分析</span>
                    <select id="nameSelect" onchange="selectByCountryName()">
                        <option value="美国">美国</option>
                    </select>
                </div>
                <div id="foreignCountryCurrentInfoWithCoutryName" class="charts"></div>
            </div>
        </div>
        <div class="con-center fl">
            <div class="map-num">
                <a href="${pageContext.request.contextPath}/native.jsp" style="color: red">点击查看中国疫情</a>
                <p>全球新冠肺炎死亡人数(人)</p>
                <div class="num" id="showCount">

                </div>
            </div>
            <div class="cen-top map" id="world_map">


            </div>
            <div class="cen-bottom boxstyle">
                <!-- <div class="title">自己设计这块的图表</div> -->
                <div id="showYiqingOfWorldWithLine" class="charts"></div>
            </div>
        </div>
        <div class="con-right fr">
            <div class="right-top boxstyle">
                <div class="title">国外新冠肺炎确诊TOP5</div>
                <div id="confirmCountOfWorld" class="charts"></div>
            </div>
            <div class="right-center boxstyle">
                <div class="title">国外新冠肺炎治愈TOP5</div>
                <div id="cureCountOfWorld" class="charts"></div>
            </div>
            <div class="right-bottom boxstyle">
                <div class="title">国外新冠肺炎死亡TOP5</div>
                <div id="deadCountOfWorld" class="charts"></div>
            </div>
        </div>
    </div>
</div>
</body>

<script src="${pageContext.request.contextPath}/layui/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/layui/js/world.js"></script>
<script src="${pageContext.request.contextPath}/layui/myjs/WorldInfomation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/myjs/selectCurYingqingOfWorld.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/myjs/selectYiqingByCountryName.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/myjs/selectFiveConfrimOfWorld.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/myjs/selectFiveCuredOfWorld.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/myjs/selectFiveDeadOfWorld.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/myjs/showYiqingOfWorldWithLine.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/world.js"></script>
<script src="${pageContext.request.contextPath}/resources/myjs/WorldInfomation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/myjs/selectCurYingqingOfWorld.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/myjs/selectYiqingByCountryName.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/myjs/selectFiveConfrimOfWorld.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/myjs/selectFiveCuredOfWorld.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/myjs/selectFiveDeadOfWorld.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/myjs/showYiqingOfWorldWithLine.js"></script>

<script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
<script type="text/javascript">
    layui.config({
        base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['jquery'], function(){
        var $ = layui.jquery;

        var htmlstr = "";
        $(function(){

            $.ajax({
                url:"${pageContext.request.contextPath}/info/selectDeadCountOfWorld",
                type:"get",
                success:function(data){
                    var datainfo = String (data.deadCount);
                    for(var i = 0;i<datainfo.length;i++){
                        htmlstr += "<span>"+ datainfo[i] +"</span>";
                    }
                    $("#showCount").append(htmlstr);
                    $("#newConfirm").text(data.currentConfirmedCount);
                    $("#allConfirm").text(data.confirmedCount);
                    $("#cured").text(data.curedCount);
                    $("#dead").text(data.deadCount);

                    $.ajax({
                        url:"${pageContext.request.contextPath}/info/selectCountryByCurrentConfirmedCount",
                        type:"get",
                        success:function(data){
                            $("#maxPname").text(data[0]);
                            $("#minPname").text(data[1]);
                        },
                        error:function(error){
                            alert("请求错误，请重试")
                        }
                    })
                },
                error:function(error){
                    alert("请求错误，请重试")
                }
            })
        })

    });


</script>

</html>