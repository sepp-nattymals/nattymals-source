'use strict';

angular.module('nattymalsApp')
    .controller('PetRatingDetailController', function ($scope, $rootScope, $stateParams, entity, PetRating, PetOwner, Pet) {
        $scope.petRating = entity;
        $scope.load = function (id) {
            PetRating.get({id: id}, function(result) {
                $scope.petRating = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:petRatingUpdate', function(event, result) {
            $scope.petRating = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
