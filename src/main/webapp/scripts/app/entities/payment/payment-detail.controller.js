'use strict';

angular.module('nattymalsApp')
    .controller('PaymentDetailController', function ($scope, $rootScope, $stateParams, entity, Payment, Actor) {
        $scope.payment = entity;
        $scope.load = function (id) {
            Payment.get({id: id}, function(result) {
                $scope.payment = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:paymentUpdate', function(event, result) {
            $scope.payment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
