'use strict';

angular.module('nattymalsApp')
	.controller('DiscountDeleteController', function($scope, $uibModalInstance, entity, Discount) {

        $scope.discount = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Discount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
