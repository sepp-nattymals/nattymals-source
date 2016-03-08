'use strict';

angular.module('nattymalsApp')
    .controller('ActorDetailController', function ($scope, $rootScope, $stateParams, entity, Actor, Payment, User) {
        $scope.actor = entity;
        $scope.load = function (id) {
            Actor.get({id: id}, function(result) {
                $scope.actor = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:actorUpdate', function(event, result) {
            $scope.actor = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
