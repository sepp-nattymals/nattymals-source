'use strict';

angular.module('nattymalsApp').controller('AdministratorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Administrator', 'Discount', 'Announcement',
        function($scope, $stateParams, $uibModalInstance, entity, Administrator, Discount, Announcement) {

        $scope.administrator = entity;
        $scope.discounts = Discount.query();
        $scope.announcements = Announcement.query();
        $scope.load = function(id) {
            Administrator.get({id : id}, function(result) {
                $scope.administrator = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:administratorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.administrator.id != null) {
                Administrator.update($scope.administrator, onSaveSuccess, onSaveError);
            } else {
                Administrator.save($scope.administrator, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
