'use strict';

angular.module('nattymalsApp')
    .controller('DiscountDetailController', function ($scope, $rootScope, $stateParams, entity, Discount, Administrator) {
        $scope.discount = entity;
        $scope.load = function (id) {
            Discount.get({id: id}, function(result) {
                $scope.discount = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:discountUpdate', function(event, result) {
            $scope.discount = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
