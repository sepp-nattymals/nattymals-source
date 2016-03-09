'use strict';

angular.module('nattymalsApp')
    .controller('MessageDetailController', function ($scope, $rootScope, $stateParams, entity, Message, Customer) {
        $scope.message = entity;
        $scope.load = function (id) {
            Message.get({id: id}, function(result) {
                $scope.message = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:messageUpdate', function(event, result) {
            $scope.message = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
