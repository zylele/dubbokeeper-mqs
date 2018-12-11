var alarm=angular.module("alarm",['ngAnimate','ngRoute','serviceProvider','queryFilter','breadCrumb']);
alarm.config(function($routeProvider){
    $routeProvider.when("/alarm/alarmlist",{
        templateUrl:"templates/alarm/alarm-list.html",
        controller:"alarmListTable"
    }).when("/alarm/alarmSet", {
        templateUrl:"templates/alarm/alarm-set.html",
        controller:"alarmSetTable"
    });
});
alarm.controller("alarmListTable",function($scope,$breadcrumb,$httpWrapper){
    $breadcrumb.pushCrumb("告警列表","查看告警列表","alarm/alarmlist");
    
});

alarm.controller("alarmSetTable",function($scope,$breadcrumb){
    $breadcrumb.pushCrumb("通知设置","进入通知设置","alarm/alarmSet");
});