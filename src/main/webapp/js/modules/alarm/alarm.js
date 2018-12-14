var alarm=angular.module("alarm",['ngAnimate','ngRoute','serviceProvider','queryFilter','breadCrumb','wui.date']);
alarm.config(function($routeProvider){
    $routeProvider.when("/alarm/alarmlist",{
        templateUrl:"templates/alarm/alarm-list.html",
        controller:"alarmListTable"
    }).when("/alarm/alarmSet", {
        templateUrl:"templates/alarm/alarm-set.html",
        controller:"alarmSetTable"
    }).when("/alarm/alarmBusiness", {
        templateUrl:"templates/alarm/alarmBusiness.html",
        controller:"alarmBusinessTable"
    });
});
alarm.controller("alarmListTable",function($scope,$breadcrumb,$httpWrapper){
    $breadcrumb.pushCrumb("服务告警","查看服务告警列表","alarm/alarmlist");

});

alarm.controller("alarmBusinessTable",function($scope,$breadcrumb){
    $breadcrumb.pushCrumb("业务告警","查看业务告警列表","alarm/alarmBusiness");
});

alarm.controller("alarmSetTable",function($scope,$breadcrumb){
    $breadcrumb.pushCrumb("通知设置","进入通知设置","alarm/alarmSet");
});