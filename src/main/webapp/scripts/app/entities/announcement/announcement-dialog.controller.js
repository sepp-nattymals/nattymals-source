'use strict';

angular.module('nattymalsApp').controller('AnnouncementDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Announcement', 'Administrator',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, Announcement, Administrator) {

        $scope.announcement = entity;
        $scope.administrators = Administrator.query();
        $scope.load = function(id) {
            Announcement.get({id : id}, function(result) {
                $scope.announcement = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('nattymalsApp:announcementUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.announcement.id != null) {
                Announcement.update($scope.announcement, onSaveSuccess, onSaveError);
            } else {
                Announcement.save($scope.announcement, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
        $scope.datePickerForStartDate = {};

        $scope.datePickerForStartDate.status = {
            opened: false
        };

        $scope.datePickerForStartDateOpen = function($event) {
            $scope.datePickerForStartDate.status.opened = true;
        };
        $scope.datePickerForEndDate = {};

        $scope.datePickerForEndDate.status = {
            opened: false
        };

        $scope.datePickerForEndDateOpen = function($event) {
            $scope.datePickerForEndDate.status.opened = true;
        };

        $scope.setPhoto = function ($file, announcement) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        announcement.photo = base64Data;
                        announcement.photoContentType = $file.type;
                    });
                };
            }
        };
}]);
