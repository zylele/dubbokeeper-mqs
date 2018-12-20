var alarm=angular.module("alarm",['ngAnimate','ngRoute','serviceProvider','queryFilter','breadCrumb','wui.date','mePagination','ui.bootstrap','details','alarmset']);
alarm.config(function($routeProvider){
    $routeProvider.when("/alarm/alarmlist",{
        templateUrl:"templates/alarm/alarm-list.html",
        controller:"alarmListTable"
    }).when("/alarm/alarmSet", {
        templateUrl:"templates/alarm/alarm-set.html",
        controller:"alarmset"
    }).when("/alarm/alarmBusiness", {
        templateUrl:"templates/alarm/alarmBusiness.html",
        controller:"alarmBusinessTable"
   });
});
alarm.controller("alarmListTable",function($scope,$breadcrumb,$httpWrapper,$queryFilter,$modal,$menu,$routeParams,$rootScope){
	$menu.switchMenu("alarm/alarmlist");
	$breadcrumb.pushCrumb("服务异常","查看服务异常列表","alarm/alarmlist");
	$scope.application=$routeParams.application;
    $scope.services=[];
    $scope.isEmpty=false;
    $rootScope.warnStatus.bizStatus = false;
    $rootScope.warnStatus.serviceStatus = false;
    $scope.myPage={
      		currentPage:1,
      		totalItems:0,
      		itemsPerPage: 10,
      		pagesLength: 15,
      		perPageOptions: [10, 20, 30, 40, 50, 60]
  	  };
    Date.prototype.Format = function(fmt) 
    { //author: meizz 
      var o = { 
        "M+" : this.getMonth()+1,                 //月份 
        "d+" : this.getDate(),                    //日 
        "h+" : this.getHours(),                   //小时 
        "m+" : this.getMinutes(),                 //分 
        "s+" : this.getSeconds(),                 //秒 
        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
        "S"  : this.getMilliseconds()             //毫秒 
      }; 
      if(/(y+)/.test(fmt)) 
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
      for(var k in o) 
        if(new RegExp("("+ k +")").test(fmt)) 
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
      return fmt; 
    }
    if(!$scope.startdate) $scope.startdate = new Date().Format("yyyy-MM-dd")+' 00:00:00';
    if(!$scope.enddate) $scope.enddate = new Date().Format("yyyy-MM-dd hh:mm:ss");
    
    $httpWrapper.post({
        url:"servicewarning/getServiceWarningByPageByCondition",
        data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"serviceStartDate": "'+$scope.startdate+'","serviceEndDate": "'+$scope.enddate+'"}}',
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
    
   	$scope.$watch(function (){ 
       		return $scope.myPage.itemsPerPage+' '+$scope.myPage.currentPage+' '+$scope.myPage.totalItems;
   	},getList);

	function getList(){
      	$scope.queryServiceWarning();
	} 
	
    $scope.openModal = function(traceContent) {
            var modalInstance = $modal.open({
                templateUrl : 'templates/alarm/detailsBussiness.html',//script标签中定义的id
                controller : 'modalCtrl',//modal对应的Controller
                resolve : {
                    data : function() {//data作为modal的controller传入的参数
                         return traceContent;//用于传递数据
                    }
                }
            })
        }

});





alarm.controller("alarmBusinessTable",function($scope,$breadcrumb,$httpWrapper,$queryFilter,$modal,$menu,$routeParams,$rootScope){
	$menu.switchMenu("alarm/alarmBusiness");
	$breadcrumb.pushCrumb("业务异常","查看业务异常列表","alarm/alarmBusiness");
	$scope.application=$routeParams.application;
    $scope.alarms=[];
    $scope.isEmpty=false;
    $scope.myPage={
      		currentPage:1,
      		totalItems:0,
      		itemsPerPage: 10,
      		pagesLength: 15,
      		perPageOptions: [10, 20, 30, 40, 50, 60]
  	  };
    Date.prototype.Format = function(fmt) 
    { //author: meizz 
      var o = { 
        "M+" : this.getMonth()+1,                 //月份 
        "d+" : this.getDate(),                    //日 
        "h+" : this.getHours(),                   //小时 
        "m+" : this.getMinutes(),                 //分 
        "s+" : this.getSeconds(),                 //秒 
        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
        "S"  : this.getMilliseconds()             //毫秒 
      }; 
      if(/(y+)/.test(fmt)) 
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
      for(var k in o) 
        if(new RegExp("("+ k +")").test(fmt)) 
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
      return fmt; 
    }
    if(!$scope.startdate) $scope.startdate = new Date().Format("yyyy-MM-dd")+' 00:00:00';
    if(!$scope.enddate) $scope.enddate = new Date().Format("yyyy-MM-dd hh:mm:ss");
    $httpWrapper.post({
        url:"bizwarning/getBizWarningByPageByCondition",
        data:'{"currentPage": {"currentPage": 1,"pageSize": 10},"conditions": {"bizStartDate": "'+$scope.startdate+'","bizEndDate": "'+$scope.enddate+'"}}',
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

    $scope.$watch(function (){ 
   		return $scope.myPage.itemsPerPage+' '+$scope.myPage.currentPage+' '+$scope.myPage.totalItems;
	},getList);

    function getList(){
      	$scope.queryBizWarning();
	}
   
    $scope.openModal = function(content) {
            var modalInstance = $modal.open({
                templateUrl : 'templates/alarm/detailsBussiness.html',
                controller : 'modalCtrl',
                resolve : {
                    data : function() {
                         return content;
                    }
                }
            })
        }

});
