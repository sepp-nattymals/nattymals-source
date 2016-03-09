'use strict';

angular.module('nattymalsApp').controller('PremiumSubscriptionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PremiumSubscription', 'Customer',
        function($scope, $stateParams, $uibModalInstance, entity, PremiumSubscription, Customer) {

        $scope.premiumSubscription = entity;
        $scope.customers = Customer.query();
        $scope.load = function(id) {
            PremiumSubscription.get({id : id}, function(result) {
                $scope.premiumSubscription = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:premiumSubscriptionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.premiumSubscription.id != null) {
                PremiumSubscription.update($scope.premiumSubscription, onSaveSuccess, onSaveError);
            } else {
                PremiumSubscription.save($scope.premiumSubscription, onSaveSuccess, onSaveError);
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
