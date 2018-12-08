var alarmset=angular.module("alarmset",['ngRoute']);
alarmset.config(function($routeProvider){
    $routeProvider.when("/alarm/SetMail",{
        templateUrl:"templates/alarm/alarm-set-mail.html",
        controller:"alarmSetMail"
    }).when("/alarm/SetPhone",{
    	templateUrl:"templates/alarm/alarm-set-phone.html",
    	controller:"alarmSetPhone"
    }).when("/alarm/Setqq",{
    	templateUrl:"templates/alarm/alarm-set-qq.html",
    	controller:"alarmSetQq"
    });
});
alarmset.controller("alarmSetMail",function($scope,$httpWrapper){
	$httpWrapper.get({
        url:"templates/alarm/alarm-set-mail.html",
        success:function(htmlx){
            var converter = new showdown.Converter();
            var html = converter.makeHtml(Mustache.render(htmlx, {}));
            $(".alarmset").html(html);
        }
    });
});
alarmset.controller("alarmSetPhone",function($scope,$httpWrapper){
	$scope.$route = $route;
});
alarmset.controller("alarmSetQq",function($scope,$httpWrapper){
	$scope.$route = $route;
});