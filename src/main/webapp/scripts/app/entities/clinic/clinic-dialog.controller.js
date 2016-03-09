'use strict';

angular.module('nattymalsApp').controller('ClinicDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Clinic', 'Veterinarian',
        function($scope, $stateParams, $uibModalInstance, entity, Clinic, Veterinarian) {

        $scope.clinic = entity;
        $scope.veterinarians = Veterinarian.query();
        $scope.load = function(id) {
            Clinic.get({id : id}, function(result) {
                $scope.clinic = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:clinicUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.clinic.id != null) {
                Clinic.update($scope.clinic, onSaveSuccess, onSaveError);
            } else {
                Clinic.save($scope.clinic, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
