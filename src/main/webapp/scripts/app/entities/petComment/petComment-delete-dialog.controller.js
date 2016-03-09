'use strict';

angular.module('nattymalsApp')
	.controller('PetCommentDeleteController', function($scope, $uibModalInstance, entity, PetComment) {

        $scope.petComment = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PetComment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
