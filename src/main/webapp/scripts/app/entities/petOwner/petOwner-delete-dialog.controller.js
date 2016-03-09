'use strict';

angular.module('nattymalsApp')
	.controller('PetOwnerDeleteController', function($scope, $uibModalInstance, entity, PetOwner) {

        $scope.petOwner = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PetOwner.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
