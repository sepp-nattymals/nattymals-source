'use strict';

angular.module('nattymalsApp')
	.controller('VeterinarianDeleteController', function($scope, $uibModalInstance, entity, Veterinarian) {

        $scope.veterinarian = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Veterinarian.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
