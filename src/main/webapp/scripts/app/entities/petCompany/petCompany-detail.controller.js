'use strict';

angular.module('nattymalsApp')
    .controller('PetCompanyDetailController', function ($scope, $rootScope, $stateParams, entity, PetCompany, Contract) {
        $scope.petCompany = entity;
        $scope.load = function (id) {
            PetCompany.get({id: id}, function(result) {
                $scope.petCompany = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:petCompanyUpdate', function(event, result) {
            $scope.petCompany = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
