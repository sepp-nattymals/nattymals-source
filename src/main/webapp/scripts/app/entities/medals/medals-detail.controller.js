'use strict';

angular.module('nattymalsApp')
    .controller('MedalsDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Medals, PetOwner) {
        $scope.medals = entity;
        $scope.load = function (id) {
            Medals.get({id: id}, function(result) {
                $scope.medals = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:medalsUpdate', function(event, result) {
            $scope.medals = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
