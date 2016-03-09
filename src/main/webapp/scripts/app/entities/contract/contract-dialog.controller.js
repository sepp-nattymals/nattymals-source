'use strict';

angular.module('nattymalsApp').controller('ContractDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contract', 'PetCompany',
        function($scope, $stateParams, $uibModalInstance, entity, Contract, PetCompany) {

        $scope.contract = entity;
        $scope.petcompanys = PetCompany.query();
        $scope.load = function(id) {
            Contract.get({id : id}, function(result) {
                $scope.contract = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:contractUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.contract.id != null) {
                Contract.update($scope.contract, onSaveSuccess, onSaveError);
            } else {
                Contract.save($scope.contract, onSaveSuccess, onSaveError);
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
        $scope.datePickerForTerminationDate = {};

        $scope.datePickerForTerminationDate.status = {
            opened: false
        };

        $scope.datePickerForTerminationDateOpen = function($event) {
            $scope.datePickerForTerminationDate.status.opened = true;
        };
}]);
