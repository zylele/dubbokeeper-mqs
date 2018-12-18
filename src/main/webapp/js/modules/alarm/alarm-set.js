var alarmset=angular.module("alarmset",['ngRoute']);
alarmset.config(function($routeProvider){
    $routeProvider.when("/alarmset",{
        templateUrl:"templates/alarm/alarm-set.html",
        controller:"alarmset"
    });
});
alarmset.controller("alarmset",function($scope,$httpWrapper,$breadcrumb,$menu){

    $breadcrumb.pushCrumb("Home","首页","alarmset");
    $menu.switchMenu(menu.HOME);
    $scope.currentTab='setMail';
    $scope.switchTab=function(tabName){
        $scope.currentTab=tabName;
        switch (tabName){
            case 'setMail':{
            	  var myChart = echarts.init(document.getElementById('SetMail'));
                  myChart.setTheme(curTheme);
                  myChart.setOption(option);
                break;
            }
            case 'setPhone':{
            	var myChart = echarts.init(document.getElementById('SetPhone'));
                myChart.setTheme(curTheme);
                myChart.setOption(option);
                break;
            }
            case 'setqq':{
            	var myChart = echarts.init(document.getElementById('Setqq'));
                myChart.setTheme(curTheme);
                myChart.setOption(option);
                break;
            }
            
    $scope.switchTab('setMail');
        }
    }
			
                       
});
