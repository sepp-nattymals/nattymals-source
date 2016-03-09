'use strict';

angular.module('nattymalsApp')
    .controller('ClinicController', function ($scope, $state, Clinic, ParseLinks) {

        $scope.clinics = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Clinic.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.clinics = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.clinic = {
                address: null,
                city: null,
                province: null,
                schedule: null,
                phoneNumber: null,
                id: null
            };
        };
    });
