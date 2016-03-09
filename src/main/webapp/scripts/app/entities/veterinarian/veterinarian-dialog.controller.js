'use strict';

angular.module('nattymalsApp').controller('VeterinarianDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Veterinarian', 'Clinic', 'VeterinarianRating', 'VeterinarianComment',
        function($scope, $stateParams, $uibModalInstance, entity, Veterinarian, Clinic, VeterinarianRating, VeterinarianComment) {

        $scope.veterinarian = entity;
        $scope.clinics = Clinic.query();
        $scope.veterinarianratings = VeterinarianRating.query();
        $scope.veterinariancomments = VeterinarianComment.query();
        $scope.load = function(id) {
            Veterinarian.get({id : id}, function(result) {
                $scope.veterinarian = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:veterinarianUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.veterinarian.id != null) {
                Veterinarian.update($scope.veterinarian, onSaveSuccess, onSaveError);
            } else {
                Veterinarian.save($scope.veterinarian, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
