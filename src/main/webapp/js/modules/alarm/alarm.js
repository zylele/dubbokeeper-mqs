var alarm=angular.module("alarm",['ngAnimate','ngRoute','serviceProvider','queryFilter','breadCrumb','wui.date','mePagination']);
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
alarm.controller("alarmListTable",function($scope,$breadcrumb,$httpWrapper,$queryFilter){
    $breadcrumb.pushCrumb("服务告警","查看服务告警列表","alarm/alarmlist");
    $scope.alarms=[];
    $scope.isEmpty=false;
    /*$httpWrapper.post({
        url:"bizwarning/getBizWarningByPageByCondition",
        data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"bizStartDate": "'+$scope.startdate+'","bizEndDate": "'+$scope.enddate+'"}}',
        success:function(data){
            $scope.alarms=data.list;
            if(!data||data.length<0){
                $scope.isEmpty=true;
            }
            $scope.originData=data;
        }
    });*/
    $scope.query={};
    $scope.filter=function(){
        var filterResult=[];
        if($scope.isEmpty){
            return ;
        }
        $scope.alarms=$queryFilter($scope.originData,$scope.query);
    }
    
    $scope.queryBizWarning=function(){
    	if($scope.myPage.currentPage===0){
    		$scope.myPage.currentPage = 1;
    	}
    	$httpWrapper.post({
            url:"bizwarning/getBizWarningByPageByCondition",
            data:'{"currentPage": {"currentPage": '+$scope.myPage.currentPage+',"pageSize":'+$scope.myPage.itemsPerPage+'},"conditions": {"bizStartDate": "'+$scope.startdate+'","bizEndDate": "'+$scope.enddate+'"}}',
            success:function(data){
                $scope.alarms=data.list;
                if(!data||data.length<0){
                    $scope.isEmpty=true;
                }
                $scope.myPage.totalItems=data.totalCount;
                $scope.originData=data;
            }
        });
    } 
    $scope.myPage={
      		currentPage:1,//访问第几页数据，从1开始
      		totalItems:0,//数据库中总共有多少条数据
      		itemsPerPage: 10,//默认每页展示多少条数据，可更改
      		pagesLength: 15,
      		perPageOptions: [10, 20, 30, 40, 50, 60]//可选择的每页展示多少条数据
  	  };

	//监测当页码。总数据，每页展示数据个数变化时，重新加载数据
   	$scope.$watch(function (){ 
       		return $scope.myPage.itemsPerPage+' '+$scope.myPage.currentPage+' '+$scope.myPage.totalItems;
   	},getList);

	function getList(){
		//获取列表需要时，将页码重置为1
      	//$scope.myPage.currentPage=myPage.pageNub;
      	$scope.queryBizWarning();
	}

});
alarm.controller("alarmBusinessTable",function($scope,$breadcrumb,$httpWrapper,$queryFilter){
    $breadcrumb.pushCrumb("业务告警","查看业务告警列表","alarm/alarmBusiness");
    $scope.services=[];
    $scope.isEmpty=false;
//    $httpWrapper.post({
//        url:"servicewarning/getServiceWarningByPageByCondition",
//        data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"serviceStartDate": "'+$scope.startdate+'","serviceEndDate": "'+$scope.enddate+'"}}',
//        success:function(data){
//            $scope.services=data.list;
//            if(!data||data.length<0){
//                $scope.isEmpty=true;
//            }
//            $scope.originData=data;
//        }
//    });
    $scope.query={};
    $scope.filter=function(){
        var filterResult=[];
        if($scope.isEmpty){
            return ;
        }
        $scope.services=$queryFilter($scope.originData,$scope.query);
    }
    $scope.queryServiceWarning=function(){
    	if($scope.myPage.currentPage===0){
    		$scope.myPage.currentPage = 1;
    	}
    	$httpWrapper.post({
            url:"servicewarning/getServiceWarningByPageByCondition",
            data:'{"currentPage": {"currentPage": '+$scope.myPage.currentPage+',"pageSize":'+$scope.myPage.itemsPerPage+'},"conditions": {"serviceStartDate": "'+$scope.startdate+'","serviceEndDate": "'+$scope.enddate+'"}}',
            success:function(data){
                $scope.services=data.list;
                if(!data||data.length<0){
                    $scope.isEmpty=true;
                }
                $scope.myPage.totalItems=data.totalCount;
                $scope.originData=data;
            }
        });
    }
    $scope.myPage={
      		currentPage:1,//访问第几页数据，从1开始
      		totalItems:0,//数据库中总共有多少条数据
      		itemsPerPage: 10,//默认每页展示多少条数据，可更改
      		pagesLength: 15,
      		perPageOptions: [10, 20, 30, 40, 50, 60]//可选择的每页展示多少条数据
  	  };

	//监测当页码。总数据，每页展示数据个数变化时，重新加载数据
    $scope.$watch(function (){ 
   		return $scope.myPage.itemsPerPage+' '+$scope.myPage.currentPage+' '+$scope.myPage.totalItems;
	},getList);

    function getList(){
		//获取列表需要时，将页码重置为1
      	//$scope.myPage.currentPage=myPage.pageNub;
      	$scope.queryServiceWarning();
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
