'use strict';

angular.module('nattymalsApp')
    .controller('ContractDetailController', function ($scope, $rootScope, $stateParams, entity, Contract, PetCompany) {
        $scope.contract = entity;
        $scope.load = function (id) {
            Contract.get({id: id}, function(result) {
                $scope.contract = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:contractUpdate', function(event, result) {
            $scope.contract = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
