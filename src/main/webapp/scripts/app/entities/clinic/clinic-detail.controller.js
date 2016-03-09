'use strict';

angular.module('nattymalsApp')
    .controller('ClinicDetailController', function ($scope, $rootScope, $stateParams, entity, Clinic, Veterinarian) {
        $scope.clinic = entity;
        $scope.load = function (id) {
            Clinic.get({id: id}, function(result) {
                $scope.clinic = result;
            });
        };
        var unsubscribe = $rootScope.$on('nattymalsApp:clinicUpdate', function(event, result) {
            $scope.clinic = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
