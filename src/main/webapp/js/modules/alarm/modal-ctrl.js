var details = angular.module('details', ['ui.bootstrap','ngRoute']);
//增加邮箱模态框对应的Controller
details.controller('modalCtrl', function($scope, $modalInstance, data,$httpWrapper) {
          $scope.data= data;
          $scope.cancel = function() {
              $modalInstance.dismiss('cancel');
          }
          $scope.addMail=function(){
        		  success:$httpWrapper.post({
				         		url:"notification/addNotification",
				         		data:'{"type": "01","receiver": "'+$scope.addMailReceiver+'","address": "'+$scope.addMailAddress+'","chnlCode":"'+$scope.addMailChnlCode+'","chnlName":"'+$scope.addMailChnlName+'"}',
				         		success:function(){
				         			$modalInstance.close();
				         			//alert("保存成功");
				         		}
				         	 });
        
	         	 
	          } 
          
          $scope.chnlDefs=[];
          $httpWrapper.post({
              url:"notification/getChnlDef",
              success:function(data){
                  $scope.chnlDefs=data.list;
                  $(function() {
          			$(".selectpicker").selectpicker({
          				noneSelectedText: '请选择',
          				countSelectedText: function(){}
          			});
          		});
          	   function selectValue() {
                 //获取选择的值
                      alert($('#usertype').selectpicker('val'));
                  }
                  if(!data||data.length<0){
                      $scope.isEmpty=true;
                  }
                  $scope.originData=data;
              }
          });
          
});

//更新邮箱模态框对应的Controller
details.controller('updateMailCtrl', function($scope, $modalInstance, id,type,receiver,address,chnlCode,chnlName,$httpWrapper) {
    if(id )$scope.updateMailId = id;
    if(type )$scope.updateMailType = type;
    if(receiver )$scope.updateMailReceiver = receiver;
    if(address )$scope.updateMailAddress = address;
    if(chnlCode )$scope.updateMailChnlCode = chnlCode;
    if(chnlName )$scope.updateMailChnlName = chnlName;
    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
    
  	  $scope.updateMail=function(){
         	 $httpWrapper.post({
         		url:"notification/updateNotificationById",
         		data:'{"id":"'+$scope.updateMailId+'","type": "01","receiver": "'+$scope.updateMailReceiver+'","address": "'+$scope.updateMailAddress+'","chnlCode":"'+$scope.updateMailChnlCode+'","chnlName":"'+$scope.updateMailChnlName+'"}',
         		success:function(){
         			$modalInstance.close();
         			//alert("已修改");
         		}
         	 });
          }
	  	$scope.chnlDefs=[];
	    $httpWrapper.post({
	        url:"notification/getChnlDef",
	        success:function(data){
	            $scope.chnlDefs=data.list;
	            $(function() {
	    			$(".selectpicker").selectpicker({
	    				noneSelectedText: $scope.updateMailChnlCode,
	    				countSelectedText: function(){}
	    			});
	    		});
	    	   function selectValue() {
	           //获取选择的值
	                alert($('#usertype').selectpicker('val'));
	            }
	            if(!data||data.length<0){
	                $scope.isEmpty=true;
	            }
	            $scope.originData=data;
	        }
	    });
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
	         		data:'{"type": "02","receiver": "'+$scope.addPhoneReceiver+'","address": "'+$scope.addPhoneAddress+'","chnlCode":"'+$scope.addPhoneChnlCode+'","chnlName":"'+$scope.addPhoneChnlName+'"}',
	         		success:function(){
	         			$modalInstance.close();
	         			//alert("保存成功");
	         		}
	         	 });
	          }   
          $scope.chnlDefs=[];
          $httpWrapper.post({
              url:"notification/getChnlDef",
              success:function(data){
                  $scope.chnlDefs=data.list;
                  $(function() {
          			$(".selectpicker").selectpicker({
          				noneSelectedText: '请选择',
          				countSelectedText: function(){}
          			});
          		});
          	   function selectValue() {
                 //获取选择的值
                      alert($('#usertype').selectpicker('val'));
                  }
                  if(!data||data.length<0){
                      $scope.isEmpty=true;
                  }
                  $scope.originData=data;
              }
          });
});
//更新手机模态框对应的Controller
details.controller('updatePhoneCtrl', function($scope, $modalInstance, id,type,receiver,address,chnlCode,chnlName,$httpWrapper) {
    if(id )$scope.updatePhoneId = id;
    if(type )$scope.updatePhoneType = type;
    if(receiver )$scope.updatePhoneReceiver = receiver;
    if(address )$scope.updatePhoneAddress = address;
    if(chnlCode )$scope.updatePhoneChnlCode = chnlCode;
    if(chnlName )$scope.updatePhoneChnlName = chnlName;
    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
    
  	  $scope.updatePhone=function(){
         	 $httpWrapper.post({
         		url:"notification/updateNotificationById",
         		data:'{"id":"'+$scope.updatePhoneId+'","type": "02","receiver": "'+$scope.updatePhoneReceiver+'","address": "'+$scope.updatePhoneAddress+'","chnlCode":"'+$scope.updatePhoneChnlCode+'","chnlName":"'+$scope.updatePhoneChnlName+'"}',
         		success:function(){
         			$modalInstance.close();
         			//alert("已修改");
         		}
         	 });
          } 
  	 $scope.chnlDefs=[];
     $httpWrapper.post({
         url:"notification/getChnlDef",
         success:function(data){
             $scope.chnlDefs=data.list;
             $(function() {
     			$(".selectpicker").selectpicker({
     				noneSelectedText: $scope.updatePhoneChnlCode,
     				countSelectedText: function(){}
     			});
     		});
     	   function selectValue() {
            //获取选择的值
                 alert($('#usertypePhone').selectpicker('val'));
             }
             if(!data||data.length<0){
                 $scope.isEmpty=true;
             }
             $scope.originData=data;
         }
     });
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

