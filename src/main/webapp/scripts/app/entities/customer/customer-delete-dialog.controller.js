'use strict';

angular.module('nattymalsApp')
	.controller('CustomerDeleteController', function($scope, $uibModalInstance, entity, Customer) {

        $scope.customer = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Customer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
