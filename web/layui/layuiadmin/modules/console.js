/**

 @Name：layuiAdmin 主页控制台
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：GPL-2
    
 */


layui.define(function(exports){
  
  /*
    下面通过 layui.use 分段加载不同的模块，实现不同区域的同时渲染，从而保证视图的快速呈现
  */
  
  
  //区块轮播切换
  layui.use(['admin', 'carousel'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,carousel = layui.carousel
    ,element = layui.element
    ,device = layui.device();

    //轮播切换
    $('.layadmin-carousel').each(function(){
      var othis = $(this);
      carousel.render({
        elem: this
        ,width: '100%'
        ,arrow: 'none'
        ,interval: othis.data('interval')
        ,autoplay: othis.data('autoplay') === true
        ,trigger: (device.ios || device.android) ? 'click' : 'hover'
        ,anim: othis.data('anim')
      });
    });
    
    element.render('progress');
    
  });

  //数据概览
  layui.use(['carousel', 'echarts'], function(){
    var $ = layui.$
    ,carousel = layui.carousel
    ,echarts = layui.echarts;
      var echartsApp = [], options = [];
    $.ajax({
              url:"../StaffInfo/selCompanyInfoationOfSex",
              type:"get",
              success:function (data) {
                  // var echartsApp = [], options = [

                      // 创建ehcarts报表

                      var sexinfo = {
                          title: {
                              text: '公司男女比例信息',
                              x: 'left',
                              textStyle: {
                                  fontSize: 14
                              }
                          },
                          tooltip: {
                              trigger: 'item',
                              formatter: '{a} <br/>{b}: {c} ({d}%)'
                          },
                          legend: {
                              orient: 'vertical',
                              left: 10,
                              data: data.sexDataList
                          },
                          series: [
                              {
                                  name: '男女比例',
                                  type: 'pie',
                                  radius: ['50%', '70%'],
                                  avoidLabelOverlap: false,
                                  label: {
                                      show: false,
                                      position: 'center'
                                  },
                                  emphasis: {
                                      label: {
                                          show: true,
                                          fontSize: '30',
                                          fontWeight: 'bold'
                                      }
                                  },
                                  labelLine: {
                                      show: false
                                  },
                                  data:data.valueDataList
                              }
                          ]
                      };
                  options.push(sexinfo),
                      $.ajax({
                          type:"get",
                          url:"../SystemInfo/GetBrowserInfo",
                          success:function (data) {
                            var browserInfo = {
                                    title : {
                                        text: '访客浏览器分布',
                                        x: 'center',
                                        textStyle: {
                                            fontSize: 14
                                        }
                                    },
                                    tooltip : {
                                        trigger: 'item',
                                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                                    },
                                    legend: {
                                        orient : 'vertical',
                                        x : 'left',
                                        data:data.browserNameList
                                    },
                                    series : [{
                                        name:'访问来源',
                                        type:'pie',
                                        radius : '55%',
                                        center: ['50%', '50%'],
                                        data:data.browserInfoList
                                    }]
                                };
                              options.push(browserInfo);
                              //获取公司基本信息
                              $.ajax({
                                  type:"get",
                                  url:"../SystemInfo/GetCompanyInfo",
                                  success:function (data) {
                                      var companyinfo = {
                                          tooltip: {
                                              trigger: 'axis',
                                              axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                                                  type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                              }
                                          },
                                          legend: {
                                              data: [ '男', '女', '博士', '硕士', '本科', '其他']
                                          },
                                          grid: {
                                              left: '3%',
                                              right: '4%',
                                              bottom: '3%',
                                              containLabel: true
                                          },
                                          xAxis: [
                                              {
                                                  type: 'category',
                                                  data: data.departnameList
                                              }
                                          ],
                                          yAxis: [
                                              {
                                                  type: 'value'
                                              }
                                          ],
                                          series: [
                                              {
                                                  name: '男',
                                                  type: 'bar',
                                                  stack: '性别',
                                                  data: data.maleCountList
                                              },
                                              {
                                                  name: '女',
                                                  type: 'bar',
                                                  stack: '性别',
                                                  data: data.femaleCountList
                                              },
                                              {
                                                  name: '博士',
                                                  type: 'bar',
                                                  stack: '学历',
                                                  data: data.edubackCountList[0]
                                              },
                                              {
                                                  name: '硕士',
                                                  type: 'bar',
                                                  stack: '学历',
                                                  data: data.edubackCountList[1]
                                              },
                                              {
                                                  name: '本科',
                                                  type: 'bar',
                                                  stack: '学历',
                                                  data: data.edubackCountList[2]
                                              },
                                              {
                                                  name: '其他',
                                                  type: 'bar',
                                                  stack: '学历',
                                                  data: data.edubackCountList[3]
                                              }
                                          ]
                                      };
                                      options.push(companyinfo);
                                  }
                              })
                          }
                      })

                  // ]
                      ,elemDataView = $('#LAY-index-dataview').children('div')
                      ,renderDataView = function(index){
                      echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
                      echartsApp[index].setOption(options[index]);
                      window.onresize = echartsApp[index].resize;
                  };
                  //没找到DOM，终止执行
                  if(!elemDataView[0]) return;
                  renderDataView(0);

                  //监听数据概览轮播
                  var carouselIndex = 0;
                  carousel.on('change(LAY-index-dataview)', function(obj){
                      renderDataView(carouselIndex = obj.index);
                  });

                  //监听侧边伸缩
                  layui.admin.on('side', function(){
                      setTimeout(function(){
                          renderDataView(carouselIndex);
                      }, 300);
                  });

                  //监听路由
                  layui.admin.on('hash(tab)', function(){
                      layui.router().path.join('') || renderDataView(carouselIndex);
                  });
              },
              error:function (error) {
                  console.log(error);
              }
          })

  });

  //最新订单
  layui.use('table', function(){
    var $ = layui.$
    ,table = layui.table;



    //今日热搜
      //     // table.render({
      //     //   elem: '#LAY-index-topSearch'
      //     //   ,url: layui.setter.base + 'json/console/top-search.js' //模拟接口
      //     //   ,page: true
      //     //   ,cols: [[
      //     //     {type: 'numbers', fixed: 'left'}
      //     //     ,{field: 'keywords', title: '关键词', minWidth: 300, templet: '<div><a href="https://www.baidu.com/s?wd={{ d.keywords }}" target="_blank" class="layui-table-link">{{ d.keywords }}</div>'}
      //     //     ,{field: 'frequency', title: '搜索次数', minWidth: 120, sort: true}
      //     //     ,{field: 'userNums', title: '用户数', sort: true}
      //     //   ]]
      //     //   ,skin: 'line'
      //     // });

      http://v.juhe.cn/toutiao、

    //今日热贴
    table.render({
      elem: '#LAY-index-topCard'
      ,url:  "../Notice/GetHotPostInfo"
      ,page: true
      ,cellMinWidth: 120
      ,cols: [[
        {type: 'numbers', fixed: 'left'}/*target="_blank"*/
        ,{field: 'title', title: '标题', minWidth: 300, templet:function (d) {
            console.log(d);
            var localObj = window.location;
            var contextPath = localObj.pathname.split("/")[1];
            var path = contextPath + d.href;
            var noticeidstr = "?noticeid="+d.id;
            console.log( d.id);
            // var id = d.id;
            return '<div><a href="../../'+ path + noticeidstr + '" ' + 'target="_blank"  class="layui-table-link"> ' + d.title + '</div>';
                }}
        ,{field: 'date', title: '发布日期'}
        ,{field: 'author', title: '发帖者'}
        ,{field: 'collectCount', title: '收藏率'}
      ]]
      ,skin: 'line'
    });
  });
  
  exports('console', {})
});