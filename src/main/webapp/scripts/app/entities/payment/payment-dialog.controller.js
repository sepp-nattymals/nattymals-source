'use strict';

angular.module('nattymalsApp').controller('PaymentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Payment', 'Actor',
        function($scope, $stateParams, $uibModalInstance, entity, Payment, Actor) {

        $scope.payment = entity;
        $scope.actors = Actor.query();
        $scope.load = function(id) {
            Payment.get({id : id}, function(result) {
                $scope.payment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:paymentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.payment.id != null) {
                Payment.update($scope.payment, onSaveSuccess, onSaveError);
            } else {
                Payment.save($scope.payment, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForPaymentDate = {};

        $scope.datePickerForPaymentDate.status = {
            opened: false
        };

        $scope.datePickerForPaymentDateOpen = function($event) {
            $scope.datePickerForPaymentDate.status.opened = true;
        };
}]);
