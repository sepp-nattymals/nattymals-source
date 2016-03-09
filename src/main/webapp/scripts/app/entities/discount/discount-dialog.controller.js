'use strict';

angular.module('nattymalsApp').controller('DiscountDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Discount', 'Administrator',
        function($scope, $stateParams, $uibModalInstance, entity, Discount, Administrator) {

        $scope.discount = entity;
        $scope.administrators = Administrator.query();
        $scope.load = function(id) {
            Discount.get({id : id}, function(result) {
                $scope.discount = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:discountUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.discount.id != null) {
                Discount.update($scope.discount, onSaveSuccess, onSaveError);
            } else {
                Discount.save($scope.discount, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForStartDate = {};

        $scope.datePickerForStartDate.status = {
            opened: false
        };

        $scope.datePickerForStartDateOpen = function($event) {
            $scope.datePickerForStartDate.status.opened = true;
        };
        $scope.datePickerForEndDate = {};

        $scope.datePickerForEndDate.status = {
            opened: false
        };

        $scope.datePickerForEndDateOpen = function($event) {
            $scope.datePickerForEndDate.status.opened = true;
        };
}]);
