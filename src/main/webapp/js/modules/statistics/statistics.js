var statistics = angular.module("statistics",['ngRoute']);

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
        		var getMaxtotalNum=function (arr) {
                    var max
                    for (var i = 0; i < arr.length; i++) {
                      for (var j = i; j < arr.length; j++) {
                        if (arr[i] < arr[j]) {
                          max = arr[j];
                          arr[j] = arr[i];
                          arr[i] = max;
                        }
                      }
                    }
                    return arr;
                  }
        		var myDate = new Date();
        		var nowDate = myDate.toLocaleDateString();
        		//交易量
        		$httpWrapper.post({
        			url:"tradingStatistic/getTradingStatisticByPageByCondition",
        			data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"nowTime": "'+nowDate+'"}}',
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
                                    	if(key == "txCode")
                                    		xAxisData.push(arr[key]);
                                    	if(key == "totalNum")
                                    		totalNum.push(arr[key]);
                                    }
                                }
                                
                                getMaxtotalNum(totalNum);
                                var option = {
                                		title : {
                                            text: '交易量TOP10'
                                        },
                                        tooltip : {
                                            trigger: 'axis'
                                        },
                                        legend: {
                                            data:['交易量']
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
                                	        show:true
                                	    },
                                	    yAxis: {
                                	        type: 'value'
                                	    },
                                	    series: [{
                                	    	name: '交易量',
                                	        data: totalNum,
                                	        type: 'bar'
                                	    }]
                                	};
                                var myChart = echarts.init(document.getElementById('total'));
                                myChart.setTheme(curTheme)
                                myChart.setOption(option);
                            });
                        });
                    }
                });
        		//平均消耗时间
        		$httpWrapper.post({
        			url:"tradingStatistic/getTradingStatisticByPageByCondition",
        			data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"nowTime": "'+nowDate+'"}}',
                    success:function(data){
                        require( [
                            'echarts',
                            'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                            'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                        ], function (echarts) {
                            require(['echarts/theme/macarons'], function(curTheme){
                            	var arrs = data.list;
                                var xAxisData=[];     //交易码数据
                                var timeAvg=[];      //平均消耗时间数据
                                
                                for(var x in arrs){
                                	var arr = arrs[x];
                                	for(var key in arr){
                                    	if(key == "txCode")
                                    		xAxisData.push(arr[key]);
                                    	if(key == "timeAvg")
                                    		timeAvg.push(arr[key]);
                                    }
                                }
                                getMaxtotalNum(timeAvg);
                                var option = {
                                		title : {
                                            text: '平均消耗时间TOP10'
                                        },
                                        tooltip : {
                                            trigger: 'axis'
                                        },
                                        legend: {
                                            data:['平均消耗时间']
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
                                	        show:true
                                	    },
                                	    yAxis: {
                                	        type: 'value'
                                	    },
                                	    series: [{
                                	    	name: '平均消耗时间',
                                	        data: timeAvg,
                                	        type: 'bar'
                                	    }]
                                	};
                                var myChart = echarts.init(document.getElementById('time'));
                                myChart.setTheme(curTheme)
                                myChart.setOption(option);
                            });
                        });
                    }
                });
        		//成功次数
        		$httpWrapper.post({
        			url:"tradingStatistic/getTradingStatisticByPageByCondition",
        			data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"nowTime": "'+nowDate+'"}}',
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
                                    	if(key == "txCode")
                                    		xAxisData.push(arr[key]);
                                    	if(key == "success")
                                    		success.push(arr[key]);
                                    }
                                }
                                getMaxtotalNum(success);
                                var option = {
                                		title : {
                                            text: '成功次数TOP10'
                                        },
                                        tooltip : {
                                            trigger: 'axis'
                                        },
                                        legend: {
                                            data:['成功次数']
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
                                	        show:true
                                	    },
                                	    yAxis: {
                                	        type: 'value'
                                	    },
                                	    series: [{
                                	    	name: '成功次数',
                                	        data: success,
                                	        type: 'bar'
                                	    }]
                                	};
                                var myChart = echarts.init(document.getElementById('success'));
                                myChart.setTheme(curTheme)
                                myChart.setOption(option);
                            });
                        });
                    }
                });
        		//失败次数
        		$httpWrapper.post({
        			url:"tradingStatistic/getTradingStatisticByPageByCondition",
        			data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"nowTime": "'+nowDate+'"}}',
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
                                    	if(key == "txCode")
                                    		xAxisData.push(arr[key]);
                                    	if(key == "fail")
                                    		fail.push(arr[key]);
                                    }
                                }
                                getMaxtotalNum(fail);
                                var option = {
                                		title : {
                                            text: '失败次数TOP10'
                                        },
                                        tooltip : {
                                            trigger: 'axis'
                                        },
                                        legend: {
                                            data:['失败次数']
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
                                	        show:true
                                	    },
                                	    yAxis: {
                                	        type: 'value'
                                	    },
                                	    series: [{
                                	    	name: '失败次数',
                                	        data: fail,
                                	        type: 'bar'
                                	    }]
                                	};
                                var myChart = echarts.init(document.getElementById('fail'));
                                myChart.setTheme(curTheme)
                                myChart.setOption(option);
                            });
                        });
                    }
                });
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
                                var myChart = echarts.init(document.getElementById('statisticsAppsTypes'));
                                myChart.setTheme(curTheme);
                                myChart.setOption(option);
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
                                var myChart = echarts.init(document.getElementById('statisticsServiceProtocol'));
                                myChart.setTheme(curTheme);
                                myChart.setOption(option);
                            });
                        });

                    }
                });
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
                                            show:false
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
                                            show:false
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
