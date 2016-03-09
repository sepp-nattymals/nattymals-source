'use strict';

angular.module('nattymalsApp')
	.controller('MedalsDeleteController', function($scope, $uibModalInstance, entity, Medals) {

        $scope.medals = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Medals.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
