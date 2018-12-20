var details = angular.module('details', ['ui.bootstrap','ngRoute']);
//模态框对应的Controller
details.controller('modalCtrl', function($scope, $modalInstance, data,$httpWrapper) {
          $scope.data= data;
          $scope.cancel = function() {
              $modalInstance.dismiss('cancel');
          }
          $scope.addMail=function(){
	         	 $httpWrapper.post({
	         		url:"notification/addNotification",
	         		data:'{"type": "'+$scope.addMailType+'","receiver": "'+$scope.addMailReceiver+'","address": "'+$scope.addMailAddress+'"}',
	         		success:function(){
	         			$modalInstance.close();
	         			alert("保存成功");
	         		}
	         	 });
	          }        
});
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
         		data:'{"id":"'+$scope.updateMailId+'","type": "'+$scope.updateMailType+'","receiver": "'+$scope.updateMailReceiver+'","address": "'+$scope.updateMailAddress+'"}',
         		success:function(){
         			$modalInstance.close();
         			alert("已修改");
         		}
         	 });
          }      
});
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
       			alert("已删除");
       		}
       	 });
        }        
});