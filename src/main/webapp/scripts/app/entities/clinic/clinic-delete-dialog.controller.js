'use strict';

angular.module('nattymalsApp')
	.controller('ClinicDeleteController', function($scope, $uibModalInstance, entity, Clinic) {

        $scope.clinic = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Clinic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
