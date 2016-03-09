'use strict';

angular.module('nattymalsApp')
	.controller('HistoricalPointsDeleteController', function($scope, $uibModalInstance, entity, HistoricalPoints) {

        $scope.historicalPoints = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HistoricalPoints.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
