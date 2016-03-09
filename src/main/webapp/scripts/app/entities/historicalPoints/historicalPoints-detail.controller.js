'use strict';

angular.module('nattymalsApp')
    .controller('HistoricalPointsDetailController', function ($scope, $rootScope, $stateParams, entity, HistoricalPoints, PetOwner) {
        $scope.historicalPoints = entity;
        $scope.load = function (id) {
            HistoricalPoints.get({id: id}, function(result) {
                $scope.historicalPoints = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:historicalPointsUpdate', function(event, result) {
            $scope.historicalPoints = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
