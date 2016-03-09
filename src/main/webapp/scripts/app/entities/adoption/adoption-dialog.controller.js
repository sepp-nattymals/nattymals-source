'use strict';

angular.module('nattymalsApp').controller('AdoptionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Adoption', 'Pet',
        function($scope, $stateParams, $uibModalInstance, entity, Adoption, Pet) {

        $scope.adoption = entity;
        $scope.pets = Pet.query();
        $scope.load = function(id) {
            Adoption.get({id : id}, function(result) {
                $scope.adoption = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:adoptionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.adoption.id != null) {
                Adoption.update($scope.adoption, onSaveSuccess, onSaveError);
            } else {
                Adoption.save($scope.adoption, onSaveSuccess, onSaveError);
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
        $scope.datePickerForModificationDate = {};

        $scope.datePickerForModificationDate.status = {
            opened: false
        };

        $scope.datePickerForModificationDateOpen = function($event) {
            $scope.datePickerForModificationDate.status.opened = true;
        };
}]);
