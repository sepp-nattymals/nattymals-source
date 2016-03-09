'use strict';

angular.module('nattymalsApp')
    .controller('AnnouncementDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Announcement, Administrator) {
        $scope.announcement = entity;
        $scope.load = function (id) {
            Announcement.get({id: id}, function(result) {
                $scope.announcement = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:announcementUpdate', function(event, result) {
            $scope.announcement = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
