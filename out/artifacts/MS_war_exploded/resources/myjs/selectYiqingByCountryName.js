//定义文档加载事件
$(function(){
	//运行加载34个省市自治区的名称函数
	loadCountryName("nameSelect");
	//默认加载
	loadData("美国");
})
//加载34个省市自治区名称
function loadCountryName(id){
	
	$.ajax({
		url:"selectCountryName",
		type:"get",//提交方式
		dataType:"json",//返回的数据类型
		success:function(data){
			//在浏览器控制台打印数据
			console.log(data);
			//拼接省市名称
			var html ="";
			//遍历数组
			for(var i=0;i<data.length;i++){
				//取出国家名称
				console.log(data[i].provinceName);
				html +="<option value='"+data[i].provinceName+"'>"+data[i].provinceName+"</option>"
			}
			//加载到select下来框中proNameOne
			$("#"+id+"").html(html);
			
			
		}
	})
}
//加载饼图数据
function loadData(name){
	console.log(name)
	$.ajax({
		url:"selectyiqingByCountryName",
		type:"get",//提交方式
		data:{"pName":name},
		dataType:"json",//返回的数据类型
		success:function(data){
			//在浏览器控制台打印数据
			console.log(data);
			var pie_fanzui =echarts.init(document.getElementById("foreignCountryCurrentInfoWithCoutryName"),'infographic'); 
			option = {
			    title : {
			        x:'center'
			       
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: ['现有确诊','现有治愈','现有死亡'],
			        textStyle: {color: 'balck'}
			    },
			    
				label: {
				     normal: {
				          textStyle: {
				                color: 'red'  // 改变标示文字的颜色
				          }
				     }
				},
			    series : [
			        {
			            name: '今日国外疫情分析',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:data,
			          
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			            
			        }
			    ]
			    
			};
			
			//
			pie_fanzui.setOption(option);
			
			
		}
	})
	
}
//下来框选中触发的函数
function selectByCountryName(){
	var name = $("#nameSelect").val();
	loadData(name);
}