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
   }).when("/alarm/detailsBussiness",{
    	templateUrl:"templates/alarm/detailsBusiness.html",
    	controller:"detailsBusinessTable"
    });
});
alarm.controller("alarmListTable",function($scope,$breadcrumb,$httpWrapper,$queryFilter,$menu){
    $breadcrumb.pushCrumb("服务告警","查看服务告警列表","alarm/alarmlist");
    $scope.alarms=[];
    $scope.isEmpty=false;
    $httpWrapper.post({
        url:"bizwarning/getBizWarningByPageByCondition",
        data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"bizStartDate": "'+$scope.startdate+'","bizEndDate": "'+$scope.enddate+'"}}',
        success:function(data){
            $scope.alarms=data.list;
            if(!data||data.length<0){
                $scope.isEmpty=true;
            }
            $scope.originData=data;
        }
    });
    $scope.query={};
    $scope.filter=function(){
        var filterResult=[];
        if($scope.isEmpty){
            return ;
        }
        $scope.alarms=$queryFilter($scope.originData,$scope.query);
    }
    
    $scope.queryBizWarning=function(){
    	$httpWrapper.post({
            url:"bizwarning/getBizWarningByPageByCondition",
            data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"bizStartDate": "'+$scope.startdate+'","bizEndDate": "'+$scope.enddate+'"}}',
            success:function(data){
                $scope.alarms=data.list;
                if(!data||data.length<0){
                    $scope.isEmpty=true;
                }
                $scope.originData=data;
            }
        });
    }
});



alarm.controller("alarmBusinessTable",function($scope,$breadcrumb,$httpWrapper,$queryFilter,$menu){
    $breadcrumb.pushCrumb("业务告警","查看业务告警列表","alarm/alarmBusiness");
    $scope.services=[];
    $scope.isEmpty=false;
    $httpWrapper.post({
        url:"servicewarning/getServiceWarningByPageByCondition",
        data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"serviceStartDate": "'+$scope.startdate+'","serviceEndDate": "'+$scope.enddate+'"}}',
        success:function(data){
            $scope.services=data.list;
            if(!data||data.length<0){
                $scope.isEmpty=true;
            }
            $scope.originData=data;
        }
    });
    $scope.query={};
    $scope.filter=function(){
        var filterResult=[];
        if($scope.isEmpty){
            return ;
        }
        $scope.services=$queryFilter($scope.originData,$scope.query);
    }
    $scope.queryServiceWarning=function(){
    	$httpWrapper.post({
            url:"servicewarning/getServiceWarningByPageByCondition",
            data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"serviceStartDate": "'+$scope.startdate+'","serviceEndDate": "'+$scope.enddate+'"}}',
            success:function(data){
                $scope.services=data.list;
                if(!data||data.length<0){
                    $scope.isEmpty=true;
                }
                $scope.originData=data;
            }
        });
    }
    
});
alarm.controller("detailsBusinessTable",function($scope,$breadcrumb,$httpWrapper,$compile){
    $breadcrumb.pushCrumb("业务告警详情");
    $scope.open=function(){
    	
    };
});

alarm.controller("alarmSetTable",function($scope,$breadcrumb){
    $breadcrumb.pushCrumb("通知设置","进入通知设置","alarm/alarmSet");
});
