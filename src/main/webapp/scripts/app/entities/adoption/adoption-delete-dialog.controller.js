'use strict';

angular.module('nattymalsApp')
	.controller('AdoptionDeleteController', function($scope, $uibModalInstance, entity, Adoption) {

        $scope.adoption = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Adoption.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
