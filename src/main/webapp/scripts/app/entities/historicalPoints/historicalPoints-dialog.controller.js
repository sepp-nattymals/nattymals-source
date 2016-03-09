'use strict';

angular.module('nattymalsApp').controller('HistoricalPointsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HistoricalPoints', 'PetOwner',
        function($scope, $stateParams, $uibModalInstance, entity, HistoricalPoints, PetOwner) {

        $scope.historicalPoints = entity;
        $scope.petowners = PetOwner.query();
        $scope.load = function(id) {
            HistoricalPoints.get({id : id}, function(result) {
                $scope.historicalPoints = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:historicalPointsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.historicalPoints.id != null) {
                HistoricalPoints.update($scope.historicalPoints, onSaveSuccess, onSaveError);
            } else {
                HistoricalPoints.save($scope.historicalPoints, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
