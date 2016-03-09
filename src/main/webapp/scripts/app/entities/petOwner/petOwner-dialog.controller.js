'use strict';

angular.module('nattymalsApp').controller('PetOwnerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PetOwner', 'HistoricalPoints', 'VeterinarianComment', 'PetComment', 'Medals', 'Pet', 'PetRating', 'VeterinarianRating',
        function($scope, $stateParams, $uibModalInstance, entity, PetOwner, HistoricalPoints, VeterinarianComment, PetComment, Medals, Pet, PetRating, VeterinarianRating) {

        $scope.petOwner = entity;
        $scope.historicalpointss = HistoricalPoints.query();
        $scope.veterinariancomments = VeterinarianComment.query();
        $scope.petcomments = PetComment.query();
        $scope.medalss = Medals.query();
        $scope.pets = Pet.query();
        $scope.petratings = PetRating.query();
        $scope.veterinarianratings = VeterinarianRating.query();
        $scope.load = function(id) {
            PetOwner.get({id : id}, function(result) {
                $scope.petOwner = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:petOwnerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.petOwner.id != null) {
                PetOwner.update($scope.petOwner, onSaveSuccess, onSaveError);
            } else {
                PetOwner.save($scope.petOwner, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
