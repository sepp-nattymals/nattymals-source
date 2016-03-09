'use strict';

angular.module('nattymalsApp')
	.controller('PetCompanyDeleteController', function($scope, $uibModalInstance, entity, PetCompany) {

        $scope.petCompany = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PetCompany.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
