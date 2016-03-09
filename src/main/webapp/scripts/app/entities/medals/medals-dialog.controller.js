'use strict';

angular.module('nattymalsApp').controller('MedalsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Medals', 'PetOwner',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, Medals, PetOwner) {

        $scope.medals = entity;
        $scope.petowners = PetOwner.query();
        $scope.load = function(id) {
            Medals.get({id : id}, function(result) {
                $scope.medals = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:medalsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.medals.id != null) {
                Medals.update($scope.medals, onSaveSuccess, onSaveError);
            } else {
                Medals.save($scope.medals, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setIcon = function ($file, medals) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        medals.icon = base64Data;
                        medals.iconContentType = $file.type;
                    });
                };
            }
        };
}]);
