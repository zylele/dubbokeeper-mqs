var details = angular.module('details', ['ui.bootstrap','ngRoute']);
//增加邮箱模态框对应的Controller
details.controller('modalCtrl', function($scope, $modalInstance, data,$httpWrapper) {
          $scope.data= data;
          $scope.cancel = function() {
              $modalInstance.dismiss('cancel');
          }
          $scope.addMail=function(){
	         	 $httpWrapper.post({
	         		url:"notification/addNotification",
	         		data:'{"type": "01","receiver": "'+$scope.addMailReceiver+'","address": "'+$scope.addMailAddress+'"}',
	         		success:function(){
	         			$modalInstance.close();
	         			//alert("保存成功");
	         		}
	         	 });
	          }        
});

//更新邮箱模态框对应的Controller
details.controller('updateMailCtrl', function($scope, $modalInstance, id,type,receiver,address,$httpWrapper) {
    if(id )$scope.updateMailId = id;
    if(type )$scope.updateMailType = type;
    if(receiver )$scope.updateMailReceiver = receiver;
    if(address )$scope.updateMailAddress = address;
    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
    
  	  $scope.updateMail=function(){
         	 $httpWrapper.post({
         		url:"notification/updateNotificationById",
         		data:'{"id":"'+$scope.updateMailId+'","type": "01","receiver": "'+$scope.updateMailReceiver+'","address": "'+$scope.updateMailAddress+'"}',
         		success:function(){
         			$modalInstance.close();
         			//alert("已修改");
         		}
         	 });
          }      
});

//删除邮箱
details.controller('deleteMailCtrl', function($scope, $modalInstance, id,$httpWrapper) {

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
    $scope.deleteMail=function(){
       	 $httpWrapper.post({
       		url:"notification/deleteNotificationById",
       		data:'{"id":"'+id+'"}',
       		success:function(){
       			$modalInstance.close();
       			//alert("已删除");
       		}
       	 });
        }        
});
//增加手机模态框对应的Controller
details.controller('modalCtrlPhone', function($scope, $modalInstance, data,$httpWrapper) {
          $scope.data= data;
          $scope.cancel = function() {
              $modalInstance.dismiss('cancel');
          }
          $scope.addPhone=function(){
	         	 $httpWrapper.post({
	         		url:"notification/addNotification",
	         		data:'{"type": "02","receiver": "'+$scope.addPhoneReceiver+'","address": "'+$scope.addPhoneAddress+'"}',
	         		success:function(){
	         			$modalInstance.close();
	         			//alert("保存成功");
	         		}
	         	 });
	          }        
});
//更新手机模态框对应的Controller
details.controller('updatePhoneCtrl', function($scope, $modalInstance, id,type,receiver,address,$httpWrapper) {
    if(id )$scope.updatePhoneId = id;
    if(type )$scope.updatePhoneType = type;
    if(receiver )$scope.updatePhoneReceiver = receiver;
    if(address )$scope.updatePhoneAddress = address;
    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
    
  	  $scope.updatePhone=function(){
         	 $httpWrapper.post({
         		url:"notification/updateNotificationById",
         		data:'{"id":"'+$scope.updatePhoneId+'","type": "02","receiver": "'+$scope.updatePhoneReceiver+'","address": "'+$scope.updatePhoneAddress+'"}',
         		success:function(){
         			$modalInstance.close();
         			//alert("已修改");
         		}
         	 });
          }      
});
//删除手机
details.controller('deletePhoneCtrl', function($scope, $modalInstance, id,$httpWrapper) {

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
    $scope.deletePhone=function(){
       	 $httpWrapper.post({
       		url:"notification/deleteNotificationById",
       		data:'{"id":"'+id+'"}',
       		success:function(){
       			$modalInstance.close();
       			//alert("已删除");
       		}
       	 });
        }        
});

