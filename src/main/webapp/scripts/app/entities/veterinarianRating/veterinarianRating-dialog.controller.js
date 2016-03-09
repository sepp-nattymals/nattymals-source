'use strict';

angular.module('nattymalsApp').controller('VeterinarianRatingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'VeterinarianRating', 'Veterinarian', 'PetOwner',
        function($scope, $stateParams, $uibModalInstance, entity, VeterinarianRating, Veterinarian, PetOwner) {

        $scope.veterinarianRating = entity;
        $scope.veterinarians = Veterinarian.query();
        $scope.petowners = PetOwner.query();
        $scope.load = function(id) {
            VeterinarianRating.get({id : id}, function(result) {
                $scope.veterinarianRating = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:veterinarianRatingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.veterinarianRating.id != null) {
                VeterinarianRating.update($scope.veterinarianRating, onSaveSuccess, onSaveError);
            } else {
                VeterinarianRating.save($scope.veterinarianRating, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
