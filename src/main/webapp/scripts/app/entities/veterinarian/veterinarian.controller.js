'use strict';

angular.module('nattymalsApp')
    .controller('VeterinarianController', function ($scope, $state, Veterinarian, ParseLinks) {

        $scope.veterinarians = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Veterinarian.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.veterinarians = result;
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
            $scope.veterinarian = {
                bankAccount: null,
                webAddress: null,
                refereeNumber: null,
                id: null
            };
        };
    });
