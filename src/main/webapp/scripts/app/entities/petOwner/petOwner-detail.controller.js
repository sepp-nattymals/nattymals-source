'use strict';

angular.module('nattymalsApp')
    .controller('PetOwnerDetailController', function ($scope, $rootScope, $stateParams, entity, PetOwner, HistoricalPoints, VeterinarianComment, PetComment, Medals, Pet, PetRating, VeterinarianRating) {
        $scope.petOwner = entity;
        $scope.load = function (id) {
            PetOwner.get({id: id}, function(result) {
                $scope.petOwner = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:petOwnerUpdate', function(event, result) {
            $scope.petOwner = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
