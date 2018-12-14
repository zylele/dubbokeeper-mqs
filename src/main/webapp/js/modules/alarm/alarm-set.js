var alarmset=angular.module("alarmset",['ngRoute']);
alarmset.config(function($routeProvider){
    $routeProvider.when("/alarmset",{
        templateUrl:"templates/alarm/alarm-set.html",
        controller:"alarmset"
    });
});
alarmset.controller("alarmset",function($scope,$httpWrapper,$breadcrumb){
			
                       
});
