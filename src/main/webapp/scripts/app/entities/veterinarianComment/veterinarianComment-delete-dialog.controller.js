'use strict';

angular.module('nattymalsApp')
	.controller('VeterinarianCommentDeleteController', function($scope, $uibModalInstance, entity, VeterinarianComment) {

        $scope.veterinarianComment = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            VeterinarianComment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
