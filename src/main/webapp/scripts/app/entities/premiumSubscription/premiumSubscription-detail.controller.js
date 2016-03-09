'use strict';

angular.module('nattymalsApp')
    .controller('PremiumSubscriptionDetailController', function ($scope, $rootScope, $stateParams, entity, PremiumSubscription, Customer) {
        $scope.premiumSubscription = entity;
        $scope.load = function (id) {
            PremiumSubscription.get({id: id}, function(result) {
                $scope.premiumSubscription = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:premiumSubscriptionUpdate', function(event, result) {
            $scope.premiumSubscription = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
