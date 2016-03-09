'use strict';

angular.module('nattymalsApp')
    .controller('FolderDetailController', function ($scope, $rootScope, $stateParams, entity, Folder, Customer, Message) {
        $scope.folder = entity;
        $scope.load = function (id) {
            Folder.get({id: id}, function(result) {
                $scope.folder = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:folderUpdate', function(event, result) {
            $scope.folder = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
