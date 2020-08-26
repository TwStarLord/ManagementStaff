//文档加载事件
$(function(){
	$.ajax({
		url:"showYiqingOfWorldWithLine",
		type:"get",
		success:function(data){
			console.log(data);
			var pie_fanzui =echarts.init(document.getElementById("showYiqingOfWorldWithLine")); 
			
			option = {
				    title: {
				        text: '日期-疫情'
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data: ['确诊数量', '治愈数量', '死亡数量']
				    },
				    grid: {
				    	top:'25%',
				        left: '3%',
				        right: '4%',
				        bottom: '1%',
				        containLabel: true
				    },
				    toolbox: {
				        feature: {
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: data.dateList
				    },
				    yAxis: {
				        type: 'value'
				    },
				    series: [
				        {
				            name: '确诊数量',
				            type: 'line',
				            stack: '总量',
				            data: data.confirmedList
				        },
				        {
				            name: '治愈数量',
				            type: 'line',
				            stack: '总量',
				            data: data.curedList
				        },
				        {
				            name: '死亡数量',
				            type: 'line',
				            stack: '总量',
				            data: data.deadList
				        }
				    ]
				};

			
			//
			pie_fanzui.setOption(option);
		},
		error:function(error){
			console.log(error)
		}
	})
})