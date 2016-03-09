'use strict';

angular.module('nattymalsApp').controller('PetRatingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PetRating', 'PetOwner', 'Pet',
        function($scope, $stateParams, $uibModalInstance, entity, PetRating, PetOwner, Pet) {

        $scope.petRating = entity;
        $scope.petowners = PetOwner.query();
        $scope.pets = Pet.query();
        $scope.load = function(id) {
            PetRating.get({id : id}, function(result) {
                $scope.petRating = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:petRatingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.petRating.id != null) {
                PetRating.update($scope.petRating, onSaveSuccess, onSaveError);
            } else {
                PetRating.save($scope.petRating, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
