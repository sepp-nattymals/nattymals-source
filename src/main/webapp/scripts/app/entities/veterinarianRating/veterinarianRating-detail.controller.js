'use strict';

angular.module('nattymalsApp')
    .controller('VeterinarianRatingDetailController', function ($scope, $rootScope, $stateParams, entity, VeterinarianRating, Veterinarian, PetOwner) {
        $scope.veterinarianRating = entity;
        $scope.load = function (id) {
            VeterinarianRating.get({id: id}, function(result) {
                $scope.veterinarianRating = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:veterinarianRatingUpdate', function(event, result) {
            $scope.veterinarianRating = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
