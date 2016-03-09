'use strict';

angular.module('nattymalsApp').controller('PetCommentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PetComment', 'PetOwner', 'Pet',
        function($scope, $stateParams, $uibModalInstance, entity, PetComment, PetOwner, Pet) {

        $scope.petComment = entity;
        $scope.petowners = PetOwner.query();
        $scope.pets = Pet.query();
        $scope.load = function(id) {
            PetComment.get({id : id}, function(result) {
                $scope.petComment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:petCommentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.petComment.id != null) {
                PetComment.update($scope.petComment, onSaveSuccess, onSaveError);
            } else {
                PetComment.save($scope.petComment, onSaveSuccess, onSaveError);
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
