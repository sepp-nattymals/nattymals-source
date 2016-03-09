'use strict';

angular.module('nattymalsApp').controller('VeterinarianCommentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'VeterinarianComment', 'Veterinarian', 'PetOwner',
        function($scope, $stateParams, $uibModalInstance, entity, VeterinarianComment, Veterinarian, PetOwner) {

        $scope.veterinarianComment = entity;
        $scope.veterinarians = Veterinarian.query();
        $scope.petowners = PetOwner.query();
        $scope.load = function(id) {
            VeterinarianComment.get({id : id}, function(result) {
                $scope.veterinarianComment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:veterinarianCommentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.veterinarianComment.id != null) {
                VeterinarianComment.update($scope.veterinarianComment, onSaveSuccess, onSaveError);
            } else {
                VeterinarianComment.save($scope.veterinarianComment, onSaveSuccess, onSaveError);
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
}]);
