var alarmset=angular.module("alarmset",['ngRoute','mePagination','ui.bootstrap']);
alarmset.config(function($routeProvider){
    $routeProvider.when("/alarmset",{
        templateUrl:"templates/alarm/alarm-set.html",
        controller:"alarmset"
    });
});
alarmset.controller("alarmset",function($scope,$httpWrapper,$breadcrumb,$menu,$modal){
	 $scope.myPage={
     		currentPage:1,
     		totalItems:0,
     		itemsPerPage: 10,
     		pagesLength: 15,
     		perPageOptions: [10, 20, 30, 40, 50, 60]
 	  };
  	$scope.$watch(function (){ 
      		return $scope.myPage.itemsPerPage+' '+$scope.myPage.currentPage+' '+$scope.myPage.totalItems;
  	},getList);
	function getList(){
//     	$scope.mailSetQuery();
	}
	$scope.refresh=function (){
  	  $httpWrapper.post({
            url:"notification/getNotificationByPage",
            data:'{"currentPage": {"currentPage": "1","pageSize":"50"},"conditions": {"id":"","type": "","receiver": "","address":""}}',
            success:function(data){
                $scope.mails=data.list;
                if(!data||data.length<0){
                    $scope.isEmpty=true;
                }
                $scope.originData=data;
            }
        });
    }
    $scope.switchTab=function(tabName){
        $scope.currentTab=tabName;
        switch (tabName){
            case 'setMail':{
            	  var myChart = document.getElementById('SetMail');
                  $scope.mails=[];
                  $scope.isEmpty=false;
                	  $httpWrapper.post({
                          url:"notification/getNotificationByPage",
                          data:'{"currentPage": {"currentPage": "1","pageSize":"50"},"conditions": {"id":"","type": "","receiver": "","address":""}}',
                          success:function(data){
                              $scope.mails=data.list;
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
                      $scope.mails=$queryFilter($scope.originData,$scope.query);
                  }
//                  $scope.mailSetQuery=function(){
//                  	if($scope.myPage.currentPage===0){
//                  		$scope.myPage.currentPage = 1;
//                  	}
//                  	$httpWrapper.post({
//                          url:"notification/getNotificationByPage",
//                          data:'{"currentPage": {"currentPage": '+$scope.myPage.currentPage+',"pageSize":'+$scope.myPage.itemsPerPage+'},"conditions": {"id":"","type": "","receiver": "","address":""}}',
//                          success:function(data){
//                              $scope.mails=data.list;
//                              if(!data||data.length<0){
//                                  $scope.isEmpty=true;
//                              }
//                              $scope.myPage.totalItems=data.totalCount;
//                              $scope.originData=data;
//                          }
//                      });
//                  }
                 $scope.addMail=function(add){
                	 var modaladdMail = $modal.open({
                         templateUrl : 'templates/alarm/AddMail.html',//script标签中定义的id
                         controller : 'modalCtrl',//modal对应的Controller
                         resolve : {
                             data : function() {//data作为modal的controller传入的参数
                                  return add;//用于传递数据
                             }
                         }
                     })
                     modaladdMail.opened.then(function () {// 模态窗口打开之后执行的函数
                    	 
                     });
                	 modaladdMail.result.then(function (result) {
                     //这是关闭模态框的回调函数，可以在这里去重新请求父页面的数据
                		 $scope.refresh();
                     });
                     
                 }
                 
                 $scope.updateMail=function(id,type,receiver,address){
                	 var modalupdateMail = $modal.open({
                         templateUrl : 'templates/alarm/UpdateMail.html',
                         controller : 'updateMailCtrl',
                         resolve : {
                        	 id : function() {
                                  return  id;
                             },
                             type : function() {
                                 return  type;
                            },
                            receiver : function() {
                                 return  receiver;
                            },
                            address : function() {
                                return  address;
                           }
                         }
                     });
                	 modalupdateMail.opened.then(function () {// 模态窗口打开之后执行的函数
                    	 
                     });
                	 modalupdateMail.result.then(function (result) {
                     //这是关闭模态框的回调函数，可以在这里去重新请求父页面的数据
                		 $scope.refresh();
                     });
                 }
                 
                 $scope.deleteMail=function(id){
                	 var modaldeleteMail = $modal.open({
                         templateUrl : 'templates/alarm/DeleteMail.html',//script标签中定义的id
                         controller : 'deleteMailCtrl',//modal对应的Controller
                         resolve : {
                             id : function() {//data作为modal的controller传入的参数
                                  return id;//用于传递数据
                             }
                         }
                     });
                	 modaldeleteMail.opened.then(function () {// 模态窗口打开之后执行的函数
                    	 
                     });
                	 modaldeleteMail.result.then(function (result) {
                     //这是关闭模态框的回调函数，可以在这里去重新请求父页面的数据
                		 $scope.refresh();
                     });
                 }
                 
                  
                 
                
                break;
            }
            case 'setPhone':{
            	
            	var myChart = document.getElementById('SetPhone');
                break;
            }
            case 'setqq':{
            	
            	var myChart = document.getElementById('Setqq');
                break;
            }
            
        }
    }
			
                       
});
