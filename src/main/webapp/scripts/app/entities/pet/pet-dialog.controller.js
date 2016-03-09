'use strict';

angular.module('nattymalsApp').controller('PetDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Pet', 'PetOwner', 'PetComment', 'Adoption', 'PetRating',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, Pet, PetOwner, PetComment, Adoption, PetRating) {

        $scope.pet = entity;
        $scope.petowners = PetOwner.query();
        $scope.petcomments = PetComment.query();
        $scope.adoptions = Adoption.query();
        $scope.petratings = PetRating.query();
        $scope.load = function(id) {
            Pet.get({id : id}, function(result) {
                $scope.pet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:petUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.pet.id != null) {
                Pet.update($scope.pet, onSaveSuccess, onSaveError);
            } else {
                Pet.save($scope.pet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
        $scope.datePickerForBirthDate = {};

        $scope.datePickerForBirthDate.status = {
            opened: false
        };

        $scope.datePickerForBirthDateOpen = function($event) {
            $scope.datePickerForBirthDate.status.opened = true;
        };

        $scope.setPhoto = function ($file, pet) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        pet.photo = base64Data;
                        pet.photoContentType = $file.type;
                    });
                };
            }
        };
}]);
