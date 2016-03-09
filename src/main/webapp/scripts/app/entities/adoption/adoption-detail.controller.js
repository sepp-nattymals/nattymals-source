'use strict';

angular.module('nattymalsApp')
    .controller('AdoptionDetailController', function ($scope, $rootScope, $stateParams, entity, Adoption, Pet) {
        $scope.adoption = entity;
        $scope.load = function (id) {
            Adoption.get({id: id}, function(result) {
                $scope.adoption = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:adoptionUpdate', function(event, result) {
            $scope.adoption = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
