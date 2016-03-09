'use strict';

angular.module('nattymalsApp')
    .controller('PetCommentDetailController', function ($scope, $rootScope, $stateParams, entity, PetComment, PetOwner, Pet) {
        $scope.petComment = entity;
        $scope.load = function (id) {
            PetComment.get({id: id}, function(result) {
                $scope.petComment = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:petCommentUpdate', function(event, result) {
            $scope.petComment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
