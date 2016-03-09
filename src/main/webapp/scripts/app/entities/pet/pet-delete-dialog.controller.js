'use strict';

angular.module('nattymalsApp')
	.controller('PetDeleteController', function($scope, $uibModalInstance, entity, Pet) {

        $scope.pet = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Pet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
