var alarmset=angular.module("alarmset",['ngRoute']);
alarmset.config(function($routeProvider){
    $routeProvider.when("/alarmset",{
        templateUrl:"templates/alarm/alarm-set.html",
        controller:"alarmset"
    });
});
alarmset.controller("alarmset",function($scope,$httpWrapper,$breadcrumb){
		$breadcrumb.pushCrumb("Home","首页","alarmset");
	    $menu.switchMenu(menu.HOME);
	    $scope.currentTab='setMail';
	    $scope.switchTab=function(tabName){
        $scope.currentTab=tabName;
        switch (tabName){
        case 'setMail':{
        	$httpWrapper.post({
                url:"/setMail.htm",
                success:function(data){
                            var myChart = echarts.init(document.getElementById('setMail'));
                            myChart.setTheme(curTheme)
                            var ecConfig = require('echarts/config');
                            myChart.on(ecConfig.EVENT.CLICK, function (params) {
                                location.hash="#/alarm/"+params.name+"/setMail";
                            });
                		}
                    });
            break;
        	}
        case 'setPhone':{
        	$httpWrapper.post({
                url:"/setPhone.htm",
                success:function(data){
                            var myChart = echarts.init(document.getElementById('setPhone'));
                            myChart.setTheme(curTheme)
                            var ecConfig = require('echarts/config');
                            myChart.on(ecConfig.EVENT.CLICK, function (params) {
                                location.hash="#/alarm/"+params.name+"/setPhone";
                            });
                		}
                    });
            break;
        	}	
        case 'setqq':{
        	$httpWrapper.post({
                url:"/setqq.htm",
                success:function(data){
                    
                            var myChart = echarts.init(document.getElementById('setqq'));
                            myChart.setTheme(curTheme)
                            var ecConfig = require('echarts/config');
                            myChart.on(ecConfig.EVENT.CLICK, function (params) {
                                location.hash="#/alarm/"+params.name+"/setqq";
                            });
                		}
                    });
            
            break;
        	}	
        $scope.switchTab('setMail');
        }
     }
});
