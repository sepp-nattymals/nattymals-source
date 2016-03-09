'use strict';

angular.module('nattymalsApp')
    .controller('AdministratorDetailController', function ($scope, $rootScope, $stateParams, entity, Administrator, Discount, Announcement) {
        $scope.administrator = entity;
        $scope.load = function (id) {
            Administrator.get({id: id}, function(result) {
                $scope.administrator = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:administratorUpdate', function(event, result) {
            $scope.administrator = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
