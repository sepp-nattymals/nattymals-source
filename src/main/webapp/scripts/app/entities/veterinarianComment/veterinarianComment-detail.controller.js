'use strict';

angular.module('nattymalsApp')
    .controller('VeterinarianCommentDetailController', function ($scope, $rootScope, $stateParams, entity, VeterinarianComment, Veterinarian, PetOwner) {
        $scope.veterinarianComment = entity;
        $scope.load = function (id) {
            VeterinarianComment.get({id: id}, function(result) {
                $scope.veterinarianComment = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:veterinarianCommentUpdate', function(event, result) {
            $scope.veterinarianComment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
