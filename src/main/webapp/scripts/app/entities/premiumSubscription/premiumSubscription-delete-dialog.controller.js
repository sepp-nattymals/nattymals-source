'use strict';

angular.module('nattymalsApp')
	.controller('PremiumSubscriptionDeleteController', function($scope, $uibModalInstance, entity, PremiumSubscription) {

        $scope.premiumSubscription = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PremiumSubscription.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
