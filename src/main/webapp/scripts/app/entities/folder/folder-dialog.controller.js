'use strict';

angular.module('nattymalsApp').controller('FolderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Folder', 'Customer', 'Message',
        function($scope, $stateParams, $uibModalInstance, entity, Folder, Customer, Message) {

        $scope.folder = entity;
        $scope.customers = Customer.query();
        $scope.messages = Message.query();
        $scope.load = function(id) {
            Folder.get({id : id}, function(result) {
                $scope.folder = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:folderUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.folder.id != null) {
                Folder.update($scope.folder, onSaveSuccess, onSaveError);
            } else {
                Folder.save($scope.folder, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
