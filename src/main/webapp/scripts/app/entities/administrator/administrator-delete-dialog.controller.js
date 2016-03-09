'use strict';

angular.module('nattymalsApp')
	.controller('AdministratorDeleteController', function($scope, $uibModalInstance, entity, Administrator) {

        $scope.administrator = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Administrator.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
