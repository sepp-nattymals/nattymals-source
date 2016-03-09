'use strict';

angular.module('nattymalsApp').controller('PetCompanyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PetCompany', 'Contract',
        function($scope, $stateParams, $uibModalInstance, entity, PetCompany, Contract) {

        $scope.petCompany = entity;
        $scope.contracts = Contract.query();
        $scope.load = function(id) {
            PetCompany.get({id : id}, function(result) {
                $scope.petCompany = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:petCompanyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.petCompany.id != null) {
                PetCompany.update($scope.petCompany, onSaveSuccess, onSaveError);
            } else {
                PetCompany.save($scope.petCompany, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
