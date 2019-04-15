var statistics = angular.module("statistics",['ngRoute','wui.date']);

statistics.config(function($routeProvider){
    $routeProvider.when("/statistics",{
        templateUrl:"templates/statistics/statistics.html",
        controller:"statisticsIndex"
    });
});
statistics._generatePieOption=function(data,title,name){
    if(typeof data != 'object'){
        throw new Error("饼状图数据必须是键值对形式");
    }
    var keys = [];
    var dataset=[];
    for(var key in data){
        keys.push(key);
        var item={};
        item.name=key;
        item.value=data[key];
        dataset.push(item);
    }
    var option = {
        title : {
            text: title,
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:keys
        },
        toolbox: {
            show : true,
            feature : {
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:name,
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:dataset
            }
        ]
    };
    return option;
}
statistics.controller("statisticsIndex",function($scope,$httpWrapper,$breadcrumb,$menu,$routeParams){
    $breadcrumb.pushCrumb("Home","首页","statisticsIndex");
    $menu.switchMenu(menu.HOME);

    $scope.currentTab='tradingStatistic';
    $scope.switchTab=function(tabName){
        $scope.currentTab=tabName;
        switch (tabName){
        	case 'tradingStatistic':{
        		//每日交易量默认
        		Date.prototype.Format = function(fmt) 
        	    { 
        	      var o = { 
        	        "M+" : this.getMonth()+1,                 //月份 
        	        "d+" : this.getDate(),                    //日 
        	        "h+" : this.getHours(),                   //小时 
        	        "m+" : this.getMinutes(),                 //分 
        	        "s+" : this.getSeconds(),                 //秒 
        	        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
        	        "S"  : this.getMilliseconds()             //毫秒 
        	      }; 
        	      if(/(y+)/.test(fmt)) 
        	        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
        	      for(var k in o) 
        	        if(new RegExp("("+ k +")").test(fmt)) 
        	      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
        	      return fmt; 
        	    }
/*        		var x = new Date();
    			if(!$scope.daydate) dayStratTime = Number(x.getTime()/1000 - 28800);
    			if(!$scope.daydate) dayEndTime = Number(x.getTime()/1000 + 57600);
    			$scope.daydate = new Date().Format("yyyy-MM-dd");*/
    			var nowtime = CurentTime();
    			// 获取页面输入条件
    			var txCode = $scope.txCode;
    			var chnlType = $scope.chnlType;
    			var findType = $scope.findType;
    			if(!$scope.txCode) txCode = "";
    			if(!$scope.chnlType) chnlType = "";
    			if(!$scope.findType) findType = "minute";
    			
    			var dayStratTime = $scope.startdate1;
    			var dayEndTime = $scope.enddate1;
    			if(!$scope.startdate1) dayStratTime = nowtime;
    			if(!$scope.enddate1) dayEndTime = nowtime;
    			// 如条件为minute 即只返回一条数据避免数据过多
    			if("minute"==findType){
    				dayStratTime = nowtime;
        			dayEndTime = nowtime;
    			}
    			$httpWrapper.post({
    			url:"dayTrading/getDayTradingByPageByCondition",
    			data:'{"dayTradingStartDate": "'+dayStratTime+'","dayTradingEndDate": "'+dayEndTime+'","findType": "'+findType+'","txCode": "'+txCode+'","chnlCode": "'+chnlType+'"}',
                success:function(data){
                    require( [
                              'echarts',
                              'echarts/chart/line', // 使用柱状图就加载line模块，按需加载
                              'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                          ], function (echarts) {
                              require(['echarts/theme/macarons2'], function(curTheme){
                            	  var arrs = data.list;
                                  var xAxisData=[];
                                  
                                  for(var a=0; a<arrs.length; a++){
                                	  
                                	  xAxisData.push([new Date(arrs[a].timestamp*1000),
                                	                  arrs[a].totalTimeNum
                                	                  ]);
                                  }
                            	  var option = {
	                            			title: {
	                              		        text: '每日交易量折线图',
	                              		    },
                            			    tooltip : {
                            			        trigger: 'item',
                            			        formatter : function (params) {
                            			            var date = new Date(params.value[0]);
                            			            data = date.getFullYear() + '-'
                            			                   + (date.getMonth() + 1) + '-'
                            			                   + date.getDate() + ' '
                            			                   + date.getHours() + ':'
                            			                   + date.getMinutes() + ':'
                            			                   + date.getSeconds();
                            			            return data + '<br/>'
                            			                   + params.value[1] + '次交易'
                            			        },
                            			        enterable: true
                            			    },
                            			    toolbox: {
                            			        show : true,
                            			        feature: {
                                                    restore : {show: true},
                                                    saveAsImage : {show: true}
                                  		        }
                            			    },
                            			    dataZoom: {
                            			        show: true,
                            			        start : 0
                            			    },
                            			    legend : {
                            			        data : ['每日交易量(单位/笔)']
                            			    },
                            			    grid: {
                            			            y2: 80
                            			    },
                            			    
                            			    xAxis : [
                            			        {
                            			            type : 'time',
                            			            splitNumber:10,
                                    		        splitLine:{show: false},
                                    		        splitArea : {show : false},
                                    		        axisLine:{
                                                        lineStyle:{color:'#708090'}
                                                    }
                            			        }
                            			    ],
                            			    yAxis : [
                            			        {
                            			            type : 'value',
                                    		        splitLine:{show: false},
                                    		        splitArea : {show : true},
                                    		        axisLine:{
                                                        lineStyle:{color:'#708090'}
                                                    }
                            			        }
                            			    ],
                            			    series : [
                            			        {
                            			            name: '每日交易量(单位/笔)',
                            			            type: 'line',
                            			            showAllSymbol: true,
                            			            data:xAxisData
                            			        }
                            			    ]
                            			};
                            			    
                                  var myChart = echarts.init(document.getElementById('dayTrading'));
                                  myChart.setTheme(curTheme)
                                  myChart.setOption(option);
                                  window.onresize = myChart.resize;
                              });
                          });
                }
            });
    	        $scope.chnlDefs=[];
    	        $httpWrapper.post({
    	            url:"notification/getChnlDef",
    	            success:function(data){
    	                $scope.chnlDefs=data.list;
    	                $(function() {
    	        			$(".selectpicker").selectpicker({
    	        				noneSelectedText: '请选择',
    	        				countSelectedText: function(){}
    	        			});
    	        		});
    	        	   function selectValue() {
    	               //获取选择的值
    	                    alert($('#chnlType').selectpicker('val'));
    	                }
    	                if(!data||data.length<0){
    	                    $scope.isEmpty=true;
    	                }
    	                $scope.originData=data;
    	            }
    	        });
    	        
    	        $scope.txDefs=[];
    	        $httpWrapper.post({
    	            url:"notification/getTxCode",
    	            success:function(data){
    	                $scope.txDefs=data.list;
    	                $(function() {
    	        			$(".selectpicker").selectpicker({
    	        				noneSelectedText: '请选择',
    	        				countSelectedText: function(){}
    	        			});
    	        		});
    	        	   function selectValue() {
    	               //获取选择的值
    	                    alert($('#txCode').selectpicker('val'));
    	                }
    	                if(!data||data.length<0){
    	                    $scope.isEmpty=true;
    	                }
    	                $scope.originData=data;
    	            }
    	        });
    			
    			// 获取时间字符串
    		    function CurentTime()  
    	        {   
    	            var now = new Date();  
    	                 
    	            var year = now.getFullYear();       //年  
    	            var month = now.getMonth() + 1;     //月  
    	            var day = now.getDate();            //日  
    	                 
    	            var clock = year + "-";  
    	                 
    	            if(month < 10) clock += "0";         
    	            clock += month + "-";  
    	                 
    	            if(day < 10) clock += "0";   
    	            clock += day;  
    	          
    	            return(clock);   
    	        }
    			
        		//每日交易量查询
        		$scope.queryDayTrading = function(){
        			var nowtime = CurentTime();
        			// 获取页面输入条件
        			var txCode = $scope.txCode;
        			var chnlType = $scope.chnlType;
        			var findType = $scope.findType;
        			if(!$scope.txCode) txCode = "";
        			if(!$scope.chnlType) chnlType = "";
        			if(!$scope.findType) findType = "minute";
        			
        			var dayStratTime = $scope.startdate1;
        			var dayEndTime = $scope.enddate1;
        			if(!$scope.startdate1) dayStratTime = nowtime;
        			if(!$scope.enddate1) dayEndTime = nowtime;
        			// 如条件为minute 即只返回一条数据避免数据过多
        			if("minute"==findType){
        				dayStratTime = nowtime;
            			dayEndTime = nowtime;
        			}
        			if("day"==findType){
        				
        			}
        			$httpWrapper.post({
        			url:"dayTrading/getDayTradingByPageByCondition",
        			data:'{"dayTradingStartDate": "'+dayStratTime+'","dayTradingEndDate": "'+dayEndTime+'","findType": "'+findType+'","txCode": "'+txCode+'","chnlCode": "'+chnlType+'"}',
                    success:function(data){
                        require( [
                                  'echarts',
                                  'echarts/chart/line', // 使用柱状图就加载line模块，按需加载
                                  'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                              ], function (echarts) {
                                  require(['echarts/theme/macarons2'], function(curTheme){
                                	  var arrs = data.list;
                                      var xAxisData=[];
                                      var successData=[];
                                      var failData=[];
                                      
                                      for(var a=0; a<arrs.length; a++){
                                    	  
/*                                    	  xAxisData.push([new Date(arrs[a].timestamp*1000),
                                    	                  arrs[a].totalTimeNum
                                    	                  ]);*/
                                    	  if(findType=="day"){
                                    		  xAxisData.push([arrs[a].nowtime,arrs[a].totalTimeNum]);
                                    		  successData.push([arrs[a].nowtime,arrs[a].success]);
                                    		  failData.push([arrs[a].nowtime,arrs[a].fail]);
                                    	  }else{
                                    		  xAxisData.push([arrs[a].startTime,arrs[a].totalTimeNum]);
                                    		  successData.push([arrs[a].startTime,arrs[a].success]);
                                    		  failData.push([arrs[a].startTime,arrs[a].fail]);
                                    	  }
                                    	 
                                      }
                                	  var option = {
    	                            			title: {
    	                              		        text: '每日交易量折线图',
    	                              		    },
                                			    tooltip : {
                                			        trigger: 'item',
                                			        formatter : function (params) {
                                			            var date = new Date(params.value[0]);
                                			            data = date.getFullYear() + '-'
                                			                   + (date.getMonth() + 1) + '-'
                                			                   + date.getDate() + ' '
                                			                   + date.getHours() + ':'
                                			                   + date.getMinutes() + ':'
                                			                   + date.getSeconds();
                                			            return data + '<br/>'
                                			                   + params.value[1] + '次交易'
                                			        },
                                			        enterable: true
                                			    },
                                			    toolbox: {
                                			        show : true,
                                			        feature: {
                                                        restore : {show: true},
                                                        saveAsImage : {show: true}
                                      		        }
                                			    },
                                			    dataZoom: {
                                			        show: true,
                                			        start : 0,
                                			        end:100
                                			    },
                                			    legend : {
                                			        data : ['每日交易量(单位/笔)']
                                			    },
                                			    grid: {
                            			            y2: 80
                                			    },
                                			    xAxis : [
                                			        {
                                			            type : 'time',
                                			            splitNumber:10,
                                        		        splitLine:{show: false},
                                        		        splitArea : {show : false},
                                        		        axisLine:{
                                                            lineStyle:{color:'#708090'}
                                                        }
                                			        }
                                			    ],
                                			    yAxis : [
                                			        {
                                			            type : 'value',
                                        		        splitLine:{show: false},
                                        		        splitArea : {show : true},
                                        		        axisLine:{
                                                            lineStyle:{color:'#708090'}
                                                        }
                                			        }
                                			    ],
                                			    series : [
                                			        {
                                			            name: '总交易量(单位/笔)',
                                			            type: 'line',
                                			            showAllSymbol: true,
                                			            data:xAxisData
                                			        },
                                			        {
                                			            name: '成功交易量(单位/笔)',
                                			            type: 'line',
                                			            showAllSymbol: true,
                                			            data:successData
                                			        },
                                			        {
                                			            name: '失败交易量(单位/笔)',
                                			            type: 'line',
                                			            showAllSymbol: true,
                                			            data:failData
                                			        }
                                			    ]
                                			};
                                      var myChart = echarts.init(document.getElementById('dayTrading'));
                                      myChart.setTheme(curTheme)
                                      myChart.setOption(option);
                                      window.onresize = myChart.resize;
                                  });
                              });
                    }
                });
        			
        		}
        		
        		$scope.queryTrading=function(){
        			Date.prototype.Format = function(fmt) 
        			{ //author: meizz 
        			      var o = { 
        			        "M+" : this.getMonth()+1,                 //月份 
        			        "d+" : this.getDate(),                    //日 
        			        "h+" : this.getHours(),                   //小时 
        			        "m+" : this.getMinutes(),                 //分 
        			        "s+" : this.getSeconds(),                 //秒 
        			        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
        			        "S"  : this.getMilliseconds()             //毫秒 
        			      }; 
        			      if(/(y+)/.test(fmt)) 
        			        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
        			      for(var k in o) 
        			        if(new RegExp("("+ k +")").test(fmt)) 
        			      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
        			      return fmt; 
        			    } 
        			
        		    if(!$scope.startdate) $scope.startdate = new Date(new Date().getFullYear(), new Date().getMonth(), 1).Format("yyyy-MM-dd");
        		    if(!$scope.enddate) $scope.enddate = new Date().Format("yyyy-MM-dd");
        		//交易量
        			$httpWrapper.post({
        			url:"tradingStatistic/getTradingStatisticByPageByCondition",
        			data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"tradingStartDate": "'+$scope.startdate+'","tradingEndDate": "'+$scope.enddate+'","type":"01"}}',
                    success:function(data){
                        require( [
                            'echarts',
                            'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                            'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                        ], function (echarts) {
                            require(['echarts/theme/macarons'], function(curTheme){
                            	var arrs = data.list;
                                var xAxisData=[];     //X轴数据
                                var totalNum=[];      //交易量数据
                                
                                for(var x in arrs){
                                	var arr = arrs[x];
                                	for(var key in arr){
                                    	if(key == "txName")
                                    		xAxisData.push(arr[key]);
                                    	if(key == "totalNum")
                                    		totalNum.push(arr[key]);
                                    }
                                }
                               
                                var option = {
                                		title : {
                                            text: '交易量TOP10'
                                        },
                                        tooltip : {
                                            trigger: 'axis'
                                        },
                                        legend: {
                                            data:['交易量(单位/笔)']
                                        },
                                        toolbox: {
                                            show : true,
                                            feature : {
                                                magicType : {show: true, type: ['line', 'bar']},
                                                restore : {show: true},
                                                saveAsImage : {show: true}
                                            }
                                        },
                                	    xAxis: {
                                	        type: 'category',
                                	        data: xAxisData,
                                	        axisLabel: {  
                                                interval: 0,  
                                                formatter:function(value)  
                                                {  
                                                    debugger  
                                                    var ret = "";//拼接加\n返回的类目项  
                                                    var maxLength = 5;//每项显示文字个数  
                                                    var valLength = value.length;//X轴类目项的文字个数  
                                                    var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                                                    if (rowN > 1)//如果类目项的文字大于3,  
                                                    {  
                                                        for (var i = 0; i < rowN; i++) {  
                                                            var temp = "";//每次截取的字符串  
                                                            var start = i * maxLength;//开始截取的位置  
                                                            var end = start + maxLength;//结束截取的位置  
                                                            //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                                            temp = value.substring(start, end) + "\n";  
                                                            ret += temp; //凭借最终的字符串  
                                                        }  
                                                        return ret;  
                                                    }  
                                                    else {  
                                                        return value;  
                                                    }  
                                                },
                                                textStyle: {
                                                    fontSize : 9      //更改坐标轴文字大小
                                                  }
                                            },
                                	        show:true
                                	    },
                                	    yAxis: {
                                	        type: 'value'
                                	    },
                                	    
                                	    series: [{
                                	    	name: '交易量(单位/笔)',
                                	        data: totalNum,
                                	        type: 'bar'
                                	    }]
                                	};
                                myChart3 = echarts.init(document.getElementById("total"));
                                myChart3.setTheme(curTheme)
                                myChart3.setOption(option);
                            });
                        });
                    }
                });
        			//平均消耗时间
        			$httpWrapper.post({
            			url:"tradingStatistic/getTradingStatisticByPageByCondition",
            			data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"tradingStartDate": "'+$scope.startdate+'","tradingEndDate": "'+$scope.enddate+'","type":"02"}}',
                        success:function(data){
                            require( [
                                'echarts',
                                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                            ], function (echarts) {
                                require(['echarts/theme/macarons'], function(curTheme){
                                	var arrs = data.list;
                                    var xAxisData=[];     //X轴数据
                                    var timeAvg=[];      //交易量数据
                                    
                                    for(var x in arrs){
                                    	var arr = arrs[x];
                                    	for(var key in arr){
                                        	if(key == "txName")
                                        		xAxisData.push(arr[key]);
                                        	if(key == "timeAvg")
                                        		timeAvg.push(arr[key]);
                                        }
                                    }
                                    
                                    var option = {
                                    		title : {
                                                text: '平均耗时TOP10'
                                            },
                                            tooltip : {
                                                trigger: 'axis'
                                            },
                                            legend: {
                                                data:['平均耗时(ms)']
                                            },
                                            toolbox: {
                                                show : true,
                                                feature : {
                                                    magicType : {show: true, type: ['line', 'bar']},
                                                    restore : {show: true},
                                                    saveAsImage : {show: true}
                                                }
                                            },
                                    	    xAxis: {
                                    	        type: 'category',
                                    	        data: xAxisData,
                                    	        axisLabel: {  
                                                    interval: 0,  
                                                    formatter:function(value)  
                                                    {  
                                                        debugger  
                                                        var ret = "";//拼接加\n返回的类目项  
                                                        var maxLength = 5;//每项显示文字个数  
                                                        var valLength = value.length;//X轴类目项的文字个数  
                                                        var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                                                        if (rowN > 1)//如果类目项的文字大于3,  
                                                        {  
                                                            for (var i = 0; i < rowN; i++) {  
                                                                var temp = "";//每次截取的字符串  
                                                                var start = i * maxLength;//开始截取的位置  
                                                                var end = start + maxLength;//结束截取的位置  
                                                                //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                                                temp = value.substring(start, end) + "\n";  
                                                                ret += temp; //凭借最终的字符串  
                                                            }  
                                                            return ret;  
                                                        }  
                                                        else {  
                                                            return value;  
                                                        }  
                                                    },
                                                    textStyle: {
                                                        fontSize : 9      //更改坐标轴文字大小
                                                      }
                                                },
                                    	        show:true
                                    	    },
                                    	    yAxis: {
                                    	        type: 'value'
                                    	    },
                                    	    series: [{
                                    	    	name: '平均耗时(ms)',
                                    	        data: timeAvg,
                                    	        type: 'bar'
                                    	    }]
                                    	};
                                    myChart4 = echarts.init(document.getElementById("time1"));
                                    myChart4.setTheme(curTheme)
                                    myChart4.setOption(option);
                                });
                            });
                        }
                    });
            		//成功次数
            		$httpWrapper.post({
            			url:"tradingStatistic/getTradingStatisticByPageByCondition",
            			data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"tradingStartDate": "'+$scope.startdate+'","tradingEndDate": "'+$scope.enddate+'","type":"03"}}',
                        success:function(data){
                            require( [
                                'echarts',
                                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                            ], function (echarts) {
                                require(['echarts/theme/macarons'], function(curTheme){
                                	var arrs = data.list;
                                    var xAxisData=[];     //交易码数据
                                    var success=[];      //成功次数
                                    
                                    for(var x in arrs){
                                    	var arr = arrs[x];
                                    	for(var key in arr){
                                        	if(key == "txName")
                                        		xAxisData.push(arr[key]);
                                        	if(key == "success")
                                        		success.push(arr[key]);
                                        }
                                    }
                                    var option = {
                                    		title : {
                                                text: '成功次数TOP10'
                                            },
                                            tooltip : {
                                                trigger: 'axis'
                                            },
                                            legend: {
                                                data:['成功次数(单位/笔)']
                                            },
                                            toolbox: {
                                                show : true,
                                                feature : {
                                                    magicType : {show: true, type: ['line', 'bar']},
                                                    restore : {show: true},
                                                    saveAsImage : {show: true}
                                                }
                                            },
                                    	    xAxis: {
                                    	        type: 'category',
                                    	        data: xAxisData,
                                    	        axisLabel: {  
                                                    interval: 0,  
                                                    formatter:function(value)  
                                                    {  
                                                        debugger  
                                                        var ret = "";//拼接加\n返回的类目项  
                                                        var maxLength = 5;//每项显示文字个数  
                                                        var valLength = value.length;//X轴类目项的文字个数  
                                                        var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                                                        if (rowN > 1)//如果类目项的文字大于3,  
                                                        {  
                                                            for (var i = 0; i < rowN; i++) {  
                                                                var temp = "";//每次截取的字符串  
                                                                var start = i * maxLength;//开始截取的位置  
                                                                var end = start + maxLength;//结束截取的位置  
                                                                //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                                                temp = value.substring(start, end) + "\n";  
                                                                ret += temp; //凭借最终的字符串  
                                                            }  
                                                            return ret;  
                                                        }  
                                                        else {  
                                                            return value;  
                                                        }  
                                                    },
                                                    textStyle: {
                                                        fontSize : 9      //更改坐标轴文字大小
                                                      }
                                                },
                                    	        show:true
                                    	    },
                                    	    yAxis: {
                                    	        type: 'value'
                                    	    },
                                    	    series: [{
                                    	    	name: '成功次数(单位/笔)',
                                    	        data: success,
                                    	        type: 'bar'
                                    	    }]
                                    	};
                                    myChart5 = echarts.init(document.getElementById('success'));
                                    myChart5.setTheme(curTheme)
                                    myChart5.setOption(option);
                                    
                                });
                            });
                        }
                    });
            		//失败次数
            		$httpWrapper.post({
            			url:"tradingStatistic/getTradingStatisticByPageByCondition",
            			data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"tradingStartDate": "'+$scope.startdate+'","tradingEndDate": "'+$scope.enddate+'","type":"04"}}',
                        success:function(data){
                            require( [
                                'echarts',
                                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                            ], function (echarts) {
                                require(['echarts/theme/macarons'], function(curTheme){
                                	var arrs = data.list;
                                    var xAxisData=[];     //交易码数据
                                    var fail=[];      //失败次数
                                    
                                    for(var x in arrs){
                                    	var arr = arrs[x];
                                    	for(var key in arr){
                                        	if(key == "txName")
                                        		xAxisData.push(arr[key]);
                                        	if(key == "fail")
                                        		fail.push(arr[key]);
                                        }
                                    }
                                    var option = {
                                    		title : {
                                                text: '失败次数TOP10'
                                            },
                                            tooltip : {
                                                trigger: 'axis'
                                            },
                                            legend: {
                                                data:['失败次数(单位/笔)']
                                            },
                                            toolbox: {
                                                show : true,
                                                feature : {
                                                    magicType : {show: true, type: ['line', 'bar']},
                                                    restore : {show: true},
                                                    saveAsImage : {show: true}
                                                }
                                            },
                                    	    xAxis: {
                                    	        type: 'category',
                                    	        data: xAxisData,
                                    	        axisLabel: {  
                                                    interval: 0,  
                                                    formatter:function(value)  
                                                    {  
                                                        debugger  
                                                        var ret = "";//拼接加\n返回的类目项  
                                                        var maxLength = 5;//每项显示文字个数  
                                                        var valLength = value.length;//X轴类目项的文字个数  
                                                        var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                                                        if (rowN > 1)//如果类目项的文字大于3,  
                                                        {  
                                                            for (var i = 0; i < rowN; i++) {  
                                                                var temp = "";//每次截取的字符串  
                                                                var start = i * maxLength;//开始截取的位置  
                                                                var end = start + maxLength;//结束截取的位置  
                                                                //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                                                temp = value.substring(start, end) + "\n";  
                                                                ret += temp; //凭借最终的字符串  
                                                            }  
                                                            return ret;  
                                                        }  
                                                        else {  
                                                            return value;  
                                                        }  
                                                    },
                                                    textStyle: {
                                                        fontSize : 9      //更改坐标轴文字大小
                                                      }
                                                },
                                    	        show:true
                                    	    },
                                    	    yAxis: {
                                    	        type: 'value'
                                    	    },
                                    	    series: [{
                                    	    	name: '失败次数(单位/笔)',
                                    	        data: fail,
                                    	        type: 'bar'
                                    	    }]
                                    	};
                                    myChart6 = echarts.init(document.getElementById('fail'));
                                    myChart6.setTheme(curTheme);
                                    myChart6.setOption(option);
                                   
                                });
                            });
                        }
                    });
            		//成功率
            		$httpWrapper.post({
            			url:"tradingStatistic/getTradingStatisticByPageByCondition",
            			data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"tradingStartDate": "'+$scope.startdate+'","tradingEndDate": "'+$scope.enddate+'","type":"05"}}',
                        success:function(data){
                            require( [
                                'echarts',
                                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                            ], function (echarts) {
                                require(['echarts/theme/macarons'], function(curTheme){
                                	var arrs = data.list;
                                    var xAxisData=[];     //交易码数据
                                    var successRate=[];      //成功率
                                    
                                    for(var x in arrs){
                                    	var arr = arrs[x];
                                    	for(var key in arr){
                                        	if(key == "txName")
                                        		xAxisData.push(arr[key]);
                                        	if(key == "totalNum")
                                        		var totalNum = arr[key];
                                        	if(key == "success")
                                        		successRate.push((arr[key]/totalNum).toFixed(4)*100);
                                        }
                                    }
                                    var option = {
                                    		title : {
                                                text: '成功率TOP10'
                                            },
                                            tooltip : {
                                                trigger: 'axis',
                                                formatter:'{c}%'
                                            },
                                            legend: {
                                                data:['成功率']
                                            },
                                            toolbox: {
                                                show : true,
                                                feature : {
                                                    magicType : {show: true, type: ['line', 'bar']},
                                                    restore : {show: true},
                                                    saveAsImage : {show: true}
                                                }
                                            },
                                    	    xAxis: {
                                    	        type: 'category',
                                    	        data: xAxisData,
                                    	        axisLabel: {  
                                                    interval: 0,  
                                                    formatter:function(value)  
                                                    {  
                                                        debugger  
                                                        var ret = "";//拼接加\n返回的类目项  
                                                        var maxLength = 5;//每项显示文字个数  
                                                        var valLength = value.length;//X轴类目项的文字个数  
                                                        var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                                                        if (rowN > 1)//如果类目项的文字大于3,  
                                                        {  
                                                            for (var i = 0; i < rowN; i++) {  
                                                                var temp = "";//每次截取的字符串  
                                                                var start = i * maxLength;//开始截取的位置  
                                                                var end = start + maxLength;//结束截取的位置  
                                                                //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                                                temp = value.substring(start, end) + "\n";  
                                                                ret += temp; //凭借最终的字符串  
                                                            }  
                                                            return ret;  
                                                        }  
                                                        else {  
                                                            return value;  
                                                        }  
                                                    },
                                                    textStyle: {
                                                        fontSize : 9      //更改坐标轴文字大小
                                                      }
                                                },
                                    	        show:true
                                    	    },
                                    	    yAxis: {
                                    	        type: 'value',
                                    	        axisLabel: {  
                                                    show: true,  
                                                    interval: 'auto',  
                                                    formatter: '{value} %'  
                                                    }
                                    	
                                    	    },
                                    	    series: [{
                                    	    	name: '成功率',
                                    	        data: successRate,
                                    	        label: {
            	        	                        show: true,
            	        	                        position: 'top',
            	        	                        formatter: '{b}\n{c}%'
                    	        	                     },
                                    	        type: 'bar'
                                    	    }]
                                    	};
                                    myChart7 = echarts.init(document.getElementById('successRate'));
                                    myChart7.setTheme(curTheme)
                                    myChart7.setOption(option);
                                    
                                });
                            });
                        }
                    });
            		//失败率
            		$httpWrapper.post({
            			url:"tradingStatistic/getTradingStatisticByPageByCondition",
            			data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"tradingStartDate": "'+$scope.startdate+'","tradingEndDate": "'+$scope.enddate+'","type":"06"}}',
                        success:function(data){
                            require( [
                                'echarts',
                                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                            ], function (echarts) {
                                require(['echarts/theme/macarons'], function(curTheme){
                                	var arrs = data.list;
                                    var xAxisData=[];     //交易码数据
                                    var failRate=[];      //失败率
                                    
                                    for(var x in arrs){
                                    	var arr = arrs[x];
                                    	for(var key in arr){
                                        	if(key == "txName")
                                        		xAxisData.push(arr[key]);
                                        	if(key == "totalNum")
                                        		var totalNum = arr[key];
                                        	if(key == "fail")
                                        		failRate.push((arr[key]/totalNum).toFixed(4)*100);
                                        }
                                    }
                                    var option = {
                                    		title : {
                                                text: '失败率TOP10'
                                            },
                                            tooltip : {
                                                trigger: 'axis',
                                                formatter:'{c}%'
                                            },
                                            legend: {
                                                data:['失败率']
                                            },
                                            toolbox: {
                                                show : true,
                                                feature : {
                                                    magicType : {show: true, type: ['line', 'bar']},
                                                    restore : {show: true},
                                                    saveAsImage : {show: true}
                                                }
                                            },
                                    	    xAxis: {
                                    	        type: 'category',
                                    	        data: xAxisData,
                                    	        axisLabel: {  
                                                    interval: 0,  
                                                    formatter:function(value)  
                                                    {  
                                                        debugger  
                                                        var ret = "";//拼接加\n返回的类目项  
                                                        var maxLength = 5;//每项显示文字个数  
                                                        var valLength = value.length;//X轴类目项的文字个数  
                                                        var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                                                        if (rowN > 1)//如果类目项的文字大于3,  
                                                        {  
                                                            for (var i = 0; i < rowN; i++) {  
                                                                var temp = "";//每次截取的字符串  
                                                                var start = i * maxLength;//开始截取的位置  
                                                                var end = start + maxLength;//结束截取的位置  
                                                                //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                                                temp = value.substring(start, end) + "\n";  
                                                                ret += temp; //凭借最终的字符串  
                                                            }  
                                                            return ret;  
                                                        }  
                                                        else {  
                                                            return value;  
                                                        }  
                                                    },
                                                    textStyle: {
                                                        fontSize : 9      //更改坐标轴文字大小
                                                      }
                                                },
                                    	        show:true
                                    	    },
                                    	    yAxis: {
                                    	        type: 'value',
                                    	        axisLabel: {  
                                                    show: true,  
                                                    interval: 'auto',  
                                                    formatter: '{value} %'  
                                                    }
                                    	    },
                                    	    series: [{
                                    	    	name: '失败率',
                                    	        data: failRate,
                                    	        label: {
            	        	                        show: true,
            	        	                        position: 'top',
            	        	                        formatter: '{b}\n{c}%'
                    	        	                     },
                                    	        type: 'bar'
                                    	    }]
                                    	};
                                    myChart8 = echarts.init(document.getElementById('failRate'));
                                    myChart8.setTheme(curTheme);
                                    myChart8.setOption(option);
                                   
                                });
                            });
                        }
                    });
            		$(window).resize(function(){
            			myChart3.resize();	
                    	myChart4.resize();
//                    	myChart5.resize();
//                    	myChart6.resize();
                    	myChart7.resize();
                    	myChart8.resize();
            		});
            		window.onresize =function(){
                    	myChart3.resize();	
                    	myChart4.resize();
//                    	myChart5.resize();
//                    	myChart6.resize();
                    	myChart7.resize();
                    	myChart8.resize();
                    	
            		}
        		}      
        	    break; 
        	}
            case 'apps':{
                $httpWrapper.post({
                    url:"loadAppsType.htm",
                    success:function(data){
                        require( [
                            'echarts',
                            'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
                            'echarts/chart/funnel' // 使用柱状图就加载bar模块，按需加载
                        ], function (echarts) {
                            require(['echarts/theme/shine'], function(curTheme){
                                var option =statistics._generatePieOption({'P':data[0],'C':data[1],'P.AND.C':data[2]},'应用类型分布图','应用类型');
                                myChart1 = echarts.init(document.getElementById('statisticsAppsTypes'));
                                myChart1.setTheme(curTheme);
                                myChart1.setOption(option);
                                
                            });
                        });
                    }
                });
                $httpWrapper.post({
                    url:"loadServiceProtocols.htm",
                    success: function (data) {
                        require( [
                            'echarts',
                            'echarts/chart/pie',// 使用柱状图就加载bar模块，按需加载
                            'echarts/chart/funnel' // 使用柱状图就加载bar模块，按需加载
                        ], function (echarts) {
                            require(['echarts/theme/blue'], function(curTheme){
                                var option =statistics._generatePieOption(data,'暴露协议分布图','暴露协议');
                                myChart2 = echarts.init(document.getElementById('statisticsServiceProtocol'));
                                myChart2.setTheme(curTheme);
                                myChart2.setOption(option);
                                
                            });
                        });

                    }
                });
                window.onresize =function(){
                	myChart1.resize();
                	myChart2.resize();
                } 
                break;
            }
            case 'nodes':{
                $httpWrapper.post({
                    url:"loadAppNodes.htm",
                    success:function(data){
                        require( [
                            'echarts',
                            'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                            'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                        ], function (echarts) {
                            require(['echarts/theme/macarons'], function(curTheme){
                                var xAxisData=[];
                                var nodes=[];
                                for(var key in data){
                                    xAxisData.push(key);
                                    nodes.push(data[key]);
                                }
                                var option = {
                                    title : {
                                        text: '应用部署节点统计',
                                        subtext: '来自注册中心'
                                    },
                                    tooltip : {
                                        trigger: 'axis'
                                    },
                                    legend: {
                                        data:['部署节点数']
                                    },
                                    toolbox: {
                                        show : true,
                                        feature : {
                                            magicType : {show: true, type: ['line', 'bar']},
                                            restore : {show: true},
                                            saveAsImage : {show: true}
                                        }
                                    },
                                    calculable : true,
                                    xAxis : [
                                        {
                                            type : 'category',
                                            data : xAxisData,
                                            show:true
                                        }
                                    ],
                                    yAxis : [
                                        {
                                            type : 'value'
                                        }
                                    ],
                                    series : [
                                        {
                                            name:'部署节点数',
                                            type:'bar',
                                            data:nodes
                                        }
                                    ]
                                };
                                var myChart = echarts.init(document.getElementById('nodes'));
                                myChart.setTheme(curTheme)
                                myChart.setOption(option);
                                window.onresize = myChart.resize;
                                var ecConfig = require('echarts/config');
                                myChart.on(ecConfig.EVENT.CLICK, function (params) {
                                    location.hash="#/admin/"+params.name+"/nodes";
                                });
                            });
                        });
                    }
                });
                break;
            }
            case 'service':{
                $httpWrapper.post({
                    url:"loadAppServices.htm",
                    success:function(data){
                        require( [
                            'echarts',
                            'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                            'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                        ], function (echarts) {
                            require(['echarts/theme/macarons'], function(curTheme){
                                var xAxisData=[];
                                var provides=[];
                                var consumes=[];
                                for(var key in data){
                                    xAxisData.push(key);
                                    provides.push(data[key][0]);
                                    consumes.push(data[key][1]);
                                }
                                var option = {
                                    title : {
                                        text: '应用发布服务/订阅服务统计',
                                        subtext: '来自注册中心'
                                    },
                                    tooltip : {
                                        trigger: 'axis'
                                    },
                                    legend: {
                                        data:['发布的服务','订阅的服务']
                                    },
                                    toolbox: {
                                        show : true,
                                        feature : {
                                            magicType : {show: true, type: ['line', 'bar']},
                                            restore : {show: true},
                                            saveAsImage : {show: true}
                                        }
                                    },
                                    calculable : true,
                                    xAxis : [
                                        {
                                            type : 'category',
                                            data : xAxisData,
                                            show:true
                                        }
                                    ],
                                    yAxis : [
                                        {
                                            type : 'value'
                                        }
                                    ],
                                    series : [
                                        {
                                            name:'发布的服务',
                                            type:'bar',
                                            data:provides
                                        },
                                        {
                                            name:'订阅的服务',
                                            type:'bar',
                                            data:consumes
                                        }
                                    ]
                                };
                                var myChart = echarts.init(document.getElementById('serviceStatus'));
                                myChart.setTheme(curTheme)
                                myChart.setOption(option);
                                window.onresize = myChart.resize;
                                var ecConfig = require('echarts/config');
                                myChart.on(ecConfig.EVENT.CLICK, function (params) {
                                    if(params.seriesIndex==0){
                                        location.hash="#/admin/"+params.name+"/provides";
                                    }else{
                                        location.hash="#/admin/"+params.name+"/consumes";
                                    }
                                })
                            });
                        });
                    }
                });
                break;
            }
            case 'dependencies':{ 
                $httpWrapper.post({
                url:"loadAppsDependencies.htm",
                success:function(data){
                     /*var nodeSize = 20;
                     var graph={};
                     var nodes=[];
                     for(var i=0;i<nodeSize;i++){
                        var node={};
                         node.name="node"+i;
                         node.category=parseInt(Math.random()*2);
                         nodes.push(node);
                     }
                     var edges=[];
                    for(var i=0;i<nodeSize;i++){
                        var size = Math.random()*10;
                        for(var j=0;j<size;j++){
                            var edge={};
                            edge.source=nodes[i].name;
                            edge.target="node"+parseInt(Math.random()*nodeSize);
                            edges.push(edge);
                        }
                    }*/

                    $scope.graph=data;
                }
            });
                break;
            }
        }
    }
    $scope.switchTab('tradingStatistic');

});
