'use strict';

angular.module('nattymalsApp').controller('CustomerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Customer', 'PremiumSubscription', 'Folder',
        function($scope, $stateParams, $uibModalInstance, entity, Customer, PremiumSubscription, Folder) {

        $scope.customer = entity;
        $scope.premiumsubscriptions = PremiumSubscription.query();
        $scope.folders = Folder.query();
        $scope.load = function(id) {
            Customer.get({id : id}, function(result) {
                $scope.customer = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:customerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.customer.id != null) {
                Customer.update($scope.customer, onSaveSuccess, onSaveError);
            } else {
                Customer.save($scope.customer, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
