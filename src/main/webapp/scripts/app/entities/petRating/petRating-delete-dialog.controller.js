'use strict';

angular.module('nattymalsApp')
	.controller('PetRatingDeleteController', function($scope, $uibModalInstance, entity, PetRating) {

        $scope.petRating = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PetRating.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
