'use strict';

angular.module('nattymalsApp').controller('ActorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Actor', 'Payment',
        function($scope, $stateParams, $uibModalInstance, entity, Actor, Payment) {

        $scope.actor = entity;
        $scope.payments = Payment.query();
        $scope.load = function(id) {
            Actor.get({id : id}, function(result) {
                $scope.actor = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:actorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.actor.id != null) {
                Actor.update($scope.actor, onSaveSuccess, onSaveError);
            } else {
                Actor.save($scope.actor, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
