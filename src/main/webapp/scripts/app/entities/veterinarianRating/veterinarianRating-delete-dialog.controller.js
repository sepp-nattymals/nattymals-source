'use strict';

angular.module('nattymalsApp')
	.controller('VeterinarianRatingDeleteController', function($scope, $uibModalInstance, entity, VeterinarianRating) {

        $scope.veterinarianRating = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            VeterinarianRating.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
