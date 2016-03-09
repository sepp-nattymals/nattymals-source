'use strict';

angular.module('nattymalsApp')
	.controller('MessageDeleteController', function($scope, $uibModalInstance, entity, Message) {

        $scope.message = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Message.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
