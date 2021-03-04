var tracking=angular.module('tracking',['ngAnimate','ngRoute','serviceProvider']);
tracking.config(function($routeProvider){
	    $routeProvider.when("/tracking/text", {
        templateUrl:"templates/tracking/tracking.html",
        controller:"trackingTable"
		})
});
tracking.controller("trackingTable",function($scope){
    
      
});