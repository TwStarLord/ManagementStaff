$(function(){
	//下拉框加载34个省市自治区名称
	loadPName("curedArea");
	
	loadCuredmData("黑龙江")
})

//加载数据
function loadCuredmData(name){
	$.ajax({
		url:"selectFiveCuredByPName",
		type:"get",//提交方式
		data:{"pName":name},
		dataType:"json",//返回的数据类型
		success:function(data){
			//在浏览器控制台打印数据
			console.log(data);
			var pie_fanzui =echarts.init(document.getElementById("cureCount"),'infographic'); 
		       option = {
		         
		         tooltip: {
				        trigger: 'axis',
				        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
				            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
		           grid: {
		                     left:40,
		                     right:20,
		                     top:30,
		                     bottom:0,
		                     containLabel: true
		                   },

		                 xAxis: {
		                     type: 'value',
		                     axisLine:{
		                      lineStyle:{
		                        color:'rgba(255,255,255,0)'
		                      }
		                    },
		                    splitLine:{
		                      lineStyle:{
		                        color:'rgba(255,255,255,0)'
		                      }
		                    },
		                    axisLabel:{
		                        color:"rgba(255,255,255,0)"
		                    },
		                     boundaryGap: [0, 0.01]
		                 },
		                 yAxis: {
		                     type: 'category',
		                     axisLine:{
		                      lineStyle:{
		                        color:'rgba(255,255,255,5)'
		                      }
		                    },
		                    splitLine:{
		                      lineStyle:{
		                        color:'rgba(255,255,255,1)'
		                      }
		                    },
		                    axisLabel:{
		                        color:"red"
		                    },
		                     data: data.area
		                 },
		                 series: [
		                     {
		                         name: data.time,
		                         type: 'bar',
		                         barWidth :20,
		                         itemStyle: {
		                             normal: {
		                                 color:'blue',
		                                 opacity: 1,
		                 				barBorderRadius: 5,
		                             }
		                         },
		                         data: data.data
		                     }
		                 ]
		             };
		      
			pie_fanzui.setOption(option);
		
		}
	})
}

//下拉框触发的函数
function selectAreaByCured(){
	var name = $("#curedArea").val();
	loadCuredmData(name)

}
