'use strict';

angular.module('nattymalsApp')
	.controller('FolderDeleteController', function($scope, $uibModalInstance, entity, Folder) {

        $scope.folder = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Folder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
