var details = angular.module('details', ['ui.bootstrap']);
//模态框对应的Controller
details.controller('modalCtrl', function($scope, $modalInstance, data) {
          $scope.data= data;

          //在这里处理要进行的操作
          $scope.ok = function() {
              $modalInstance.close();
          };
          $scope.cancel = function() {
              $modalInstance.dismiss('cancel');
          }
    });