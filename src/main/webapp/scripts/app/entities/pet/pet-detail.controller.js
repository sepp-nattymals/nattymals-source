'use strict';

angular.module('nattymalsApp')
    .controller('PetDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Pet, PetOwner, PetComment, Adoption, PetRating) {
        $scope.pet = entity;
        $scope.load = function (id) {
            Pet.get({id: id}, function(result) {
                $scope.pet = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:petUpdate', function(event, result) {
            $scope.pet = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
