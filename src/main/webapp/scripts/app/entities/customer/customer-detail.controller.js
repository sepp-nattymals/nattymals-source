'use strict';

angular.module('nattymalsApp')
    .controller('CustomerDetailController', function ($scope, $rootScope, $stateParams, entity, Customer, PremiumSubscription, Folder) {
        $scope.customer = entity;
        $scope.load = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:customerUpdate', function(event, result) {
            $scope.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
