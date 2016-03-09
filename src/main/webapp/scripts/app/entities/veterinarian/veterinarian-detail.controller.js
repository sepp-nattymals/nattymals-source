'use strict';

angular.module('nattymalsApp')
    .controller('VeterinarianDetailController', function ($scope, $rootScope, $stateParams, entity, Veterinarian, Clinic, VeterinarianRating, VeterinarianComment) {
        $scope.veterinarian = entity;
        $scope.load = function (id) {
            Veterinarian.get({id: id}, function(result) {
                $scope.veterinarian = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:veterinarianUpdate', function(event, result) {
            $scope.veterinarian = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
