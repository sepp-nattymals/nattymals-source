'use strict';

angular.module('nattymalsApp')
	.controller('ActorDeleteController', function($scope, $uibModalInstance, entity, Actor) {

        $scope.actor = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Actor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
