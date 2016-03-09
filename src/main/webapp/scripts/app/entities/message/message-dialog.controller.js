'use strict';

angular.module('nattymalsApp').controller('MessageDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Message', 'Customer',
        function($scope, $stateParams, $uibModalInstance, entity, Message, Customer) {

        $scope.message = entity;
        $scope.customers = Customer.query();
        $scope.load = function(id) {
            Message.get({id : id}, function(result) {
                $scope.message = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:messageUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.message.id != null) {
                Message.update($scope.message, onSaveSuccess, onSaveError);
            } else {
                Message.save($scope.message, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreationDate = {};

        $scope.datePickerForCreationDate.status = {
            opened: false
        };

        $scope.datePickerForCreationDateOpen = function($event) {
            $scope.datePickerForCreationDate.status.opened = true;
        };
}]);
